package pl.kul.blog.infrastructure.launcher;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kul.blog.SpringBootApplicationLauncher;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SpringBootApplicationmanagerViewModel {
    private final AtomicReference<ConfigurableApplicationContext> applicationContext = new AtomicReference<>();

    private final BooleanProperty awaitingApplicationIsStarted = new SimpleBooleanProperty(false);
    private final BooleanProperty awaitingApplicationShutdown = new SimpleBooleanProperty(false);
    private final BooleanProperty applicationIsUp = new SimpleBooleanProperty(false);
    private final BooleanBinding cannotChangeConfiguration = awaitingApplicationIsStarted.or(applicationIsUp).or(awaitingApplicationShutdown);

    private final StringProperty databaseLocationDirectory = new SimpleStringProperty("~/.blog-application-server");
    private final BooleanProperty vulnerabilityOnAuthenticationTokenCheckEnabled = new SimpleBooleanProperty(false);
    private final BooleanProperty vulnerabilityOnAddingCommentsEnabled = new SimpleBooleanProperty(false);

    private final BooleanBinding formIsInvalid = databaseLocationDirectory.isEmpty();

    private final BooleanBinding cannotToggleApplicationPower = awaitingApplicationIsStarted.or(formIsInvalid);

    private final StringBinding toggleApplicationPowerLabel = Bindings
        .when(awaitingApplicationIsStarted).then("Uruchamianie aplikacji...")
        .otherwise(Bindings.when(awaitingApplicationShutdown).then("Zamykanie aplikacji...")
            .otherwise(Bindings.when(applicationIsUp).then("Wyłącz").otherwise("Włącz"))
        );

    public void toggleApplicationPower() {
        if (applicationIsUp.get()) {
            shutdownApplication();
        } else {
            startApplication();
        }
    }

    public void windowCloseRequested() {
        shutdownApplication();
    }

    private void startApplication() {
        awaitingApplicationIsStarted.set(true);

        ApplicationConfigurationBuilder configurationBuilder = new ApplicationConfigurationBuilder()
            .vulnerabilityOnAuthenticationTokenCheckEnabled(vulnerabilityOnAuthenticationTokenCheckEnabled.get())
            .vulnerabilityOnAddingCommentsEnabled(vulnerabilityOnAddingCommentsEnabled.get())
            .databaseLocationDirectory(databaseLocationDirectory.get());

        Single.defer(() -> Single.fromCallable(() -> SpringApplication.run(SpringBootApplicationLauncher.class, configurationBuilder.toParams())))
            .subscribeOn(Schedulers.io())
            .observeOn(JavaFxScheduler.platform())
            .doFinally(() -> awaitingApplicationIsStarted.set(false))
            .subscribe(applicationContext -> {
                this.applicationContext.set(applicationContext);
                applicationIsUp.set(true);
            });
    }

    private void shutdownApplication() {
        awaitingApplicationShutdown.set(true);
        Completable.defer(() -> Completable.fromRunnable(() -> Optional.ofNullable(applicationContext.get()).ifPresent(ConfigurableApplicationContext::close)))
            .subscribeOn(Schedulers.io())
            .observeOn(JavaFxScheduler.platform())
            .doFinally(() -> awaitingApplicationShutdown.set(false))
            .subscribe(() -> {
                this.applicationContext.set(null);
                applicationIsUp.set(false);
            });
    }

    public ObservableBooleanValue cannotToggleApplicationPower() {
        return cannotToggleApplicationPower;
    }

    public ObservableStringValue toggleApplicationPowerLabel() {
        return toggleApplicationPowerLabel;
    }

    public StringProperty databaseLocationDirectory() {
        return databaseLocationDirectory;
    }

    public ObservableBooleanValue canChangeDatabaseLocationDirectory() {
        return cannotChangeConfiguration;
    }

    public ObservableBooleanValue canChooseDatabaseDirectoryFromFileSystem() {
        return cannotChangeConfiguration;
    }

    public ObservableBooleanValue canChangeVulnerabilityOnAuthenticationTokenCheck() {
        return cannotChangeConfiguration;
    }

    public ObservableBooleanValue canChangeVulnerabilityOnAddingComments() {
        return cannotChangeConfiguration;
    }

    public BooleanProperty vulnerabilityOnAuthenticationTokenCheckEnabled() {
        return vulnerabilityOnAuthenticationTokenCheckEnabled;
    }

    public BooleanProperty vulnerabilityOnAddingCommentsEnabled() {
        return vulnerabilityOnAddingCommentsEnabled;
    }
}
