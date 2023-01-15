package pl.kul.blog.helpers.users;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserAccountAssignedRole;
import pl.kul.blog.domain.user.account.UserAccountRole;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserAccountFixtures {
    public static UserAccount.UserAccountBuilder someUserAccount() {
        return UserAccount.builder()
            .id(UUID.randomUUID())
            .isAccountLocked(false)
            .isCredentialsExpired(false)
            .isEnabled(true)
            .isExpired(false)
            .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("secure-password"))
            .registeredAt(Instant.now().minus(Duration.ofDays(5)))
            .roles(new HashSet<>(Set.of(UserAccountAssignedRole.builder()
                .id(UUID.randomUUID())
                .role(UserAccountRole.REGULAR)
                .build())))
            .username("some-user");
    }
}
