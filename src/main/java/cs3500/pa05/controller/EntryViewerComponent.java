package cs3500.pa05.controller;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import cs3500.pa05.model.TimeInterval;
import cs3500.pa05.model.Timestamp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * A controller for an entry
 */
public class EntryViewerComponent extends Dialog<Entry> {
  private IntegerProperty eventMax;
  private IntegerProperty taskMax;
  @FXML
  private TextField nameField;
  @FXML
  private ChoiceBox<DayOfWeek> dayChoice;
  @FXML
  private ChoiceBox<String> categoryChoice;
  @FXML
  private ChoiceBox<String> entryTypeChoice;
  @FXML
  private TextArea descriptionField;
  @FXML
  private VBox entrySpecificContainer;
  private List<Node> entrySpecificValues;
  @FXML
  private Button save;
  @FXML
  private Button delete;
  private Entry oldEntry;
  private EntryComponent entryComponent;

  /**
   * Opens an entry viewer component with the information of an existing entry.
   * @param oldEntry the existing entry
   * @param entryComponent parent journal
   */
  public EntryViewerComponent(Entry oldEntry, EntryComponent entryComponent) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/entryEditor.fxml"));
    this.entryComponent = Objects.requireNonNull(entryComponent);
    this.initModality(Modality.NONE);
    this.entrySpecificValues = new ArrayList<>();
    this.oldEntry = oldEntry;
    fxmlLoader.setController(EntryViewerComponent.this);
    try {
      DialogPane loaded = fxmlLoader.load();
      this.setDialogPane(loaded);
      loaded.getScene().getWindow().setOnCloseRequest(e -> this.close());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    initElements(entryComponent);
    this.setResultConverter(button -> updatedEntry());
    this.show();
  }

  /**
   * Binds the Integer Properties
   *
   * @param tasks max tasks
   * @param events max events
   */
  public void bindEntryMaximum(IntegerProperty tasks, IntegerProperty events) {
    this.taskMax.bind(tasks);
    this.eventMax.bind(events);
  }

  /**
   * Opens a empty entry viewer component.
   *
   * @param entryComponent parent journal
   */
  public EntryViewerComponent(EntryComponent entryComponent) {
    this(null, entryComponent);
  }

  /**
   * Sets up the view components.
   *
   * @param journalComponent parent journal
   */
  private void initElements(EntryComponent journalComponent) {
    initEntryCommon();
    initEntrySpecific();
    initButtons();
  }

  /**
   * Sets up the basic entry information.
   */
  private void initEntryCommon() {
    String name = (oldEntry == null) ? "" : oldEntry.name();
    DayOfWeek day = (oldEntry == null) ? DayOfWeek.SUNDAY : oldEntry.day();
    String category = (oldEntry == null) ? "" : oldEntry.category();
    String description = (oldEntry == null) ? "" : oldEntry.description();
    String entryType = (oldEntry == null) ? "Task" : (oldEntry.isTask()) ? "Task" : "Event";
    dayChoice.getItems().addAll(DayOfWeek.values());
    nameField.setText(name);
    dayChoice.getSelectionModel().select(day);
    categoryChoice.getSelectionModel().select(category);
    entryTypeChoice.getSelectionModel().select(entryType);
    descriptionField.setText(description);
  }

  /**
   * Sets up the entry specific information.
   */
  private void initEntrySpecific() {
    entryTypeChoice.setItems(FXCollections.observableArrayList("Task", "Event"));
    entryTypeChoice.setOnAction(e -> {
      entrySpecificContainer.getChildren().clear();
      entrySpecificValues.clear();
      String selection = entryTypeChoice.getValue();
      // TODO: add based on current value of entryTypeChoice.
      switch(selection) {
        case "Task" -> {
          entrySpecificContainer.getChildren().add(new Label("Task completed."));
          CheckBox checkBox = new CheckBox();
          entrySpecificValues.add(0, checkBox);
          entrySpecificContainer.getChildren().add(checkBox);
        }
        case "Event" -> {
          entrySpecificContainer.getChildren().add(new Label("Start"));
          ComboBox<Integer> startHour = new ComboBox<>();
          startHour.setPromptText("Hour");
          for (int hour = 0; hour < 12; hour++) {
            startHour.getItems().add(hour);
          }
          entrySpecificValues.add(0, startHour);
          entrySpecificContainer.getChildren().add(startHour);

          ComboBox<Integer> startMinute = new ComboBox<>();
          startMinute.setPromptText("Minutes");
          for (int minute = 0; minute < 6; minute++) {
            startMinute.getItems().add(minute * 10);
          }
          entrySpecificValues.add(1, startMinute);
          entrySpecificContainer.getChildren().add(startMinute);

          ComboBox<String> halfOfDay = new ComboBox<>();
          halfOfDay.setPromptText("AM/PM");
          halfOfDay.getItems().add("AM");
          halfOfDay.getItems().add("PM");
          entrySpecificValues.add(2, halfOfDay);
          entrySpecificContainer.getChildren().add(halfOfDay);


          entrySpecificContainer.getChildren().add(new Label("Duration"));
          ComboBox<Integer> durationHour = new ComboBox<>();
          durationHour.setPromptText("Hour");
          for (int hour = 0; hour < 25; hour++) {
            durationHour.getItems().add(hour);
          }
          entrySpecificValues.add(3, durationHour);
          entrySpecificContainer.getChildren().add(durationHour);

          ComboBox<Integer> durationMinute = new ComboBox<>();
          durationMinute.setPromptText("Minutes");
          for (int minute = 0; minute < 6; minute++) {
            durationMinute.getItems().add(minute * 10);
          }
          entrySpecificValues.add(4, durationMinute);
          entrySpecificContainer.getChildren().add(durationMinute);
        }
      }
    });
    switch(entryTypeChoice.getValue()) {
      case "Task" -> {} // TODO: init the view stuff
      case "Event" -> {}
    }
//    if (oldEntry.isEvent()) { // This feels scuffed. Will probably redo when swapped out for  dropdown menu
//      if (oldEntry != null) {
//        entrySpecificInfo = new TextField(((Event) oldEntry).interval().toString());
//      } else {
//        Timestamp startTime = new Timestamp(DayOfWeek.TUESDAY, 0);
//        TimeInterval interval = new TimeInterval(startTime,15);
//        entrySpecificInfo = new TextField(interval.toString());
//      }
//    } else if (oldEntry.isTask()) {
//      entrySpecificInfo = new CheckBox();
//      boolean status = ((Task) oldEntry).getStatus().equals(TaskStatus.COMPLETE);
//      ((CheckBox) entrySpecificInfo).setSelected(status);
//    } else {
//      throw new RuntimeException("Entry was not an event nor a task.");
//    }
  }

  /**
   * Sets up the buttons event listeners.
   */
  private void initButtons() {
    save.setOnMouseClicked((event -> {
      entryComponent.fireEvent(
          new EntryModificationEvent(EntryModificationEvent.ADD_ENTRY, updatedEntry()));
      entryComponent.fireEvent(
          new EntryModificationEvent(EntryModificationEvent.REMOVE_ENTRY, oldEntry));
      this.setTitle("create"); // TODO: Extraordinarily sus
      this.close();
    }));
    delete.setOnMouseClicked((event -> {
      entryComponent.fireEvent(
          new EntryModificationEvent(EntryModificationEvent.REMOVE_ENTRY, oldEntry));
      this.close(); // TODO: make event firing conditional if invalid.
    }));
  }

  private Entry updatedEntry() {
    ObservableList<Node> children = entrySpecificContainer.getChildren();
    String name = nameField.getText();
    String category = categoryChoice.getValue();
    String description = descriptionField.getText();
    DayOfWeek day = dayChoice.getValue();
    Entry entry = null;
    switch(entryTypeChoice.getValue()) {
      case "Task" -> {
//        TaskStatus status = children.get(1) TODO: do something with this
        Task task = new Task(name, day, description, category);
        task.setStatus(TaskStatus.COMPLETE);
        entry = task;
      }
      case "Event" -> {
//        int time = Integer.parseInt(((TextField) children.get(1)).getText()); // TODO: make the text field programmatically
//        int duration = Integer.parseInt(((TextField) children.get(2)).getText());
        TimeInterval interval = new TimeInterval(new Timestamp(day, 100), 100);
        entry = new Event(name, day, interval, description, category);
      }
    }
    return entry;
  }
}