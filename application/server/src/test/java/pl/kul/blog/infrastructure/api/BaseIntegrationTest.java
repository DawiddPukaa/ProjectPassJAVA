package pl.kul.blog.infrastructure.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.kul.blog.domain.post.PostRepository;
import pl.kul.blog.domain.user.account.UserAccountAssignedRoleRepository;
import pl.kul.blog.domain.user.account.UserAccountRepository;
import pl.kul.blog.helpers.client.utils.TestClientProvider;
import pl.kul.blog.infrastructure.repositories.comment.JpaBasedCommentRepository;
import pl.kul.blog.infrastructure.repositories.userdevice.JpaBasedUserDeviceRepository;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

@ActiveProfiles(profiles = {"integration"})
public class BaseIntegrationTest {
    @Autowired
    public TestClientProvider testing;

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserAccountAssignedRoleRepository userAccountAssignedRoleRepository;
    @Autowired
    private JpaBasedUserDeviceRepository userDeviceRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JpaBasedCommentRepository commentRepository;

    @AfterEach
    public void tearDown() {
        commentRepository.deleteAll();
        userDeviceRepository.deleteAll();
        userAccountAssignedRoleRepository.deleteAll();
        postRepository.deleteAll();
        userAccountRepository.deleteAll();
    }
}
