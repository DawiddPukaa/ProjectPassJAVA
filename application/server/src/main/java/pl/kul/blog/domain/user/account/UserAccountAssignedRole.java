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
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Version;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "role")
@ToString(exclude = "role")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountAssignedRole {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId
    UserAccount userAccount;

    UserAccountRole role;

    @Version
    Long version;
}
