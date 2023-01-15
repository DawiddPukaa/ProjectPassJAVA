package pl.kul.blog.infrastructure.api.posts;

import lombok.Builder;
import lombok.Value;
import pl.kul.blog.domain.comment.Comment;
import pl.kul.blog.domain.post.PostWithComments;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class GetBlogPostResponse {
    String subject;
    String content;
    String postedBy;
    String postedOn;
    List<CommentResponse> comments;

    public static GetBlogPostResponse fromDomain(PostWithComments input) {
        return GetBlogPostResponse.builder()
            .subject(input.getPost().getSubject())
            .content(input.getPost().getContent())
            .postedBy(input.getPost().getPostedBy().getUsername())
            .postedOn(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(input.getPost().getPostedOn().atZone(ZoneId.of("Europe/Warsaw"))))
            .comments(
                input.getComments()
                    .stream()
                    .sorted(Comparator.comparing(Comment::getPostedOn))
                    .map(it -> CommentResponse.builder()
                        .author(it.getPostedBy().getUsername())
                        .commentPostedTimeAgo(calculateTimeAgo(it.getPostedOn()))
                        .content(it.getContent())
                        .build()
                    )
                    .collect(Collectors.toList())
            )
            .build();
    }

    private static String calculateTimeAgo(Instant postedOn) {
        ZoneId zoneId = ZoneId.of("Europe/Warsaw");
        ZonedDateTime postedOnDateTime = postedOn.atZone(zoneId);
        ZonedDateTime now = ZonedDateTime.now(zoneId);


        Period between = Period.between(postedOnDateTime.toLocalDate(), now.toLocalDate()).normalized();

        if (between.getYears() > 0) {
            return between.getYears() + " lat temu";
        } else if (between.getMonths() > 0) {
            return between.getMonths() + " miesięcy temu";
        } else if (between.getDays() > 0) {
            return between.getDays() + " dni temu";
        } else {
            Duration betweenClock = Duration.between(postedOnDateTime.toLocalTime(), now.toLocalTime());

            if (betweenClock.toHours() > 0) {
                return betweenClock.toHours() + " godzin temu";
            } else if (betweenClock.toMinutes() > 2) {
                return betweenClock.toMinutes() + " minut temu";
            } else {
                return "chwilę temu";
            }
        }
    }

    @Value
    @Builder
    static class CommentResponse {
        String author;
        String commentPostedTimeAgo;
        String content;
    }
}
