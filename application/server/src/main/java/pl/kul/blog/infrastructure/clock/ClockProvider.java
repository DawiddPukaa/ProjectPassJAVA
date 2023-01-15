package pl.kul.blog.infrastructure.clock;

import java.time.Instant;

public interface ClockProvider {
    Instant instant();
}

