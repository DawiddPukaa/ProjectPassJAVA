package pl.kul.blog.infrastructure.authentication.filters.token;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.user.account.UserDeviceRepository;
import pl.kul.blog.infrastructure.authentication.GrantedAuthorites;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final UserDeviceRepository userDeviceRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        return userDeviceRepository.findByToken(tokenAuthentication.token)
            .map(it -> {
                TokenAuthentication authenticated = new TokenAuthentication(
                    it.getUserAccount(),
                    tokenAuthentication.token,
                    it.getUserAccount()
                        .getRoles()
                        .stream()
                        .map(assignedRole -> GrantedAuthorites.from(assignedRole.getRole()))
                        .collect(Collectors.toSet())
                );
                authenticated.setAuthenticated(true);
                return authenticated;
            })
            .orElse(null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TokenAuthentication.class;
    }
}
