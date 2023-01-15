package pl.kul.blog.infrastructure.authentication.filters.pwd;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class UsernameAndPasswordAuthenticationFilter extends AuthenticationFilter {
    public UsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, UsernameAndPasswordFromFormParamAuthenticationConverter authenticationConverter, String path) {
        super(authenticationManager, authenticationConverter);
        setRequestMatcher(new AntPathRequestMatcher(path));
        setSuccessHandler((request, response, authentication) -> {

        });
    }
}
