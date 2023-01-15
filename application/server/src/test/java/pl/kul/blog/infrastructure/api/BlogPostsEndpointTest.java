package pl.kul.blog.infrastructure.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.kul.blog.helpers.users.UsersTestingHelper;

import static pl.kul.blog.helpers.users.AlreadyExistingUserFixtures.janusz;
class BlogPostsEndpointTest extends BaseIntegrationTest {
    @Autowired
    UsersTestingHelper users;

    @Test
    void when_fetching_blog_posts_as_anonymous_then_should_return_401() {
        // when
        ResponseEntity<String>   response = testing.asAnonymous().sendGetBlogPosts();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void when_fetching_blog_posts_as_existing_user_then_should_return_200_with_posts() {
        // given
        users.userExists(it -> it.janusz());

        // when
        ResponseEntity<String> response = testing.as(janusz()).sendGetBlogPosts();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
