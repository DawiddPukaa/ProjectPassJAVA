package pl.kul.blog.infrastructure.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import pl.kul.blog.domain.post.PostRepository
import pl.kul.blog.domain.user.account.UserAccountAssignedRoleRepository
import pl.kul.blog.domain.user.account.UserAccountRepository
import pl.kul.blog.helpers.client.utils.TestClientProvider
import pl.kul.blog.infrastructure.repositories.comment.JpaBasedCommentRepository
import pl.kul.blog.infrastructure.repositories.userdevice.JpaBasedUserDeviceRepository
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

@ActiveProfiles("it")
class BaseIntegrationSpockTest extends Specification {

    @Autowired
    public TestClientProvider testing
    @Autowired
    private UserAccountRepository userAccountRepository
    @Autowired
    private UserAccountAssignedRoleRepository userAccountAssignedRoleRepository
    @Autowired
    private JpaBasedUserDeviceRepository userDeviceRepository
    @Autowired
    private PostRepository postRepository
    @Autowired
    private JpaBasedCommentRepository commentRepository
    @Autowired
    ApplicationContext applicationContext

    def "check application contex"(){
        given:
        applicationContext.getBean("testClientProvider")
    }

    def cleanup() {
        commentRepository.deleteAll();
        userDeviceRepository.deleteAll();
        userAccountAssignedRoleRepository.deleteAll();
        postRepository.deleteAll();
        userAccountRepository.deleteAll();
    }

}
