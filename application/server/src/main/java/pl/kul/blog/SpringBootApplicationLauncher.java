package pl.kul.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationLauncher.class, args);
    }

}
