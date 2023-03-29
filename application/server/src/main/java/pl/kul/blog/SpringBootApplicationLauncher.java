package pl.kul.blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pl.kul.blog.uploadfilesmodule.FilesStorageService;

import javax.annotation.Resource;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootApplicationLauncher implements CommandLineRunner {

    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationLauncher.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
//    storageService.deleteAll();
        storageService.init();
    }

}
