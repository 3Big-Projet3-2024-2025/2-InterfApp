package be.helha.interf_app.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service class for handling security-related operations.
 * This class provides methods to validate user access based on roles and permissions.
 */
@Service
public class SecurityService {

    /**
     * Checks if the authenticated user has access to a specific group as a manager.
     *
     * @param request The HTTP request containing the URI with the group ID.
     * @param authentication The authentication object containing user details and roles.
     * @return {@code true} if the user has the necessary role to access the group, {@code false} otherwise.
     */
    public boolean checkOwnerGroupAccess(HttpServletRequest request, Authentication authentication) {
        // Extract the group ID from the request URL.
        String requestUrl = request.getRequestURI();
        String idGroup = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);

        // Check if the user has the required role for the group.
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Manager_" + idGroup));
    }
}
