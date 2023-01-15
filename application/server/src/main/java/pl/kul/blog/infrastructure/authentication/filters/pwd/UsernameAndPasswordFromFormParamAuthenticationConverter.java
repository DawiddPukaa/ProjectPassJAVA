package pl.kul.blog.infrastructure.authentication.filters.pwd;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UsernameAndPasswordFromFormParamAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        return new UsernamePasswordAuthenticationToken(
            request.getParameter("username"),
            request.getParameter("password")
        );
    }
}
