package pl.kul.blog.helpers.blogposts;

import pl.kul.blog.helpers.users.AlreadyExistingUserFixtures;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

public class AlreadyExistingPostFixtures {
    public static AlreadyExistingPost somePost() {
        return new AlreadyExistingPost()
            .withId(UUID.randomUUID())
            .withSubject("Example post subject")
            .withContent("Example post content")
            .withPostedOn(Instant.now().minus(Duration.ofDays(1)))
            .postedBy(AlreadyExistingUserFixtures.janusz())
            .withComments(new HashSet<>())
            .withVersion(0L);
    }
}
