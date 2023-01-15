package pl.kul.blog.infrastructure.authentication.filters.token;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class TokenAuthenticationFilter extends AuthenticationFilter {
    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, TokenAuthenticationConverter authenticationConverter, String path) {
        super(authenticationManager, authenticationConverter);
        setRequestMatcher(new AntPathRequestMatcher(path));
        setSuccessHandler((request, response, authentication) -> {

        });
    }


}
