package be.helha.interf_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a user in the system with the following attributes:
 *
 * Attributes:
 * - id: The unique identifier for the user.
 * - email: The email address of the user.
 * - username: The username of the user.
 * - password: The password of the user (write-only for security).
 * - roles: The roles assigned to the user, defining their permissions within the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {
    /**
     * The unique identifier for the user.
     * This is automatically generated by MongoDB.
     */
    @Id
    private String id;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     * This field is write-only, meaning it will not be serialized when
     * the User object is converted to JSON for response.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    private String password;

    /**
     * The roles assigned to the user.
     * This field defines the user's access level and permissions within the system.
     */
    private String roles;
}


