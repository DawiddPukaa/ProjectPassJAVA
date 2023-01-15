package pl.kul.blog.helpers.users;

public class AlreadyExistingUserFixtures {
    public static AlreadyExistingUser janusz() {
        return new AlreadyExistingUser()
            .withName("Janusz")
            .withSecondName("Januszowy")
            .withUsername("janusz")
            .withPassword("test");
    }

    public static AlreadyExistingUser zbigniew() {
        return new AlreadyExistingUser()
            .withName("Zbigniew")
            .withSecondName("Zbigniewowy")
            .withUsername("zbigniew")
            .withPassword("test");
    }
}
