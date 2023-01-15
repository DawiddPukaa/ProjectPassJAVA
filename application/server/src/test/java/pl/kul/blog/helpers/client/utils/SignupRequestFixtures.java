package pl.kul.blog.helpers.client.utils;

public class SignupRequestFixtures {
    public static SignupRequest.SignupRequestBuilder validRequest() {
        return SignupRequest.builder()
            .username("some-user")
            .password("some-password");
    }
}
