package pl.kul.blog.infrastructure.api.user.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import pl.kul.blog.domain.user.SignupResult;

@Value
public class SignupResponse {
    String token;

    @JsonCreator
    public SignupResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    public static SignupResponse fromDomain(SignupResult signupResult) {
        return new SignupResponse(signupResult.getToken());
    }
}
