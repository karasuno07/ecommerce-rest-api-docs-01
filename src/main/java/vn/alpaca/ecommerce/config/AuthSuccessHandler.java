package vn.alpaca.ecommerce.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import vn.alpaca.ecommerce.entity.User;
import vn.alpaca.ecommerce.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public AuthSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {

        String username = authentication.getName();
        User loggedInUser = userService.findByUsername(username);

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user", loggedInUser);

        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            String role = grantedAuthority.toString();
            if (role.equals("ROLE_ADMIN") || role.equals("ROLE_MANAGER")) {
                httpServletResponse.sendRedirect(
                    httpServletRequest.getContextPath() + "/system"
                );
                return;
            }
        }

        httpServletResponse.sendRedirect(
                httpServletRequest.getContextPath() + "/home"
        );
    }
}
