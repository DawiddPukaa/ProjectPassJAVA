package pl.kul.blog.infrastructure.authentication;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.user.account.UserAccountRepository;

@Component
@AllArgsConstructor
public class UserAccountBasedUserDetailsService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username)
            .map(it -> new UserAccountBasedUserDetails(it))
            .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
