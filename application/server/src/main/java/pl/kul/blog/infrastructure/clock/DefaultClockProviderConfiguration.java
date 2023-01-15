package pl.kul.blog.infrastructure.clock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultClockProviderConfiguration {
    @ConditionalOnMissingBean(ClockProvider.class)
    @Bean
    ClockProvider clockProvider() {
        return new DefaultClockProvider();
    }
}
