package cs3500.pa05.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.FilterByCompletion;
import cs3500.pa05.model.FilterEvent;
import cs3500.pa05.model.FilterTask;
import cs3500.pa05.model.SortByDay;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Represents a controller for a bullet journal.
 */
public class JournalComponent extends BorderPane {
    // purposeful static so that specified configuration shared between all components
    private static final ObjectMapper mapper = new ObjectMapper();
    private BulletJournal journal;
    @FXML
    private TextField name;
    @FXML
    private Button save;
    @FXML
    private Button load;
    @FXML
    private Button createEntry;
    @FXML
    private Button addCategory;
    @FXML
    private Button removeCategory;
    @FXML
    private TextField maxEvents;
    @FXML
    private TextField maxTasks;
    @FXML
    private GridPane week;
    @FXML
    private ListView<String> taskQueue;
    @FXML
    private VBox options; // TODO: figure this out. prob need separate class
    @FXML
    private ListView<String> stats;
    @FXML
    private GridPane days;
    private Map<DayOfWeek, VBox> content;

    /**
     * Creates a new journal component that displays the passed in bullet journal.
     *
     * @param journal journal to display
     * @param parent  parent of the component
     */
    public JournalComponent(BulletJournal journal, Node parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/journal.fxml"));
        fxmlLoader.setController(this);
        this.content = new HashMap<>();
        this.journal = journal;
        Node loaded;
        try {
            loaded = fxmlLoader.load();
            this.getChildren().add(loaded);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.addEventFilter(JournalEvent.ANY, new JournalEventHandler(this));
        initComponents();
        // TODO: fix height and width alignment with box
        ((BorderPane) loaded).prefHeightProperty().bind(parent.getScene().heightProperty());
        ((BorderPane) loaded).prefWidthProperty().bind(parent.getScene().widthProperty());
    }

    /**
     * Initializes the view components of the journal display.
     */
    private void initComponents() {
        initName();
        initEventMax();
        initTaskMax();
        initButtons();
        initTaskQueue();
        initStats();
        initDays();
    }

    /**
     * Initializes the name text field.
     */
    private void initName() {
        this.name.textProperty().set(this.journal.getName());
        this.name.textProperty().addListener((observable, oldValue, newValue) -> {
            journal.setName(newValue);
        });
    }

    /**
     * Initializes the event max text field.
     */
    private void initEventMax() {
        this.maxEvents.textProperty().set(String.valueOf(this.journal.getEventMax()));
        this.maxEvents.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.maxEvents.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (!newValue.equals("")) {
                journal.setEventMax(Integer.parseInt(newValue));
            }
        });
    }

    /**
     * Initializes the task max text field.
     */
    private void initTaskMax() {
        this.maxTasks.textProperty().set(String.valueOf(this.journal.getTaskMax()));
        this.maxTasks.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.maxTasks.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (!newValue.equals("")) {
                journal.setTaskMax(Integer.parseInt(newValue));
            }
        });
    }

    private void initButtons() {
        this.save.setOnAction(e -> this.fireEvent(new JournalEvent(JournalEvent.SAVE)));
        this.load.setOnAction(e -> this.fireEvent(new JournalEvent(JournalEvent.LOAD)));
        this.createEntry.setOnAction(
            e -> this.fireEvent(new JournalEvent(JournalEvent.CREATE_ENTRY)));
        this.addCategory.setOnAction(
            e -> this.fireEvent(new JournalEvent(JournalEvent.ADD_CATEGORY)));
        this.removeCategory.setOnAction(
            e -> this.fireEvent(new JournalEvent(JournalEvent.REMOVE_CATEGORY)));
    }

    private void initTaskQueue() {
        Collection<Entry> tasks = journal.getAllEntries(new FilterTask(), new SortByDay());
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Entry entry : tasks) {
            Task task = (Task) entry;
            items.add(String.format("%s - %s", task.name(), task.getStatus()));
        }
        taskQueue.setItems(items);
    }

    private void initStats() {
        ObservableList<String> items = FXCollections.observableArrayList();
        int taskCount = journal.getAllEntries(new FilterTask()).size();
        int eventCount = journal.getAllEntries(new FilterEvent()).size();
        int taskCompleted = journal.getAllEntries(
            new FilterByCompletion(TaskStatus.COMPLETE)).size();
        double percentComplete = (double) taskCompleted / taskCount;
        items.add(String.format("Total Tasks: %d", taskCount));
        items.add(String.format("Total Events: %d", eventCount));
        items.add(String.format("Tasks Complete: %2.2f%%", percentComplete * 100));
        stats.setItems(items);
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

    /**
     * Prompts the user to choose a file location to save the bullet journal.
     */
    private void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose save location");
        // TODO: replace initial directory. Currently like this for convince
        fileChooser.setInitialDirectory(new File("src/main/resources"));
        File file = fileChooser.showSaveDialog(null);
        file = new File(file.toString() + ".bujo"); // TODO: fix .bujo.bujo
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

    public BulletJournal journal() {
        return this.journal;
    }

    private void update() {
        initTaskQueue();
        initStats();
    }

    private class JournalEventHandler implements EventHandler<JournalEvent> {
        JournalComponent component;

        public JournalEventHandler(JournalComponent component) {
            this.component = component;
        }

        // TODO: move delegated methods into the main class.
        @Override
        public void handle(JournalEvent event) {
            switch (event.getEventType().getName()) {
                case "SAVE" -> save();
                case "CREATE_ENTRY" -> handleCreateEntry();
                case "ADD_ENTRY" -> handleAddEntry(event);
                case "REMOVE_ENTRY" -> handleRemoveEntry(event);
                case "EDIT_ENTRY" -> handleEditEntry(event);
                case "ADD_CATEGORY" -> handleAddCategory();
                case "REMOVE_CATEGORY" -> handleRemoveCategory();
                default -> throw new IllegalArgumentException("Not an event");
            }
            update();
        }

        /**
         * Adds the entry to the day.
         */
        private void handleAddEntry(Event event) {
            EntryModificationEvent e = (EntryModificationEvent) event;
            Entry entry = e.entry();
            journal.addEntry(entry);
            content.get(entry.day()).getChildren()
                .add(new EntryComponent(this.component, entry));
        }

        /**
         * Opens a EntryViewerComponent to create an entry.
         */
        private void handleCreateEntry() {
            Entry temp = new Task("", DayOfWeek.SUNDAY, null, null);
            EntryComponent entryComponent = new EntryComponent(component, temp);
            EntryViewerComponent viewer = new EntryViewerComponent(temp, entryComponent);
            viewer.setOnHidden(e -> {
                Entry entry = viewer.getResult();
                if (entry != null && "create".equals(viewer.getTitle())) {
                    journal.addEntry(entry);
                    content.get(entry.day()).getChildren()
                        .add(new EntryComponent(this.component, entry));
                    update();
                }
            });
        }

        /**
         * Removes the entry from the day.
         */
        private void handleRemoveEntry(Event event) {
            EntryModificationEvent e = (EntryModificationEvent) event;
            EntryComponent target = (EntryComponent) e.getTarget();
            Entry entry = e.entry();
            journal.removeEntry(entry);
            content.get(entry.day()).getChildren().remove(target);
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
         * Adds category to the journal.
         */
        private void handleAddCategory() {
            StringProperty category = new SimpleStringProperty();
            new TextInputDialog().showAndWait().ifPresent(category::set);
            if (!category.get().equals("")) {
                journal.addCategory(category.get());
            }
        }

        /**
         * Removes the category from the journal.
         */
        private void handleRemoveCategory() {
            StringProperty category = new SimpleStringProperty(); // TODO: change to combo box or something.
            new TextInputDialog().showAndWait().ifPresent(category::set);
            if (!category.get().equals("")) {
                journal.removeCategory(category.get());
            }
        }
    }
}