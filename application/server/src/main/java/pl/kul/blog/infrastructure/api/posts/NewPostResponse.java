package pl.kul.blog.infrastructure.api.posts;

import lombok.Builder;
import lombok.Value;
import pl.kul.blog.domain.post.AddedPost;

@Value
@Builder
public class NewPostResponse {
    PostResponse addedPost;

    public static NewPostResponse fromDomain(AddedPost input) {
        return NewPostResponse.builder()
            .addedPost(PostResponse.fromDomain(input.getPost()))
            .build();
    }
}
