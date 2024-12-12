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
 * information, and deleting users.
 *
 * Attributes:
 * - userRepository: The repository used for accessing User data in the database.
 * - authenticationManager: The authentication manager used for handling authentication operations.
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
    /**
     * Saves a new user to the repository with a default role of "ROLE_User".
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public User saveUser(User user) {
        if(getUserById(user.getId()).isEmpty()) { // The methode post is accessible by any User and if he knows the id he can use it like an update methode
            user.setRoles("User");
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Authenticates the user based on the login request and generates a JWT token
     * for the user if the authentication is successful.
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

        // Remove the password from the login request
        loginRequest.setPassword("");

        // Generate the token with expiration based on "rememberMe"
        String token = JwtUtil.generateToken(userValues, loginRequest.getRememberMe());

        // Set the token in the loginRequest object
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

    public User updateUser (User user) {
        if(getUserById(user.getId()).isPresent() && getUserById(user.getId()).get().getRoles().equals(user.getRoles())) { //prohibit any attempt to modify the user's role
            return userRepository.save(user);
        }
        return null;
    }
}
