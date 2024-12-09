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
        String ownerId = (String) jwtUtil.parsedJWT.get("id");
        if(userService.getUserById(ownerId).isPresent()) {
            User owner = userService.getUserById(ownerId).get();
            owner.setRoles(owner.getRoles() + ",Group_" + ownerId);
            userService.updateUser(owner);
            group.setListOwners(new ArrayList<>(Arrays.asList(ownerId)));
            group.setListMembers(new ArrayList<>());
            group.setListForms(new ArrayList<>());
            return groupRepository.save(group);
        }
        return null;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(String id) {
        return groupRepository.findById(id);
    }

    public void deleteGroup(String id) {
        groupRepository.deleteById(id);
    }
}
