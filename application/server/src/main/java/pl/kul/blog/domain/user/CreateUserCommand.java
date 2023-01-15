package pl.kul.blog.domain.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserCommand {
    public final String name;
    public final String secondName;
    public final String username;
    public final String password;
}
