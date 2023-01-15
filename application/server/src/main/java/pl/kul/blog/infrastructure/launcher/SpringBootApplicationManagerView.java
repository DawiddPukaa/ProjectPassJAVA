package pl.kul.blog.infrastructure.launcher;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SpringBootApplicationManagerView {
    public SpringBootApplicationManagerView(SpringBootApplicationmanagerViewModel viewModel, Stage stage) {
        // Kontrolki uzupełniane przez użytkownika
        TextField databaseDirectoryTextField = new TextField();
        Button chooseDatabaseDirectoryFromFileSystemButton = new Button("...");
        Button toggleApplicationPowerButton = new Button();
        CheckBox enabledVulnerabilityOnAuthenticationTokenCheck = new CheckBox("Sprawdzanie tokena użytkownika przy uwierzytelnianiu");
        CheckBox enabledVulnerabilityOnAddingComments = new CheckBox("Dodawanie komentarzy do wpisów");

        databaseDirectoryTextField.disableProperty().bind(viewModel.canChangeDatabaseLocationDirectory());
        databaseDirectoryTextField.textProperty().bindBidirectional(viewModel.databaseLocationDirectory());

        chooseDatabaseDirectoryFromFileSystemButton.disableProperty().bind(viewModel.canChooseDatabaseDirectoryFromFileSystem());

        enabledVulnerabilityOnAuthenticationTokenCheck.disableProperty().bind(viewModel.canChangeVulnerabilityOnAuthenticationTokenCheck());
        enabledVulnerabilityOnAuthenticationTokenCheck.selectedProperty().bindBidirectional(viewModel.vulnerabilityOnAuthenticationTokenCheckEnabled());

        enabledVulnerabilityOnAddingComments.disableProperty().bind(viewModel.canChangeVulnerabilityOnAddingComments());
        enabledVulnerabilityOnAddingComments.selectedProperty().bindBidirectional(viewModel.vulnerabilityOnAddingCommentsEnabled());

        toggleApplicationPowerButton.disableProperty().bind(viewModel.cannotToggleApplicationPower());
        toggleApplicationPowerButton.textProperty().bind(viewModel.toggleApplicationPowerLabel());
        toggleApplicationPowerButton.setOnAction(event -> viewModel.toggleApplicationPower());

        // Zgrupowanie komponentów
        HBox databaseDirectoryLayout = new HBox();
        databaseDirectoryLayout.setSpacing(5);
        databaseDirectoryLayout.setAlignment(Pos.BASELINE_CENTER);
        databaseDirectoryTextField.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(databaseDirectoryTextField, Priority.ALWAYS);
        chooseDatabaseDirectoryFromFileSystemButton.setMinWidth(Region.USE_PREF_SIZE);
        databaseDirectoryLayout.getChildren().addAll(databaseDirectoryTextField, chooseDatabaseDirectoryFromFileSystemButton);

        VBox vulnerableFunctionalitiesCheckboxLayout = new VBox();
        vulnerableFunctionalitiesCheckboxLayout.setSpacing(5);
        vulnerableFunctionalitiesCheckboxLayout.setAlignment(Pos.BASELINE_LEFT);
        vulnerableFunctionalitiesCheckboxLayout.getChildren().addAll(
            enabledVulnerabilityOnAuthenticationTokenCheck,
            enabledVulnerabilityOnAddingComments
        );

        // Tabelka wyświetlająca komponenty
        GridPane settingsFormGrid = new GridPane();
        settingsFormGrid.setHgap(10);
        settingsFormGrid.setVgap(10);
        settingsFormGrid.addRow(settingsFormGrid.getRowCount(), new Label("Ścieżka do katalogu przechowującego bazę danych:"), databaseDirectoryLayout);
        settingsFormGrid.addRow(settingsFormGrid.getRowCount(), new Label("Funkcjonalności podatne na SQL Injection:"), vulnerableFunctionalitiesCheckboxLayout);

        ColumnConstraints leftColumnConstraints = new ColumnConstraints();
        leftColumnConstraints.setHalignment(HPos.RIGHT);
        leftColumnConstraints.setHgrow(Priority.NEVER);

        ColumnConstraints rightColumnConstraints = new ColumnConstraints();
        rightColumnConstraints.setHgrow(Priority.ALWAYS);

        settingsFormGrid.getColumnConstraints().addAll(leftColumnConstraints, rightColumnConstraints);
        List<RowConstraints> constraints = IntStream.rangeClosed(1, settingsFormGrid.getRowCount())
            .mapToObj(rowIndex -> {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.BASELINE);
                return rowConstraints;
            })
            .collect(Collectors.toList());
        settingsFormGrid.getRowConstraints().addAll(constraints);

        HBox actionButtonsBar = new HBox(toggleApplicationPowerButton);
        actionButtonsBar.setSpacing(5);
        actionButtonsBar.setAlignment(Pos.BASELINE_CENTER);
        toggleApplicationPowerButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(toggleApplicationPowerButton, Priority.ALWAYS);

        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(
            settingsFormGrid,
            actionButtonsBar
        );

        stage.setOnCloseRequest(event -> viewModel.windowCloseRequested());

        stage.setScene(new Scene(mainLayout));
        stage.setResizable(false);
        stage.setWidth(800);
        stage.setTitle("Aplikacja");
        stage.show();
    }
}
