package be.helha.interf_app.Controller;

import be.helha.interf_app.Model.PasswordCheckRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import be.helha.interf_app.Model.LoginRequest;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Service.UserService;

import java.util.List;
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

    /**
     * Endpoint to create a new user.
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
     * @return a ResponseEntity containing a list of all users with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint to retrieve a user by their ID.
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
     * Endpoint to update an existing user.
     *
     * @param user the updated user object, provided in the request body
     * @return a ResponseEntity containing the updated user with HTTP 200 status, or HTTP 404 if the update fails
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/checkPassword")
    public ResponseEntity<Boolean> checkPassword(@RequestBody User user) {
        PasswordCheckRequest passwordCheckRequest = new PasswordCheckRequest(user.getId(), user.getPassword());
        boolean isPasswordCorrect = userService.checkPassword(passwordCheckRequest.getId(), passwordCheckRequest.getPassword());
        return ResponseEntity.ok(isPasswordCorrect);
    }
}
