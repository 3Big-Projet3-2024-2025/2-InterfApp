package be.helha.interf_app;

import be.helha.interf_app.Controller.UserController;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Model.LoginRequest;
import be.helha.interf_app.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link UserController}.
 * This class contains unit tests for the UserController methods, ensuring that the endpoints behave correctly.
 * It uses MockMvc to simulate HTTP requests and validate the responses.
 */
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private String jwtToken;

    /**
     * Setup method to initialize the mock environment before each test.
     * It creates a test user and a simulated JWT token for authentication in the tests.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User("1", "test@example.com", "testuser", "password", "User", null);

        jwtToken = "Bearer your.jwt.token";
    }

    /**
     * Test for creating a new user through the POST /api/users endpoint.
     * It simulates sending a POST request to create a user and verifies that the response returns the created user.
     */
    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"email\":\"test@example.com\",\"username\":\"testuser\",\"password\":\"password\",\"roles\":\"User\"}")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    /**
     * Test for retrieving a user by ID through the GET /api/users/{id} endpoint.
     * It simulates a GET request and verifies that the user with the specified ID is returned.
     */
    @Test
    void getUserById_ShouldReturnUser_WhenExists() throws Exception {
        when(userService.getUserById("1")).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(get("/api/users/1")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    /**
     * Test for logging in a user through the POST /api/users/login endpoint.
     * It simulates a login request and verifies that the response contains a JWT token.
     */
    @Test
    void loginUser_ShouldReturnToken_WhenAuthenticated() throws Exception {

        LoginRequest loginRequest = new LoginRequest("test@example.com", "password", null, true);

        when(userService.login(loginRequest)).thenReturn(new LoginRequest("test@example.com", "password", "JWT_TOKEN", true));

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"password\",\"rememberMe\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("JWT_TOKEN"));
    }

    /**
     * Test for deleting a user by ID through the DELETE /api/users/{id} endpoint.
     * It simulates a DELETE request to remove a user and verifies that the response status is NoContent.
     */
    @Test
    void deleteUser_ShouldReturnNoContent_WhenDeleted() throws Exception {
        when(userService.getUserById("1")).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(delete("/api/users/1")
                        .header("Authorization", jwtToken))
                .andExpect(status().isNoContent());
    }

    /**
     * Test for retrieving all users through the GET /api/users endpoint.
     * It simulates a GET request and verifies that the response returns a list of users.
     */
    @Test
    void getAllUsers_ShouldReturnUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(java.util.List.of(user));

        mockMvc.perform(get("/api/users")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }
}
