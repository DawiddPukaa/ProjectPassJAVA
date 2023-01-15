package pl.kul.blog.helpers.blogposts;

import pl.kul.blog.domain.comment.Comment;
import pl.kul.blog.helpers.users.AlreadyExistingUser;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AlreadyExistingPost {
    UUID id;
    String subject;
    String content;
    Instant postedOn;
    AlreadyExistingUser postedBy;
    Set<Comment> comments;
    Long version;

    public AlreadyExistingPost withId(UUID id) {
        this.id = id;
        return this;
    }

    public AlreadyExistingPost withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public AlreadyExistingPost withContent(String content) {
        this.content = content;
        return this;
    }

    public AlreadyExistingPost withPostedOn(Instant postedOn) {
        this.postedOn = postedOn;
        return this;
    }

    public AlreadyExistingPost postedBy(AlreadyExistingUser postedBy) {
        this.postedBy = postedBy;
        return this;
    }

    public AlreadyExistingPost withComments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public AlreadyExistingPost withVersion(Long version) {
        this.version = version;
        return this;
    }

    public AlreadyExistingPost withNoComments() {
        this.comments = new HashSet<>();
        return this;
    }
}
