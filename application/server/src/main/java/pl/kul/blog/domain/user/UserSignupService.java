package pl.kul.blog.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserDevice;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserSignupService {
    private final UserCreator userCreator;
    private final DeviceAuthenticationService deviceAuthenticationService;

    @Transactional
    public SignupResult signupNewUser(CreateUserCommand createUserCommand) {
        UserAccount userAccount = userCreator.create(createUserCommand);
        UserDevice userDevice = deviceAuthenticationService.authenticateFirstTime(userAccount);

        return new SignupResult(userDevice.getToken());
    }
}
