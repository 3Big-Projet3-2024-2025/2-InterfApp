package be.helha.interf_app.Service;

import be.helha.interf_app.Model.Group;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Repository.GroupRepository;
import be.helha.interf_app.Repository.UserRepository;
import be.helha.interf_app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for managing and performing operations related to {@link Group} entities.
 * This class provides methods to save, retrieve, update, and manage group members and managers.
 */
@Service
public class GroupService {

    /**
     * The repository for accessing and performing CRUD operations on {@link Group} data.
     */
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Utility for handling JSON Web Tokens (JWT).
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Service for managing user-related operations.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a new group in the repository.
     * Ensures the group ID is unique and sets the current user as the group's manager.
     *
     * @param group The group to be saved.
     * @return The saved {@link Group}, or {@code null} if the group already exists or the user is invalid.
     */
    public Group saveGroup(Group group) {
        if (getGroupById(group.getId()).isEmpty()) {
            String managerId = (String) jwtUtil.parsedJWT.get("id");
            if (userRepository.findById(managerId).isPresent()) {
                List<String>listMembers = group.getListSubGroups().get("Members");
                group.getListSubGroups().put("Managers",new ArrayList<>());
                group.getListSubGroups().put("Members",new ArrayList<>());
                String idGroup = groupRepository.save(group).getId();

                listMembers.forEach((emailMember) -> addMember(emailMember,group.getId()));
                addManager(managerId,group.getId());

                return getGroupById(idGroup).get();
            }
        }
        return null;
    }

    /**
     * Retrieves all groups stored in the repository.
     *
     * @return A {@link List} of all {@link Group} objects.
     */
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * Retrieves a group by its ID from the repository.
     *
     * @param id The ID of the group to retrieve.
     * @return An {@link Optional} containing the {@link Group} if found, or an empty {@link Optional} if not.
     */
    public Optional<Group> getGroupById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return groupRepository.findById(id);
    }

    /**
     * Deletes a group by its ID from the repository.
     *
     * @param groupId The ID of the group to delete.
     */
    public void deleteGroup(String groupId) {
        if (getGroupById(groupId).isPresent()) {
            List<String> listMembers = getGroupById(groupId).get().getListSubGroups().get("Members");
            listMembers.forEach((memberId) -> {
                userRepository.findById(memberId).ifPresent(member -> {
                    member.getListGroup().remove(groupId);
                    member.setRoles(member.getRoles().replace(",Manager_" + groupId,""));
                    userRepository.save(member);
                });
            });
            groupRepository.deleteById(groupId);
        }
    }

    /**
     * Updates an existing group in the repository.
     *
     * @param group The group to be updated.
     * @return The updated {@link Group}, or {@code null} if the group does not exist.
     */
    public Group updateGroup(Group group) {
        if (getGroupById(group.getId()).isPresent()) {
            return groupRepository.save(group);
        }
        return null;
    }

    /**
     * Adds a manager to a group.
     * Updates the user's roles and associates them with the group.
     *
     * @param managerId The ID of the manager to add.
     * @param groupId   The ID of the group to which the manager is added.
     * @return The updated {@link Group}, or {@code null} if the user or group does not exist.
     */
    public Group addManager(String managerId, String groupId) {
        if (userRepository.findById(managerId).isPresent() && getGroupById(groupId).isPresent()) {
            User manager = userRepository.findById(managerId).get();
            manager.setRoles(manager.getRoles() + ",Manager_" + groupId);
            userRepository.save(manager);
            Group group = getGroupById(groupId).get();
            group.getListSubGroups().get("Managers").add(managerId);
            addMember(manager.getEmail(),groupId);
            return groupRepository.save(group);
        }
        return null;
    }

    /**
     * Adds a member to a group.
     * Associates the user with the group and updates the repository.
     *
     * @param memberEmail The ID of the member to add.
     * @param groupId  The ID of the group to which the member is added.
     * @return The updated {@link Group}, or {@code null} if the user or group does not exist.
     */
    public Group addMember(String memberEmail, String groupId) {
        Optional<User> memberOptinal = userRepository.findByEmail(memberEmail);
        if(memberOptinal.isEmpty()){
            User invitedUser = new User();
            invitedUser.setEmail(memberEmail);
            invitedUser.setUsername(memberEmail.split("@")[0]);
            invitedUser.setRoles("User");
            invitedUser.setListGroup(new ArrayList<>());
            userRepository.save(invitedUser);
        }
        if (userRepository.findByEmail(memberEmail).isPresent() && getGroupById(groupId).isPresent()) {
            User member = userRepository.findByEmail(memberEmail).get();
            member.getListGroup().add(groupId);
            userRepository.save(member);
            Group group = getGroupById(groupId).get();
            if(!group.getListSubGroups().get("Members").contains(member.getId())){
                group.getListSubGroups().get("Members").add(member.getId());
            }
            return groupRepository.save(group);
        }
        return null;
    }

    /**
     * Removes a member from a group.
     * Updates the user's associations and the group's member list.
     *
     * @param memberId The ID of the member to remove.
     * @param groupId  The ID of the group from which the member is removed.
     * @return The updated {@link Group}, or {@code null} if the user or group does not exist.
     */
    public Group deleteMember(String memberId, String groupId) {
        if (userRepository.findById(memberId).isPresent() && getGroupById(groupId).isPresent()) {
            User member = userRepository.findById(memberId).get();
            member.getListGroup().remove(groupId);
            userRepository.save(member);
            Group group = getGroupById(groupId).get();
            for (Map.Entry<String, List<String>> entry : group.getListSubGroups().entrySet()) {
                List<String> subGroupList = entry.getValue();
                subGroupList.remove(memberId);
            }
            return groupRepository.save(group);
        }
        return null;
    }

    /**
     * Removes a manager from a group.
     * Ensures at least one manager remains before removing.
     *
     * @param managerId The ID of the manager to remove.
     * @param groupId   The ID of the group from which the manager is removed.
     * @return The updated {@link Group}, or {@code null} if the user, group does not exist, or if it is the last manager.
     */
    public Group deleteManager(String managerId, String groupId) {
        if (userRepository.findById(managerId).isPresent() && getGroupById(groupId).isPresent()&& getGroupById(groupId).get().getListSubGroups().get("Managers").size() > 1) {
            User manager = userRepository.findById(managerId).get();
            manager.setRoles(manager.getRoles().replace(",Manager_" + groupId,""));
            userRepository.save(manager);
            Group group = getGroupById(groupId).get();
            group.getListSubGroups().get("Managers").remove(managerId);
            return groupRepository.save(group);
        }
        return null;
    }
}
