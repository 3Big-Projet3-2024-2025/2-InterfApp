package be.helha.interf_app.Service;

import be.helha.interf_app.Model.Group;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Repository.GroupRepository;
import be.helha.interf_app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    public Group saveGroup(Group group) {
        if(getGroupById(group.getId()).isEmpty()) { // The methode post is accessible by any User and if he knows the id he can use it like an update methode
            String managerId = (String) jwtUtil.parsedJWT.get("id");
            if (userService.getUserById(managerId).isPresent()) {
                User manager = userService.getUserById(managerId).get();
                manager.setRoles(manager.getRoles() + ",Manager_" + managerId);
                userService.updateUser(manager);
                group.setListManagers(new ArrayList<>(Arrays.asList(managerId)));
                group.setListMembers(new ArrayList<>());
                return groupRepository.save(group);
            }
        }
        return null;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return groupRepository.findById(id);
    }

    public void deleteGroup(String id) {
        groupRepository.deleteById(id);
    }

    public Group updateGroup(Group group) {
        if(getGroupById(group.getId()).isPresent()) {
            return groupRepository.save(group);
        }
        return null;
    }

    public Group addManager(String managerId, String groupId) {
        if (userService.getUserById(managerId).isPresent() && getGroupById(groupId).isPresent()) {
            User manager = userService.getUserById(managerId).get();
            manager.setRoles(manager.getRoles() + ",Manager_" + groupId);
            manager.getListGroup().add(groupId);
            userService.updateUser(manager);
            Group group = getGroupById(groupId).get();
            group.getListManagers().add(managerId);
            return groupRepository.save(group);
        }
        return null;
    }

    public Group addMember(String memberId, String groupId) {
        if (userService.getUserById(memberId).isPresent() && getGroupById(groupId).isPresent()) {
            User member = userService.getUserById(memberId).get();
            member.getListGroup().add(groupId);
            userService.updateUser(member);
            Group group = getGroupById(groupId).get();
            group.getListManagers().add(memberId);
            return groupRepository.save(group);
        }
        return null;
    }

    public Group deleteMember(String memberId, String groupId) {
        if (userService.getUserById(memberId).isPresent() && getGroupById(groupId).isPresent()) {
            User member = userService.getUserById(memberId).get();
            member.getListGroup().remove(groupId);
            userService.updateUser(member);
            Group group = getGroupById(groupId).get();
            group.getListManagers().remove(memberId);
            return groupRepository.save(group);
        }
        return null;
    }

    public Group deleteManager(String managerId, String groupId) {
        if (userService.getUserById(managerId).isPresent() && getGroupById(groupId).isPresent()&& getGroupById(groupId).get().getListManagers().size() > 1) {
            User manager = userService.getUserById(managerId).get();
            manager.getListGroup().remove(groupId);
            userService.updateUser(manager);
            Group group = getGroupById(groupId).get();
            group.getListManagers().remove(managerId);
            return groupRepository.save(group);
        }
        return null;
    }
}
