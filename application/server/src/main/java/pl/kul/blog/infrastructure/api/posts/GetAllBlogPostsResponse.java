package pl.kul.blog.infrastructure.api.posts;

import lombok.Value;

import java.util.List;

@Value
class GetAllBlogPostsResponse {
    List<PostResponse> posts;
}
