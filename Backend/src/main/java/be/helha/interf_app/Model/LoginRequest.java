package be.helha.interf_app.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents a login request with the following attributes:
 *
 * Attributes:
 * - email: The email address of the user.
 * - password: The password of the user.
 * - token: The authentication token associated with the user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    /**
     * The email address of the user.
     */
    private String email;
    /**
     * The password of the user.
     */
    private String password;
    /**
     * The authentication token associated with the user.
     */
    private String token;
}