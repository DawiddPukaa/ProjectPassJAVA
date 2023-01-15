package pl.kul.blog.helpers.client.utils;

import pl.kul.blog.helpers.users.AlreadyExistingUser;

public class SigninRequestFixtures {
    public static SigninRequest.SigninRequestBuilder validRequest() {
        return SigninRequest.builder()
            .username("some-user")
            .password("some-password");
    }

    public static SigninRequest.SigninRequestBuilder existingUser(AlreadyExistingUser user) {
        return validRequest()
            .username(user.username)
            .password(user.password);
    }
}
