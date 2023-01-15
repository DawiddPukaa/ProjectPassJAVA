package pl.kul.blog.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.kul.blog.domain.comment.Comment;
import pl.kul.blog.domain.user.account.UserAccount;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"postedBy", "comments"})
@ToString(exclude = {"postedBy", "comments"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue
    UUID id;

    String subject;
    @Lob
    String content;
    Instant postedOn;

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)

    UserAccount postedBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "postedWithin")
    Set<Comment> comments;

    @Version
    Long version;
}
