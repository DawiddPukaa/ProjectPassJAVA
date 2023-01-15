package pl.kul.blog.infrastructure.api.posts;

import lombok.Builder;
import lombok.Value;
import pl.kul.blog.domain.post.Post;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Value
@Builder
public class PostResponse {
    UUID id;
    String subject;
    String content;
    String postedOn;
    String postedBy;
    int numberOfComments;

    public static PostResponse fromDomain(Post input) {
        return PostResponse.builder()
            .id(input.getId())
            .subject(input.getSubject())
            .content(input.getContent())
            .postedBy(input.getPostedBy().getUsername())
            .postedOn(input.getPostedOn().atZone(ZoneId.of("Europe/Warsaw")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .build();
    }
}
