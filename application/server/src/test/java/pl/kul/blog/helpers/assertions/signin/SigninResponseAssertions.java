package pl.kul.blog.helpers.assertions.signin;

import org.springframework.http.ResponseEntity;
import pl.kul.blog.infrastructure.api.user.authentication.SigninResponse;

public class SigninResponseAssertions {
    private final ResponseEntity<String> response;
    private final SigninResponse body;

    public SigninResponseAssertions(ResponseEntity<String> response, SigninResponse body) {
        this.response = response;
        this.body = body;
    }
}
