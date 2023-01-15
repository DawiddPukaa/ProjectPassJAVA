package pl.kul.blog.helpers.users;

public class AlreadyExistingUser {
    public String name;
    public String secondName;
    public String username;
    public String password;

    public AlreadyExistingUser withName(String name) {
        this.name = name;
        return this;
    }

    public AlreadyExistingUser withSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public AlreadyExistingUser withUsername(String username) {
        this.username = username;
        return this;
    }

    public AlreadyExistingUser withPassword(String password) {
        this.password = password;
        return this;
    }
}
