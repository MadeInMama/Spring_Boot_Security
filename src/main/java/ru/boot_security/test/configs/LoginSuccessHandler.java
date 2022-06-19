package ru.boot_security.test.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import ru.boot_security.test.entities.Roles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Roles.ADMIN.name()))) {
            redirectStrategy.sendRedirect(request, response, "/admin/");
        } else {
            redirectStrategy.sendRedirect(request, response, "/user/");
        }

        clearAuthenticationAttributes(request);
    }
}
