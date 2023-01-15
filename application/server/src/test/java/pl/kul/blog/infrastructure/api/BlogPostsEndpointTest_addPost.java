package pl.kul.blog.infrastructure.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pl.kul.blog.helpers.client.utils.AddPostRequestFixtures;
import pl.kul.blog.helpers.users.UsersTestingHelper;

import static pl.kul.blog.helpers.users.AlreadyExistingUserFixtures.janusz;

public class BlogPostsEndpointTest_addPost extends BaseIntegrationTest {
    @Autowired
    UsersTestingHelper users;

    @Test
    void when_adding_post_as_authenticated_user_then_should_add_and_return_200() {
        // given
        users.userExists(it -> it.janusz());

        // when
        ResponseEntity<String> response = testing.as(janusz()).sendPostBlogPost(AddPostRequestFixtures.validRequest());

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
