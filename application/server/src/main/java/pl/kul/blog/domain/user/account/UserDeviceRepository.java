package pl.kul.blog.domain.user.account;

import java.util.Optional;

public interface UserDeviceRepository {
    Optional<UserDevice> findByUserAccount(UserAccount userAccount);

    Optional<UserDevice> findByToken(String token);

    UserDevice save(UserDevice userDevice);
}
