package be.helha.interf_app.Controller;

import be.helha.interf_app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import be.helha.interf_app.Model.LoginRequest;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to users.
 *
 * This controller defines endpoints for:
 * - Creating a new user
 * - Authenticating a user via login
 * - Retrieving all users
 * - Retrieving a user by their ID
 * - Retrieving a user by their email
 * - Deleting a user by their ID
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow all origins to access endpoints
public class UserController {

    /**
     * Service layer for user-related operations.
     */
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint to create a new user.
     *
     * This method creates a new user based on the provided user object in the request body.
     * If the user is created successfully, the method returns the created user with HTTP 200 status.
     * If the creation fails, it returns an HTTP 400 status.
     *
     * @param user the user object to be created, provided in the request body
     * @return a ResponseEntity containing the created user with HTTP 200 status, or HTTP 400 if creation fails
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to authenticate a user via login.
     *
     * This method authenticates a user based on the login credentials (email and password) provided
     * in the request body. If the authentication is successful, it returns a ResponseEntity with
     * HTTP 200 status and the login request details.
     * If authentication fails, it returns HTTP 401 (Unauthorized).
     *
     * @param loginRequest the login credentials (email and password), provided in the request body
     * @return a ResponseEntity containing the login response with HTTP 200 status, or HTTP 401 if authentication fails
     */
    @PostMapping("/login")
    public ResponseEntity<LoginRequest> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginRequest responseLoginRequest = userService.login(loginRequest);

        if (responseLoginRequest == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(responseLoginRequest);
    }

    /**
     * Endpoint to retrieve all users.
     *
     * This method retrieves a list of all users from the database and returns them wrapped in a
     * ResponseEntity with HTTP 200 status.
     *
     * @return a ResponseEntity containing a list of all users with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/token/{JWT}")
    public ResponseEntity<Map<String,String>> getRefreshToken(@PathVariable String JWT) {
        try {
            String refreshToken = jwtUtil.validateAndRefreshToken(JWT);
            return ResponseEntity.ok(Map.of("token", refreshToken));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("token", ""));
        }
    }
    /**
     * Endpoint to retrieve a user by their ID.
     *
     * This method retrieves a user by their unique ID. If the user is found, it returns the
     * user wrapped in a ResponseEntity with HTTP 200 status. If the user is not found, it returns
     * HTTP 404 (Not Found).
     *
     * @param id the unique identifier of the user to retrieve, extracted from the URL path
     * @return a ResponseEntity containing the requested user with HTTP 200 status, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a user by their ID.
     *
     * This method deletes a user by their unique ID. If the user is found and deleted successfully,
     * it returns HTTP 204 (No Content). If the user is not found, it returns HTTP 404 (Not Found).
     *
     * @param id the unique identifier of the user to delete, extracted from the URL path
     * @return a ResponseEntity with HTTP 204 status upon successful deletion, or HTTP 404 if the user is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve a user by their email.
     *
     * This method retrieves a user by their email address. If the user is found, it returns the
     * user wrapped in a ResponseEntity with HTTP 200 status. If the user is not found, it returns
     * HTTP 404 (Not Found).
     *
     * @param email the email address of the user to retrieve, extracted from the URL path
     * @return a ResponseEntity containing the requested user with HTTP 200 status, or HTTP 404 if not found
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to retrieve a user by their username.
     *
     * This method retrieves a user by their username. If the user is found, it returns
     * the user wrapped in a ResponseEntity with HTTP 200 status. If the user is not found,
     * it returns HTTP 404 (Not Found).
     *
     * @param username The username of the user to retrieve.
     * @return a ResponseEntity containing the requested user with HTTP 200 status, or HTTP 404 if not found
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to update an existing user.
     *
     * This method updates an existing user based on the provided user object in the request body.
     * If the update is successful, it returns the updated user wrapped in a ResponseEntity with HTTP 200 status.
     * If the update fails or the user is not found, it returns HTTP 404 (Not Found).
     *
     * @param id   the ID of the user to be updated, provided as a path variable
     * @param user the updated user object, provided in the request body
     * @return a ResponseEntity containing the updated user with HTTP 200 status, or HTTP 404 if the update fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
