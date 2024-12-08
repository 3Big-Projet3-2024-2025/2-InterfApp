package be.helha.interf_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import be.helha.interf_app.Controller.UserController;
import be.helha.interf_app.Model.User;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

/**
 * Utility class for handling JWT (JSON Web Token) creation, validation, and parsing.
 * This class provides methods to generate JWT tokens for authenticated users,
 * validate JWT tokens from requests, and extract the necessary claims from the token.
 *
 * Attributes:
 * - secretKey: The secret key used to sign and validate the JWT token.
 * - userController: A controller that provides user-related operations,
 * such as retrieving a user by email.
 */
@Component
public class JwtUtil {
    // The secret key used for signing and verifying the JWT tokens
    private final static String key = "mklsqfmlkqsjdfoziuhbrfzeamzekpoiwxnbjlxdwf";
    private final static SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    // Controller for accessing user data
    private UserController userController;

    @Getter
    public Claims parsedJWT;

    /**
     * Constructs a JwtUtil object with the provided UserController instance.
     *
     * @param userController The UserController used to retrieve user information.
     */
    public JwtUtil(UserController userController) {
        this.userController = userController;
    }

    /**
     * Generates a JWT token for a given user. The token contains the user's ID,
     * email, roles, and an expiration time of one hour.
     *
     * @param user The user for whom the token is being generated.
     * @return A JWT token as a string.
     */
    public static String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+3600000))
                .claims(Map.of(
                        "id", user.getId(),
                        "roles", user.getRoles()
                ))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Validates the given JWT token. The method extracts the claims from the token,
     * including the user's email and roles, and attempts to retrieve the corresponding
     * user from the database. If successful, it returns an Authentication object
     * containing the user's credentials and roles.
     *
     * @param JWT The JWT token to be validated.
     * @return An Authentication object containing the user's email, password,
     *         and granted authorities, or null if validation fails.
     * @throws UsernameNotFoundException if the user with the given email is not found.
     */
    public Authentication validateJWT(String JWT){
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

        // Parse the JWT and extract claims
        this.parsedJWT = jwtParser.parseSignedClaims(JWT).getPayload();
        String id = (String)parsedJWT.get("id");
        String roles = (String)parsedJWT.get("roles");

        // If the email is not null, retrieve the user and create authentication
        if (Objects.nonNull(id)) {
            List<SimpleGrantedAuthority> authorities = List.of(roles.split(","))
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            // Retrieve the user by email from the database
            User user = userController.getUserById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

            // Return an Authentication token with the user's email, password, and roles
            return new UsernamePasswordAuthenticationToken(id, user.getPassword(), authorities);
        }
        // Return null if email is not found in the claims
        return null;
    }
}
