package pl.kul.blog.infrastructure.repositories.userdevice;

import lombok.AllArgsConstructor;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserDevice;
import pl.kul.blog.domain.user.account.UserDeviceRepository;

import java.util.Optional;

@AllArgsConstructor
public class DelegatingUserDeviceRepository implements UserDeviceRepository {
    private final JpaBasedUserDeviceRepository userDeviceRepository;

    @Override
    public Optional<UserDevice> findByUserAccount(UserAccount userAccount) {
        return userDeviceRepository.findByUserAccount(userAccount);
    }

    @Override
    public Optional<UserDevice> findByToken(String token) {
        return userDeviceRepository.findByToken(token);
    }

    @Override
    public UserDevice save(UserDevice userDevice) {
        return userDeviceRepository.save(userDevice);
    }
}
