package be.helha.interf_app;

import be.helha.interf_app.Controller.GroupController;
import be.helha.interf_app.Model.Group;
import be.helha.interf_app.Service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for GroupController.
 * This class contains unit tests for all the CRUD operations and functionalities
 * for the GroupController class using MockMvc and Mockito.
 */
@SpringBootTest
class GroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private Group group;
    private Map<String, List<String>> subGroups;

    /**
     * Setup method that initializes the mock objects and MockMvc instance.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();

        // Sample group data for tests
        subGroups = new HashMap<>();
        subGroups.put("Managers", Arrays.asList("manager1"));
        subGroups.put("Members", Arrays.asList("member1", "member2"));

        group = new Group("1", "Test Group", subGroups);
    }

    /**
     * Test for creating a group. Verifies that the created group is returned.
     * @throws Exception If the request processing fails.
     */
    @Test
    void createGroup_ShouldReturnCreatedGroup() throws Exception {
        when(groupService.saveGroup(any(Group.class))).thenReturn(group);

        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"Test Group\", \"listSubGroups\": {\"Managers\": [\"manager1\"], \"Members\": [\"member1\", \"member2\"]}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Group"))
                .andExpect(jsonPath("$.listSubGroups.Managers[0]").value("manager1"))
                .andExpect(jsonPath("$.listSubGroups.Members[0]").value("member1"));
    }

    /**
     * Test for retrieving all groups. Verifies that the list of groups is returned correctly.
     * @throws Exception If the request processing fails.
     */
    @Test
    void getAllGroups_ShouldReturnAllGroups() throws Exception {
        when(groupService.getAllGroups()).thenReturn(Arrays.asList(group));

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Test Group"));
    }

    /**
     * Test for retrieving a group by ID when the group exists.
     * @throws Exception If the request processing fails.
     */
    @Test
    void getGroupById_ShouldReturnGroup_WhenExists() throws Exception {
        when(groupService.getGroupById("1")).thenReturn(Optional.of(group));

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Group"));
    }

    /**
     * Test for retrieving a group by ID when the group does not exist.
     * @throws Exception If the request processing fails.
     */
    @Test
    void getGroupById_ShouldReturnNotFound_WhenGroupDoesNotExist() throws Exception {
        when(groupService.getGroupById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test for deleting a group by ID. Verifies that the group is deleted successfully.
     * @throws Exception If the request processing fails.
     */
    @Test
    void deleteGroup_ShouldReturnNoContent_WhenDeleted() throws Exception {
        when(groupService.getGroupById("1")).thenReturn(Optional.of(group));

        mockMvc.perform(delete("/api/groups/1"))
                .andExpect(status().isNoContent());

        verify(groupService, times(1)).deleteGroup("1");
    }

    /**
     * Test for updating an existing group. Verifies that the updated group is returned.
     * @throws Exception If the request processing fails.
     */
    @Test
    void updateGroup_ShouldReturnUpdatedGroup() throws Exception {
        Group updatedGroup = new Group("1", "Updated Group", subGroups);
        when(groupService.updateGroup(any(Group.class))).thenReturn(updatedGroup);

        mockMvc.perform(put("/api/groups/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"Updated Group\", \"listSubGroups\": {\"Managers\": [\"manager1\"], \"Members\": [\"member1\", \"member2\"]}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Updated Group"));
    }

    /**
     * Test for adding a manager to a group. Verifies that the manager is added successfully.
     * @throws Exception If the request processing fails.
     */
    @Test
    void addManager_ShouldReturnUpdatedGroup() throws Exception {
        when(groupService.addManager("manager1", "1")).thenReturn(group);

        mockMvc.perform(put("/api/groups/manager/manager1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listSubGroups.Managers[0]").value("manager1"));
    }

    /**
     * Test for deleting a manager from a group. Verifies that the manager is removed successfully.
     * @throws Exception If the request processing fails.
     */
    @Test
    void deleteManager_ShouldReturnUpdatedGroup() throws Exception {
        // If there's more than 1 manager, the manager will be removed.
        when(groupService.deleteManager("manager1", "1")).thenReturn(group);

        mockMvc.perform(delete("/api/groups/manager/manager1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listSubGroups.Managers").isArray())
                .andExpect(jsonPath("$.listSubGroups.Managers.length()").value(1))
                .andExpect(jsonPath("$.listSubGroups.Managers[0]").value("manager1"));
    }
}
