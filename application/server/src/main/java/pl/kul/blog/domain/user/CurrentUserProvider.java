package pl.kul.blog.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.user.account.UserAccount;

@Component
@AllArgsConstructor
public class CurrentUserProvider {
    public UserAccount   getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
    }
}
