package be.helha.interf_app.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean checkOwnerGroupAccess(HttpServletRequest request, Authentication authentication) {
        String requestUrl = request.getRequestURI();

        String idGroup = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Manager_" + idGroup));
    }
}
