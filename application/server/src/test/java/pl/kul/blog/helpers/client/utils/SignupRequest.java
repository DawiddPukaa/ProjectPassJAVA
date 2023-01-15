package pl.kul.blog.helpers.client.utils;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignupRequest {
    String username;
    String password;
}
