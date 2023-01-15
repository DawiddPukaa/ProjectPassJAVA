package pl.kul.blog.domain.user.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = "userAccount")
@ToString(exclude = "userAccount")
@Entity
@Table(
    indexes = @Index(columnList = "user_account_id", unique = true)
)
public class UserDevice {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    UserAccount userAccount;

    String token;
    Instant generatedAt;
    Instant lastUsedAt;

    @Version
    Long version;
}
