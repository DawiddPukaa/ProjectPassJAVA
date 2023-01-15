package pl.kul.blog.domain.comment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.kul.blog.domain.post.Post;
import pl.kul.blog.domain.user.account.UserAccount;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue
    UUID id;

    @Lob
    String content;

    Instant postedOn;

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    UserAccount postedBy;

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    Post postedWithin;

    @Version
    Long version;
}
