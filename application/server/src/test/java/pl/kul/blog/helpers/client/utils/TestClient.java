package pl.kul.blog.helpers.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kul.blog.helpers.users.AlreadyExistingUser;
import pl.kul.blog.infrastructure.api.user.authentication.SignupResponse;

import java.net.URI;
import java.util.Map;

public class TestClient {
    private final TestRestTemplate restTemplate;
    private final URI baseUri;
    private final String token;

    public TestClient(TestRestTemplate restTemplate, URI baseUri) {
        this.restTemplate = restTemplate;
        this.baseUri = baseUri;
        token = null;
    }

    public TestClient(ObjectMapper objectMapper, TestRestTemplate restTemplate, URI baseUri, AlreadyExistingUser authenticatedAs) {
        this.restTemplate = restTemplate;
        this.baseUri = baseUri;

        try {
            SigninRequest.SigninRequestBuilder signupRequestBuilder = SigninRequestFixtures.existingUser(authenticatedAs);
            ResponseEntity<String> response = sendPostSignin(signupRequestBuilder);
            SignupResponse signupResponse = objectMapper.readValue(response.getBody(), SignupResponse.class);

            this.token = signupResponse.getToken();
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public ResponseEntity<String> sendPostSignup(SignupRequest.SignupRequestBuilder request) {
        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
            .post(UriComponentsBuilder.fromUri(baseUri)
                .path("/authentication/signup")
                .build()
                .toUri()
            )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        RequestEntity<SignupRequest> requestEntity = decorated(requestEntityBuilder)
            .body(request.build());

        return restTemplate.exchange(requestEntity, String.class);
    }

    public ResponseEntity<String> sendPostSignin(SigninRequest.SigninRequestBuilder request) {
        SigninRequest signinRequest = request.build();

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("username", signinRequest.getUsername());
        formParams.add("password", signinRequest.getPassword());

        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
            .post(UriComponentsBuilder.fromUri(baseUri)
                .path("/authentication/signin")
                .build()
                .toUri()
            )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        RequestEntity<MultiValueMap<String, String>> requestEntity = decorated(requestEntityBuilder)
            .body(formParams);

        return restTemplate.exchange(requestEntity, String.class);
    }

    public ResponseEntity<String> sendGetBlogPosts() {
        RequestEntity.HeadersBuilder<?> requestEntityBuilder = RequestEntity
            .get(UriComponentsBuilder.fromUri(baseUri)
                .path("/api/posts")
                .build()
                .toUri()
            )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        RequestEntity<Void> requestEntity = decorated(requestEntityBuilder)
            .build();

        return restTemplate.exchange(requestEntity, String.class);
    }

    public ResponseEntity<String> sendPutAddComment(AddCommentRequest request) {
        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
            .put(UriComponentsBuilder.fromUri(baseUri)
                .path("/api/comments/{id}")
                .build(
                    Map.of(
                        "id", request.commentId
                    )
                )
            )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        RequestEntity<AddCommentRequest.RequestBody> requestEntity = decorated(requestEntityBuilder)
            .body(request.body);

        return restTemplate.exchange(requestEntity, String.class);
    }

    public ResponseEntity<String> sendPostBlogPost(AddPostRequest.AddPostRequestBuilder requestBuilder) {
        AddPostRequest request = requestBuilder.build();

        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
            .put(UriComponentsBuilder.fromUri(baseUri)
                .path("/api/posts/{id}")
                .build(
                    Map.of(
                        "id", request.getPostId()
                    )
                )
            )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        RequestEntity<AddPostRequest.Body> requestEntity = decorated(requestEntityBuilder)
            .body(request.getBody());

        return restTemplate.exchange(requestEntity, String.class);
    }

    private RequestEntity.HeadersBuilder<?> decorated(RequestEntity.HeadersBuilder<?> input) {
        if (token != null) {
            return input.header(HttpHeaders.AUTHORIZATION, "token " + token);
        } else {
            return input;
        }
    }

    private RequestEntity.BodyBuilder decorated(RequestEntity.BodyBuilder input) {
        if (token != null) {
            return input.header(HttpHeaders.AUTHORIZATION, "token " + token);
        } else {
            return input;
        }
    }
}
