package be.helha.interf_app.Service;

import be.helha.interf_app.Model.Form;
import be.helha.interf_app.Model.Group;
import be.helha.interf_app.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
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
