package be.helha.interf_app.Service;

import be.helha.interf_app.Model.Group;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Repository.GroupRepository;
import be.helha.interf_app.Repository.UserRepository;
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
    private UserRepository userRepository;

    public Group saveGroup(Group group) {
        if(getGroupById(group.getId()).isEmpty()) { // The methode post is accessible by any User and if he knows the id he can use it like an update methode
            String managerId = (String) jwtUtil.parsedJWT.get("id");
            if (userRepository.findById(managerId).isPresent()) {
                List<String> listIdMember = new ArrayList<>();
                for (String email :group.getListSubGroups().get("Members")) {
                    Optional<User> member = userRepository.findByEmail(email);
                    if(member.isPresent()){
                        listIdMember.add(member.get().getId());
                    }else{
                        User invitedUser = new User();
                        invitedUser.setEmail(email);
                        invitedUser.setRoles("User");
                        invitedUser.setListGroup(new ArrayList<>());
                        listIdMember.add(userRepository.save(invitedUser).getId()) ;
                    }
                }
                group.getListSubGroups().put("Managers",new ArrayList<>(Arrays.asList(managerId)));
                group.getListSubGroups().put("Members",new ArrayList<>());
                String idGroup = groupRepository.save(group).getId();

                listIdMember.forEach((idMember) -> addMember(idMember,group.getId()));

                User manager = userRepository.findById(managerId).get();
                manager.setRoles(manager.getRoles() + ",Manager_" + idGroup);
                userRepository.save(manager);

                return getGroupById(idGroup).get();
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
        if (userRepository.findById(managerId).isPresent() && getGroupById(groupId).isPresent()) {
            User manager = userRepository.findById(managerId).get();
            manager.setRoles(manager.getRoles() + ",Manager_" + groupId);
            manager.getListGroup().add(groupId);
            userRepository.save(manager);
            Group group = getGroupById(groupId).get();
            group.getListSubGroups().get("Managers").add(managerId);
            return groupRepository.save(group);
        }
        return null;
    }

    public Group addMember(String memberId, String groupId) {
        if (userRepository.findById(memberId).isPresent() && getGroupById(groupId).isPresent()) {
            User member = userRepository.findById(memberId).get();
            member.getListGroup().add(groupId);
            userRepository.save(member);
            Group group = getGroupById(groupId).get();
            group.getListSubGroups().get("Members").add(memberId);
            return groupRepository.save(group);
        }
        return null;
    }

    public Group deleteMember(String memberId, String groupId) {
        if (userRepository.findById(memberId).isPresent() && getGroupById(groupId).isPresent()) {
            User member = userRepository.findById(memberId).get();
            member.getListGroup().remove(groupId);
            userRepository.save(member);
            Group group = getGroupById(groupId).get();
            group.getListSubGroups().get("Members").remove(memberId);
            return groupRepository.save(group);
        }
        return null;
    }

    public Group deleteManager(String managerId, String groupId) {
        if (userRepository.findById(managerId).isPresent() && getGroupById(groupId).isPresent()&& getGroupById(groupId).get().getListSubGroups().get("Managers").size() > 2) {
            User manager = userRepository.findById(managerId).get();
            manager.getListGroup().remove(groupId);
            userRepository.save(manager);
            Group group = getGroupById(groupId).get();
            group.getListSubGroups().get("Managers").remove(managerId);
            return groupRepository.save(group);
        }
        return null;
    }
}
