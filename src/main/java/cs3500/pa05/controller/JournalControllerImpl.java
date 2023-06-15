package cs3500.pa05.controller;

import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.Category;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
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

/**
 * Represents a controller for a bullet journal.
 */
public class JournalControllerImpl implements JournalController {
    private final EntryComponentFactory factory = new EntryComponentFactory();
    private BulletJournal journal;
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

    private BulletJournal load(String file) {
        // TODO: loads .bujo file
        return null;
    }

    private void save(String file) {
        // TODO: saves current bullet journal to file
    }

    private void AddEntry(Entry entry) {
        // TODO: implement
    }

    private void addCategory(String category) {
        Category.add(category);
    }

    private void removeCategory(String category) {
        // TODO: decide if we actually need this and an associated button
        Category.remove(category);
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
