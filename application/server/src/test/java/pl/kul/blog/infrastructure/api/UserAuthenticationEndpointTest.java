package pl.kul.blog.infrastructure.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.kul.blog.domain.user.account.UserAccountRepository;
import pl.kul.blog.helpers.client.utils.SigninRequest;
import pl.kul.blog.helpers.client.utils.SigninRequestFixtures;
import pl.kul.blog.helpers.client.utils.SignupRequest;
import pl.kul.blog.helpers.client.utils.SignupRequestFixtures;
import pl.kul.blog.helpers.users.UsersTestingHelper;

import static pl.kul.blog.helpers.users.AlreadyExistingUserFixtures.janusz;
class UserAuthenticationEndpointTest extends BaseIntegrationTest {
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    UsersTestingHelper users;

    @Test
    void when_signup_then_should_create_new_user_and_return_token() {
        // given
        SignupRequest.SignupRequestBuilder request = SignupRequestFixtures.validRequest().username("janusz").password("password");

        // when
        ResponseEntity<String> response = testing.asAnonymous().sendPostSignup(request);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // and
        Assertions.assertThat(userAccountRepository.findByUsername("janusz")).isPresent();
    }

    @Test
    void when_existing_user_trying_to_authenticate_then_should_return_new_token() {
        // given
        users.userExists(it -> janusz());
        SigninRequest.SigninRequestBuilder request = SigninRequestFixtures.existingUser(janusz());
        // and
        testing.asAnonymous().sendPostSignin(request);
        // and
        String previousTokenOfJanusz = users.tokenOf(janusz());

        // when
        ResponseEntity<String> response = testing.asAnonymous().sendPostSignin(request);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // and
        Assertions.assertThat(users.tokenOf(janusz())).isNotEqualTo(previousTokenOfJanusz);
    }
}
