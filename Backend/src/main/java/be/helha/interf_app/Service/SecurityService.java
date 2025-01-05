package be.helha.interf_app.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service class for handling security-related operations.
 * This class provides methods to validate user access based on roles and permissions.
 */
@Service
public class SecurityService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    public boolean checkOwnerGroupAccess( Authentication authentication) {
        String requestUrl = httpServletRequest.getRequestURI();
        String idGroup = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);

        // Check if the user has the required role for the group.
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Manager_" + idGroup));
    }
}
