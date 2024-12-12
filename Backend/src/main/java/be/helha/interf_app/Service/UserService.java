package be.helha.interf_app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;

import be.helha.interf_app.Model.LoginRequest;
import be.helha.interf_app.Model.User;
import be.helha.interf_app.Repository.UserRepository;
import be.helha.interf_app.security.JwtUtil;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing and performing operations related to User entities.
 * This class provides methods for saving users, logging in users, retrieving user
 * information, updating user details, and deleting users.
 *
 * <p>Attributes:</p>
 * <ul>
 *   <li><b>userRepository</b>: The repository used for accessing User data in the database.</li>
 *   <li><b>authenticationManager</b>: The authentication manager used for handling authentication operations.</li>
 * </ul>
 */
@Service
public class UserService {

    /**
     * The repository for accessing and performing CRUD operations on the User data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The authentication manager for authenticating users.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Saves a new user to the repository with a default role of "ROLE_User".
     * If a user with the given ID already exists, the user is not saved.
     *
     * @param user The user to be saved.
     * @return The saved user, or null if the user already exists.
     */
    public User saveUser(User user) {
        if (getUserById(user.getId()).isEmpty()) {
            // The method POST is accessible by any user and if they know the ID, they can use it like an update method.
            user.setRoles("User");
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Authenticates the user based on the login request and generates a JWT token
     * for the user if the authentication is successful.
     *
     * <p>If authentication fails (e.g., incorrect credentials or user not found),
     * this method returns null.</p>
     *
     * @param loginRequest The login request containing the user's credentials.
     * @return The updated login request with the JWT token if authentication is successful,
     *         or null if authentication fails.
     */
    public LoginRequest login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (!userOptional.isPresent()) {
            return null;
        }

        User userValues = userOptional.get();

        // Check if the provided password matches the stored password
        if (!userValues.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        // Remove the password from the login request for security reasons
        loginRequest.setPassword("");

        // Generate the token with expiration based on "rememberMe" flag
        String token = JwtUtil.generateToken(userValues, loginRequest.getRememberMe());

        // Set the generated token in the loginRequest object
        loginRequest.setToken(token);

        return loginRequest;
    }

    /**
     * Retrieves all users stored in the repository.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID from the repository.
     *
     * @param id The ID of the user to be retrieved.
     * @return An Optional containing the user if found, or an empty Optional if not.
     */
    public Optional<User> getUserById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return userRepository.findById(id);
    }

    /**
     * Deletes a user by their ID from the repository.
     *
     * @param id The ID of the user to be deleted.
     */
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieves a user by their email from the repository.
     *
     * @param email The email of the user to be retrieved.
     * @return An Optional containing the user if found, or an empty Optional if not.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Updates an existing user in the repository.
     * The method ensures that the user's role cannot be modified by unauthorized users.
     *
     * @param user The user to be updated with new information.
     * @return The updated user if the update is successful, or null if the user's role is being changed.
     */
    public User updateUser(User user) {
        // Prohibit any attempt to modify the user's role
        if (getUserById(user.getId()).isPresent() &&
                getUserById(user.getId()).get().getRoles().equals(user.getRoles())) {
            return userRepository.save(user);
        }
        return null;
    }
}
