package be.helha.interf_app.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    public boolean checkOwnerGroupAccess( Authentication authentication) {
        String requestUrl = httpServletRequest.getRequestURI();

        String idGroup = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Manager_" + idGroup));
    }
}
