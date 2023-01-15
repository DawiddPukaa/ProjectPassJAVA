package pl.kul.blog.helpers.assertions.signin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.kul.blog.infrastructure.api.user.authentication.SigninResponse;

@Component
@AllArgsConstructor
public class SigninResponseAssertionsAbility {
    private final ObjectMapper objectMapper;

    public SigninResponseAssertions assertThat(ResponseEntity<String> response) {
        try {
            return new SigninResponseAssertions(response, objectMapper.readValue(response.getBody(), SigninResponse.class));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
