package pl.kul.blog.helpers.blogposts;

import pl.kul.blog.domain.post.Post;
import pl.kul.blog.helpers.users.UserAccountFixtures;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class PostFixtures {
    public static Post.PostBuilder somePost() {
        return Post.builder()
            .id(UUID.randomUUID())
            .subject("Subject of example post")
            .content("Content of example post")
            .postedBy(UserAccountFixtures.someUserAccount()
                .build())
            .postedOn(Instant.now().minus(Duration.ofDays(1)));
    }
}
