package pl.kul.blog.infrastructure.authentication.filters;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import pl.kul.blog.infrastructure.authentication.filters.pwd.UsernameAndPasswordAuthenticationFilter;
import pl.kul.blog.infrastructure.authentication.filters.pwd.UsernameAndPasswordFromFormParamAuthenticationConverter;
import pl.kul.blog.infrastructure.authentication.filters.token.TokenAuthenticationConverter;
import pl.kul.blog.infrastructure.authentication.filters.token.TokenAuthenticationFilter;

@Component
@AllArgsConstructor
public class AuthenticationFiltersFactory {
    private final TokenAuthenticationConverter tokenAuthenticationConverter;
    private final UsernameAndPasswordFromFormParamAuthenticationConverter usernameAndPasswordFromFormParamAuthenticationConverter;

    public TokenAuthenticationFilter createTokenAuthenticationFilter(AuthenticationManager authenticationManager, String path) {
        return new TokenAuthenticationFilter(authenticationManager, tokenAuthenticationConverter, path);
    }

    public UsernameAndPasswordAuthenticationFilter createUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, String path) {
        return new UsernameAndPasswordAuthenticationFilter(authenticationManager, usernameAndPasswordFromFormParamAuthenticationConverter, path);
    }
}

