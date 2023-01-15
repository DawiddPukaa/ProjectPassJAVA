package pl.kul.blog.infrastructure.launcher;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

class ApplicationConfigurationBuilder {
    private boolean vulnerabilityOnAuthenticationTokenCheckEnabled;
    private boolean vulnerabilityOnAddingCommentsEnabled;
    private String databaseLocationDirectory;

    public ApplicationConfigurationBuilder vulnerabilityOnAuthenticationTokenCheckEnabled(boolean value) {
        vulnerabilityOnAuthenticationTokenCheckEnabled = value;
        return this;
    }

    public ApplicationConfigurationBuilder vulnerabilityOnAddingCommentsEnabled(boolean value) {
        vulnerabilityOnAddingCommentsEnabled = value;
        return this;
    }

    public ApplicationConfigurationBuilder databaseLocationDirectory(String path) {
        databaseLocationDirectory = path;
        return this;
    }

    public String[] toParams() {
        return Stream
            .of(
                activeProfiles(),
                setDatabaseLocationPath()
            )
            .filter(Objects::nonNull)
            .toArray(String[]::new);
    }

    private String setDatabaseLocationPath() {
        return Optional.ofNullable(databaseLocationDirectory)
            .map(it -> Paths.get(it).normalize())
            .map(it -> "--database.location=" + it)
            .orElse(null);
    }

    private String activeProfiles() {
        List<String> activeProfiles = new ArrayList<>();
        if (vulnerabilityOnAuthenticationTokenCheckEnabled) activeProfiles.add("token-auth-check-vulnerable-to-sql-injection");
        if (vulnerabilityOnAddingCommentsEnabled) activeProfiles.add("comments-vulnerable-to-sql-injection");

        return Optional.of(activeProfiles)
            .map(it -> String.join(",", it))
            .filter(it -> !it.isBlank())
            .map(it -> "--spring.profiles.active=" + it)
            .orElse(null);
    }
}
