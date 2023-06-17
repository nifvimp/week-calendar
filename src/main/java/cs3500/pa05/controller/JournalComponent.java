package cs3500.pa05.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 * Represents a controller for a bullet journal.
 */
public class JournalComponent extends BorderPane {
    // purposeful static so that specified configuration shared between all components
    private static final EntryComponentFactory factory = new EntryComponentFactory();
    private static final ObjectMapper mapper = new ObjectMapper();
    private BulletJournal journal;
    @FXML
    private TextField name;
    @FXML
    private Button save;
    @FXML
    private Button load;
    @FXML
    private Button addEntry;
    @FXML
    private Button addCategory;
    @FXML
    private TextField maxEvents;
    @FXML
    private TextField maxTasks;
    @FXML
    private MenuBar menuBar;
    @FXML
    private GridPane week;
    @FXML
    private ListView<String> taskQueue;
    @FXML
    private Group stats; // TODO: figure this out. prob need separate class
    @FXML
    private Map<DayOfWeek, ScrollPane> days; // TODO: figure this shit out too.
    private List<Group> entries; // TODO: idk how to format this properly under days

    /**
     * Creates a new journal component that displays the passed in bullet journal.
     *
     * @param journal journal to display
     * @param parent parent of the component
     */
    public JournalComponent(BulletJournal journal, Node parent) {
        this.journal = journal;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/journal.fxml"));
        fxmlLoader.setController(this);
        Node loaded;
        try {
            loaded = fxmlLoader.load();
            this.getChildren().add(loaded);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        ((BorderPane) loaded).prefHeightProperty().bind(parent.getScene().heightProperty());
        ((BorderPane) loaded).prefWidthProperty().bind(parent.getScene().widthProperty());
//        initComponents();
    }

    /**
     * Prompts the user to choose a file location to save the bullet journal.
     */
    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose save location");
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        try {
            Files.write(file.toPath(), mapper.writeValueAsString(journal).getBytes());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting journal to json", e);
        } catch (IOException e) {
            throw new RuntimeException(
                String.format("Error writing journal to choose file location '%s'", file), e
            );
        }
    }

    private void AddEntry() {
        // TODO: implement
    }

    private void addCategory() {
        // TODO: implement
    }

    private void removeCategory() {
        // TODO: implement
    }

    private void onUpdate() {
        // TODO: implement
        //  VERY IMPORTANT
    }

    private void initDays() {
        // TODO: implement
    }

    private void initStats() {
        // TODO: implement
        //  Section 3 - Weekly Overview
    }

    private void initTaskQueue() {
        // TODO: implement
        //  Section 2 - Task Queue
    }


}