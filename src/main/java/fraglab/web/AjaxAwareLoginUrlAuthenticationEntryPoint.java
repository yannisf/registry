package fraglab.web;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAwareLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public AjaxAwareLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        } else {
            super.commence(request, response, authException);
        }

    }

}
