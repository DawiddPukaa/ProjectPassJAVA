package pl.kul.blog.infrastructure.launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class SpringBootApplicationManager extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SpringBootApplicationmanagerViewModel viewModel = new SpringBootApplicationmanagerViewModel();

        new SpringBootApplicationManagerView(viewModel, primaryStage);
    }
}
