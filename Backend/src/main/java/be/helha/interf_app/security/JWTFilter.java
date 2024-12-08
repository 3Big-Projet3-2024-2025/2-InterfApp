package be.helha.interf_app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Claims;
import java.util.Objects;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import java.io.IOException;
/**
 * A filter for handling JWT (JSON Web Token) authentication in incoming HTTP requests.
 * This filter checks the Authorization header for a valid JWT token, validates it,
 * and sets the authentication in the security context if the token is valid.
 *
 * Attributes:
 * - jwtUtils: A utility class for validating and parsing the JWT token.
 * - log: A logger to log any errors during the JWT processing.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    /**
     * A utility class used to validate and parse JWT tokens.
     */
    @Autowired
    private JwtUtil jwtUtils;

    /**
     * This method is invoked for each request to check for a valid JWT token in
     * the request's Authorization header. If a valid token is found, the user's
     * authentication is set in the security context.
     *
     * @param request The HTTP request being processed.
     * @param response The HTTP response.
     * @param filterChain The filter chain that allows the request to proceed.
     * @throws ServletException If an error occurs during the filter chain processing.
     * @throws IOException If an I/O error occurs while reading the request or writing the response.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Retrieve the JWT token from the request
            String jwt = getJWT(request);
            if (Objects.nonNull(jwt)) {
                // Validate the JWT and set authentication in the security context
                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) jwtUtils.validateJWT(jwt);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            log.error("Exception while processing the JWT "+e.getMessage());
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves the JWT token from the Authorization header of the request.
     *
     * @param request The HTTP request containing the Authorization header.
     * @return The JWT token if it exists, or null if not.
     */
    private String getJWT(HttpServletRequest request){
        String jwt = request.getHeader("authorization");
        if(Objects.nonNull(jwt) && jwt.startsWith("Bearer") &&
        jwt.length()>7){
            return jwt.substring(7);
        }
        return null;
    }
}
