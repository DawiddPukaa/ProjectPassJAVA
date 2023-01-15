package pl.kul.blog.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserAccountAssignedRole;
import pl.kul.blog.domain.user.account.UserAccountAssignedRoleRepository;
import pl.kul.blog.domain.user.account.UserAccountRepository;
import pl.kul.blog.domain.user.account.UserAccountRole;
import pl.kul.blog.infrastructure.clock.ClockProvider;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Component
public class UserCreator {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClockProvider clockProvider;
    private final UserAccountAssignedRoleRepository userAccountAssignedRoleRepository;

    public UserAccount create(CreateUserCommand createUserCommand) {
        UserAccount createdUser = userAccountRepository.save(UserAccount.builder()
                .name(createUserCommand.name)
                .secondName(createUserCommand.secondName)
                .username(createUserCommand.username)
                .password(passwordEncoder.encode(createUserCommand.password))
                .registeredAt(clockProvider.instant())
                .isExpired(false)
                .isAccountLocked(false)
                .isCredentialsExpired(false)
                .isEnabled(true)
                .build());

        UserAccountAssignedRole userAccountAssignedRole = userAccountAssignedRoleRepository.save(UserAccountAssignedRole.builder()
                .role(UserAccountRole.REGULAR)
                .userAccount(createdUser)
                .build());

        createdUser.setRoles(new HashSet<>(Set.of(userAccountAssignedRole)));
        return userAccountRepository.save(createdUser);
    }
}
