package cs3500.pa05.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.FilterEvent;
import cs3500.pa05.model.FilterTask;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private GridPane days;
    private Map<DayOfWeek, VBox> content;

    /**
     * Creates a new journal component that displays the passed in bullet journal.
     *
     * @param journal journal to display
     * @param parent parent of the component
     */
    public JournalComponent(BulletJournal journal, Node parent) {
        this.content = new HashMap<>();
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
        initComponents();
        // TODO: fix
        ((BorderPane) loaded).prefHeightProperty().bind(parent.getScene().heightProperty());
        ((BorderPane) loaded).prefWidthProperty().bind(parent.getScene().widthProperty());
        this.addEventHandler(JournalEvent.ANY, new JournalEventHandler(this));
    }

    private void initComponents() {
        this.name.textProperty().set(this.journal.name());
        this.maxEvents.textProperty().set(String.valueOf(this.journal.getEventMax()));
        restrictToInteger(this.maxEvents);
        this.maxTasks.textProperty().set(String.valueOf(this.journal.getTaskMax()));
        restrictToInteger(this.maxTasks);
        this.save.setOnAction(e -> this.fireEvent(new JournalEvent(JournalEvent.SAVE)));
        this.load.setOnAction(e -> this.fireEvent(new JournalEvent(JournalEvent.LOAD)));
        this.addEntry.setOnAction(e -> {
            Entry entry = null; // TODO: make this open up a new dialog box that makes a new entry
            this.fireEvent(new EntryModificationEvent(EntryModificationEvent.ADD_ENTRY, entry));
        });
        initDays();
    }

    /**
     * Initializes the day view.
     */
    private void initDays() {
        Map<DayOfWeek, Collection<Entry>> entryMap = journal.getEntryMap();
        for (DayOfWeek day : entryMap.keySet()) {
            ScrollPane scrollPane = new ScrollPane();
            VBox content = new VBox(new Label(day.name()));
            scrollPane.setContent(content);
            for (Entry entry : entryMap.get(day)) {
                content.getChildren().add(new EntryComponent(this, entry));
            }
            this.days.add(scrollPane, day.ordinal(), 0);
            this.content.put(day, content);
        }
    }

    // TODO: might want to separate and make a Utils class that adds modifiers to components
    /**
     * Restricts the text field to only receive integer inputs.
     *
     * @param textField text field to restrict
     */
    private void restrictToInteger(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private class JournalEventHandler implements EventHandler<JournalEvent> {
        JournalComponent component;

        public JournalEventHandler(JournalComponent component) {
            this.component = component;
        }
        @Override
        public void handle(JournalEvent event) {
            switch (event.getEventType().getName()) {
                case "SAVE" -> handleSave();
                case "ADD_ENTRY" -> handleAddEntry(event);
                case "REMOVE_ENTRY" -> handleRemoveEntry(event);
                case "EDIT_ENTRY" -> handleEditEntry(event);
                case "ADD_CATEGORY" -> handleAddCategory();
                case "REMOVE_CATEGORY" -> handleRemoveCategory();
                default -> throw new IllegalArgumentException("Not an event");
            }
        }

        /**
         * Prompts the user to choose a file location to save the bullet journal.
         */
        private void handleSave() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose save location");
            // TODO: replace initial directory. Currently like this for convince
            fileChooser.setInitialDirectory(new File("src/main/resources"));
            File file = fileChooser.showSaveDialog(null);
            file = new File(file.toString() + ".bujo");
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

        /**
         * Adds the entry to the day.
         */
        private void handleAddEntry(Event event) {
            EntryModificationEvent e = (EntryModificationEvent) event;
            Entry entry = e.entry();
            journal.addEntry(entry);
            content.get(entry.day()).getChildren().add(new EntryComponent(this.component, entry));
        }

        /**
         * Checks if
         */
        private boolean checkBounds() {
            int eventCount = journal.getAllEntries(new FilterEvent()).size();
            int taskCount = journal.getAllEntries(new FilterTask()).size();
            //if (eventCount > getMaxEvent) {
            // return false;
            //}

            //if (taskCount > getMaxTask) {
            // return false;
            //}

            //if (eventCount < 0) {
            // return false;
            //}

            //if (taskCount < 0) {
            // return false;
            //}

            // return true;
            return false;
        }

        /**
         * Removes the entry from the day.
         */
        private void handleRemoveEntry(Event event) {
            EntryModificationEvent e = (EntryModificationEvent) event;
            Entry entry = e.entry();
            journal.removeEntry(entry);
            content.get(entry.day()).getChildren().remove(component);
        }

        /**
         * Lets the user edit the entry if they would like to make changes to it
         */
        private void handleEditEntry(Event event) {
//            EntryComponent component = (EntryComponent) event.getSource();
//            Entry toRemove = component.entry(); TODO: some differentiate thing
//            Entry toAdd = component.entry();
//            journal.removeEntry(toRemove)
//            journal.addEntry(toAdd);
            // TODO: update GUI
        }

        /**
         * Adds category to the day
         */
        private void handleAddCategory() {
            //checkBounds
            // TODO: Complete
        }

        /**
         * Removes the category from the day
         */
        private void handleRemoveCategory() {
            //checkbounds
            // TODO: Complete
        }
    }
}