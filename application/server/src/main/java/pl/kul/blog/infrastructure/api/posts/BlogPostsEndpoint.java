package pl.kul.blog.infrastructure.api.posts;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kul.blog.domain.post.AddedPost;
import pl.kul.blog.domain.post.PostCreator;
import pl.kul.blog.domain.post.PostFinder;
import pl.kul.blog.domain.post.PostWithComments;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/posts")
@AllArgsConstructor
public class BlogPostsEndpoint {
    private final PostCreator postCreator;
    private final PostFinder postFinder;

    @PutMapping(
        path = "/{id}",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    public NewPostResponse newPost(
        @PathVariable("id") UUID postId,
        @RequestBody NewPostRequest newPostRequest
    ) {
        AddedPost result = postCreator.newPost(newPostRequest.toDomain(postId));

        return NewPostResponse.fromDomain(result);
    }

    @GetMapping
    public GetAllBlogPostsResponse getAllPosts() {
        return postFinder.findAll()
            .stream()
            .map(it -> PostResponse.fromDomain(it))
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> new GetAllBlogPostsResponse(list)
            ));
    }

    @GetMapping(path = "/{postId}")
    public GetBlogPostResponse getBlogPost(@PathVariable("postId") UUID postId) {
        PostWithComments postWithComments = postFinder.findById(postId);

        return GetBlogPostResponse.fromDomain(postWithComments);
    }
}

