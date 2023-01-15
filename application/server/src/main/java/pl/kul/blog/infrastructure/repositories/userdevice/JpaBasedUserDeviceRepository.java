package pl.kul.blog.infrastructure.repositories.userdevice;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserDevice;

import java.util.Optional;
import java.util.UUID;

public interface JpaBasedUserDeviceRepository extends JpaRepository<UserDevice, UUID> {
    Optional<UserDevice> findByUserAccount(UserAccount userAccount);

    Optional<UserDevice> findByToken(String token);
}
