package cs3500.pa05.controller;

import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Represents a controller for a bullet journal.
 */
public class JournalControllerImpl implements JournalController {
    private final EntryComponentFactory factory = new EntryComponentFactory();
    private BulletJournal journal;
    @FXML
    private Stage primaryStage;
    @FXML
    private TextField journalName;
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
    private Map<DayOfWeek, ScrollPane> days; // TODO: figure this shit out too.
    private List<Group> entries; // TODO: idk how to format this properly under days

    public void run() {

    }

    private BulletJournal load() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .bujo File");
        File file = fileChooser.showOpenDialog(primaryStage);
        // TODO: check if the file is .bujo and open it using the jackson library
        //  do a null check too.
        return null;
    }

    private void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose save location");
        File path = fileChooser.showSaveDialog(primaryStage);
        // TODO: saves current bullet journal to path above
        //  do a null check too.
    }

    private void AddEntry(Entry entry) {
        // TODO: implement
    }

    private void addCategory(String category) {
        journal.addCategory(category);
    }

    private void removeCategory(String category) {
        journal.removeCategory(category);
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

    private void initMenuBar() {
        // TODO: implement
        //  Section 2 - Menu Bar & Shortcuts
    }
}
