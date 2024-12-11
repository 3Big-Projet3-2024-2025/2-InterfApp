package be.helha.interf_app.Controller;

import be.helha.interf_app.Model.Group;
import be.helha.interf_app.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to groups.
 *
 * This controller defines endpoints for performing CRUD operations on groups:
 * - Creating a new group
 * - Retrieving all groups
 * - Retrieving a specific group by its ID
 * - Deleting a group by its ID
 *
 * All endpoints are cross-origin enabled to allow interaction with frontend applications (e.g., Angular).
 */
@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*") // Allow all origins to access endpoints
public class GroupController {

    /**
     * Service layer for handling group-related business logic.
     */
    @Autowired
    private GroupService groupService;

    /**
     * Endpoint to create a new group.
     *
     * @param group the group object to be created, provided in the request body
     * @return a ResponseEntity containing the created group with HTTP 200 status, or HTTP 400 if creation fails
     */
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group savedGroup = groupService.saveGroup(group);
        if (savedGroup != null) {
            return ResponseEntity.ok(savedGroup);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to retrieve all groups.
     *
     * @return a ResponseEntity containing a list of all groups with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    /**
     * Endpoint to retrieve a specific group by its unique ID.
     *
     * @param id the unique identifier of the group to retrieve, extracted from the URL path
     * @return a ResponseEntity containing the requested group with HTTP 200 status, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable String id) {
        Optional<Group> group = groupService.getGroupById(id);
        return group.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a specific group by its unique ID.
     *
     * @param id the unique identifier of the group to delete, extracted from the URL path
     * @return a ResponseEntity with HTTP 204 status upon successful deletion, or HTTP 404 if the group is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
        Optional<Group> group = groupService.getGroupById(id);
        if (group.isPresent()) {
            groupService.deleteGroup(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}