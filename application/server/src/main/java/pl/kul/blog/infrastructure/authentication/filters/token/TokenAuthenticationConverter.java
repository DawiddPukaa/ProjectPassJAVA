package pl.kul.blog.infrastructure.authentication.filters.token;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TokenAuthenticationConverter implements AuthenticationConverter {
    private static final Pattern TOKEN_HEADER_PATTERN = Pattern.compile("token (?<token>.+)");

    @Override
    public Authentication convert(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
            .map(TOKEN_HEADER_PATTERN::matcher)
            .filter(Matcher::matches)
            .map(it -> it.group("token"))
            .map(it -> new TokenAuthentication(it))
            .orElse(null);
    }
}
