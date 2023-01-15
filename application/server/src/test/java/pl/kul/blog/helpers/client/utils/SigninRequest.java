package pl.kul.blog.helpers.client.utils;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SigninRequest {
    String username;
    String password;
}
