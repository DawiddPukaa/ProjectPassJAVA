package pl.kul.blog.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserAccountRepository;
import pl.kul.blog.domain.user.account.UserDevice;
import pl.kul.blog.domain.user.account.UserDeviceRepository;
import pl.kul.blog.infrastructure.clock.ClockProvider;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@AllArgsConstructor
@Component
public class DeviceAuthenticationService {
    private final UserDeviceRepository userDeviceRepository;
    private final UserAccountRepository userAccountRepository;
    private final ClockProvider clockProvider;

    @Transactional
    public UserDevice authenticateNewDeviceFor(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("No such user with usename: " + username));

        return userDeviceRepository.findByUserAccount(userAccount)
            .map(it -> regenertate(it))
            .orElseGet(() -> generateFor(userAccount));
    }

    @Transactional
    public UserDevice authenticateFirstTime(UserAccount userAccount) {
        return generateFor(userAccount);
    }

    private UserDevice generateFor(UserAccount userAccount) {
        UserDevice userDevice = UserDevice.builder()
            .userAccount(userAccount)
            .token(generateToken())
            .generatedAt(clockProvider.instant())
            .build();

        return userDeviceRepository.save(userDevice);
    }

    private UserDevice regenertate(UserDevice userDevice) {
        userDevice.setGeneratedAt(clockProvider.instant());
        userDevice.setToken(generateToken());
        userDevice.setLastUsedAt(null);

        return userDeviceRepository.save(userDevice);
    }

    private String generateToken() {
        try {
            byte[] randomBytes = new byte[64];
            SecureRandom.getInstanceStrong().nextBytes(randomBytes);

            return Base64.getEncoder().encodeToString(randomBytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
