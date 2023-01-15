package pl.kul.blog.infrastructure.repositories.userdevice;

import lombok.AllArgsConstructor;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserAccountRepository;
import pl.kul.blog.domain.user.account.UserDevice;
import pl.kul.blog.domain.user.account.UserDeviceRepository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class VulnerableUserDeviceRepository implements UserDeviceRepository {
    private final DataSource dataSource;
    private final UserAccountRepository userAccountRepository;
    private final JpaBasedUserDeviceRepository jpaBasedUserDeviceRepository;

    @Override
    public Optional<UserDevice> findByUserAccount(UserAccount userAccount) {
        return jpaBasedUserDeviceRepository.findByUserAccount(userAccount);
    }

    @Override
    public Optional<UserDevice> findByToken(String token) {
        try (
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            statement.execute(String.format(
                "select id, generated_at, last_used_at, token, user_account_id, version from user_device where token = '%s'",
                token
            ));

            try (ResultSet resultSet = statement.getResultSet()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                } else {
                    return Optional.of(
                        UserDevice.builder()
                            .id(toUUID(resultSet.getBytes(1)))
                            .generatedAt(resultSet.getTimestamp(2).toInstant())
                            .lastUsedAt(Optional.ofNullable(resultSet.getTimestamp(3)).map(Timestamp::toInstant).orElse(null))
                            .token(resultSet.getString(4))
                            .userAccount(userAccountRepository.findById(toUUID(resultSet.getBytes(5))).get())
                            .version(resultSet.getLong(6))
                            .build()
                    );
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public UserDevice save(UserDevice userDevice) {
        return jpaBasedUserDeviceRepository.save(userDevice);
    }

    private UUID toUUID(byte[] bytes) throws SQLException {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }
}
