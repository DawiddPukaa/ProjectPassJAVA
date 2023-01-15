package pl.kul.blog.infrastructure.api.user.authentication;

import lombok.Value;
import pl.kul.blog.domain.user.account.UserDevice;

@Value
public class SigninResponse {
    String token;

    public static SigninResponse fromDomain(UserDevice userDevice) {
        return new SigninResponse(userDevice.getToken());
    }
}
