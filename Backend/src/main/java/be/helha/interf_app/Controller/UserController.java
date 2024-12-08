package be.helha.interf_app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*") // Allow Angular to access endpoints
public class UserController {

    /**
     * Service layer for user-related operations.
     */
    @Autowired
    private UserService userService;

    /**
     * Endpoint to create a new user.
     *
     * @param user the user object to be created
     * @return the created user
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * Endpoint to authenticate a user via login.
     *
     * @param loginRequest the login credentials (email and password)
     * @return a LoginRequest object wrapped in a ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity<LoginRequest> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    /**
     * Endpoint to retrieve all users.
     *
     * @return a list of all users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Endpoint to retrieve a user by their ID.
     *
     * @param id the unique identifier of the user
     * @return the user object wrapped in an Optional
     */
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    /**
     * Endpoint to delete a user by their ID.
     *
     * @param id the unique identifier of the user to delete
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    /**
     * Endpoint to retrieve a user by their email.
     *
     * @param email the email address of the user
     * @return the user object wrapped in an Optional
     */
    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
