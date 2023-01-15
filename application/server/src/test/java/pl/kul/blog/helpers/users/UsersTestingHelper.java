package pl.kul.blog.helpers.users;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.user.CreateUserCommand;
import pl.kul.blog.domain.user.UserSignupService;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserAccountRepository;
import pl.kul.blog.domain.user.account.UserDeviceRepository;

import java.util.function.Function;

@AllArgsConstructor
@Component
public class UsersTestingHelper {
    private final UserAccountRepository userAccountRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final UserSignupService userSignupService;

    public void userExists(Function<AlreadyExistingUserFixtures, AlreadyExistingUser> configurer) {
        AlreadyExistingUserFixtures fixtures = new AlreadyExistingUserFixtures();

        AlreadyExistingUser configuredUser = configurer.apply(fixtures);

        userSignupService.signupNewUser(new CreateUserCommand(configuredUser.name, configuredUser.secondName, configuredUser.username, configuredUser.password));
    }

    public String tokenOf(AlreadyExistingUser user) {
        UserAccount userAccount = userAccountRepository.findByUsername(user.username)
            .get();

        return userDeviceRepository.findByUserAccount(userAccount)
            .get()
            .getToken();
    }
}
