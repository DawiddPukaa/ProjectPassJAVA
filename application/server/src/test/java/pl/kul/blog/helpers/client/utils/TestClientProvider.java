package pl.kul.blog.helpers.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kul.blog.helpers.users.AlreadyExistingUser;

import java.net.URI;

@Lazy
@Component
public class TestClientProvider {
    private final TestRestTemplate restTemplate;
    private final URI baseUri;
    private final ObjectMapper objectMapper;

    public TestClientProvider(TestRestTemplate restTemplate, @LocalServerPort int localServerPort, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

        baseUri = UriComponentsBuilder.fromHttpUrl("http://localhost")
            .port(localServerPort)
            .build()
            .toUri();
    }

    public TestClient asAnonymous() {
        return new TestClient(restTemplate, baseUri);
    }

    public TestClient as(AlreadyExistingUser userBuilder) {

        return new TestClient(objectMapper, restTemplate, baseUri, userBuilder);
    }
}
