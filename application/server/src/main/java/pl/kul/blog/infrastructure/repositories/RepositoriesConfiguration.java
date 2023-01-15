package pl.kul.blog.infrastructure.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.kul.blog.domain.comment.CommentRepository;
import pl.kul.blog.domain.user.account.UserAccountRepository;
import pl.kul.blog.domain.user.account.UserDeviceRepository;
import pl.kul.blog.infrastructure.repositories.comment.DelegatingCommentRepository;
import pl.kul.blog.infrastructure.repositories.comment.JpaBasedCommentRepository;
import pl.kul.blog.infrastructure.repositories.comment.VulnerableCommentRepository;
import pl.kul.blog.infrastructure.repositories.userdevice.DelegatingUserDeviceRepository;
import pl.kul.blog.infrastructure.repositories.userdevice.JpaBasedUserDeviceRepository;
import pl.kul.blog.infrastructure.repositories.userdevice.VulnerableUserDeviceRepository;

import javax.sql.DataSource;

@Configuration
public class RepositoriesConfiguration {
    @Profile("token-auth-check-vulnerable-to-sql-injection")
    @Bean
    UserDeviceRepository vulnerableUserDeviceRepository(DataSource dataSource, UserAccountRepository userAccountRepository, JpaBasedUserDeviceRepository jpaBasedUserDeviceRepository) {
        return new VulnerableUserDeviceRepository(dataSource, userAccountRepository, jpaBasedUserDeviceRepository);
    }

    @Profile("!token-auth-check-vulnerable-to-sql-injection")
    @Bean
    UserDeviceRepository userDeviceRepository(JpaBasedUserDeviceRepository userDeviceRepository) {
        return new DelegatingUserDeviceRepository(userDeviceRepository);
    }

    @Profile("comments-vulnerable-to-sql-injection")
    @Bean
    CommentRepository vulnerableCommentRepository(DataSource dataSource) {
        return new VulnerableCommentRepository(dataSource);
    }

    @Profile("!comments-vulnerable-to-sql-injection")
    @Bean
    CommentRepository commentRepository(JpaBasedCommentRepository commentRepository) {
        return new DelegatingCommentRepository(commentRepository);
    }
}
