package pl.kul.blog.infrastructure.clock;

import java.time.Instant;

class DefaultClockProvider implements ClockProvider {

    @Override
    public Instant instant() {
        return Instant.now();
    }
}
