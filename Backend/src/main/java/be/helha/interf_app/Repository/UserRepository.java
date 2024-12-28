package be.helha.interf_app.Repository;

import be.helha.interf_app.Model.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository interface for managing User entities.
 * This interface extends MongoRepository to provide CRUD operations for the User entity.
 */
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a user by their email address.
     *
     * @param email The email of the user to search for.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to search for.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    Optional<User> findByUsername(String username);
}
