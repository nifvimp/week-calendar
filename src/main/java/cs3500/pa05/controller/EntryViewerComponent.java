package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.FilterEvent;
import cs3500.pa05.model.FilterTask;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import cs3500.pa05.model.TimeInterval;
import cs3500.pa05.model.Timestamp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;

/**
 * A controller for an entry
 */
public class EntryViewerComponent extends Dialog<Entry> {
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
    this.setTitle("Entry view");
    fxmlLoader.setController(EntryViewerComponent.this);
    try {
      DialogPane loaded = fxmlLoader.load();
      this.setDialogPane(loaded);
      loaded.getScene().getWindow().setOnCloseRequest(e -> this.close());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    initElements(entryComponent);
    this.setResultConverter(buttonType -> {
        if (buttonType == ButtonType.APPLY) { // TODO: should not use own buttons but inbuilt dialog pane buttons
          return updatedEntry();
        }
        return null;
    });
    this.show();
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
    initButtons();
    initEntryDelegation();
    initEntryCommon();
    initEntrySpecific();
    initAutoTags();
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
    categoryChoice.getItems().addAll(entryComponent.parent().journal().getCategories());
    dayChoice.getItems().addAll(DayOfWeek.values());
    nameField.setText(name);
    dayChoice.getSelectionModel().select(day);
    categoryChoice.getSelectionModel().select(category);
    entryTypeChoice.getSelectionModel().select(entryType);
    descriptionField.setText(description);
  }

  /**
   * Initializes the handlers that handles making the fields for different type of entries.
   */
  private void initEntryDelegation() {
    entryTypeChoice.setItems(FXCollections.observableArrayList("Task", "Event"));
    entryTypeChoice.setOnAction(e -> {
      entrySpecificContainer.getChildren().clear();
      entrySpecificValues.clear();
      String selection = entryTypeChoice.getValue();
      switch(selection) {
        case "Task" -> {
          entrySpecificContainer.getChildren().add(new Label("Task Completed"));
          CheckBox checkBox = new CheckBox();
          entrySpecificValues.add(0, checkBox);
          entrySpecificContainer.getChildren().add(checkBox);
        }
        case "Event" -> {
          HBox start = new HBox();
          HBox duration = new HBox();
          start.setPadding(new Insets(10));
          duration.setPadding(new Insets(10));
          start.setAlignment(Pos.TOP_CENTER);
          duration.setAlignment(Pos.TOP_CENTER);
          entrySpecificContainer.getChildren().add(start);
          entrySpecificContainer.getChildren().add(duration);
          start.getChildren().add(new Label("Start: "));
          ComboBox<Integer> startHour = new ComboBox<>();
          startHour.setPromptText("Hour");
          for (int hour = 0; hour < 12; hour++) {
            startHour.getItems().add(hour);
          }
          entrySpecificValues.add(0, startHour);
          start.getChildren().add(startHour);

          ComboBox<Integer> startMinute = new ComboBox<>();
          startMinute.setPromptText("Minutes");
          for (int minute = 0; minute < 6; minute++) {
            startMinute.getItems().add(minute * 10);
          }
          entrySpecificValues.add(1, startMinute);
          start.getChildren().add(startMinute);

          ComboBox<String> halfOfDay = new ComboBox<>();
          halfOfDay.setPromptText("AM/PM");
          halfOfDay.getItems().add("AM");
          halfOfDay.getItems().add("PM");
          entrySpecificValues.add(2, halfOfDay);
          start.getChildren().add(halfOfDay);

          duration.getChildren().add(new Label("Duration: "));
          ComboBox<Integer> durationHour = new ComboBox<>();
          durationHour.setPromptText("Hour");
          for (int hour = 0; hour < 25; hour++) {
            durationHour.getItems().add(hour);
          }
          entrySpecificValues.add(3, durationHour);
          duration.getChildren().add(durationHour);

          ComboBox<Integer> durationMinute = new ComboBox<>();
          durationMinute.setPromptText("Minutes");
          for (int minute = 0; minute < 6; minute++) {
            durationMinute.getItems().add(minute * 10);
          }
          entrySpecificValues.add(4, durationMinute);
          duration.getChildren().add(durationMinute);
        }
      }
    });
  }

  /**
   * Sets up the entry specific information.
   */
  private void initEntrySpecific() {
     switch(entryTypeChoice.getValue()) {
      case "Task" -> {
        boolean checked = ((Task) oldEntry).getStatus() == TaskStatus.COMPLETE;
        ((CheckBox) entrySpecificValues.get(0)).setSelected(checked);
      }
      case "Event" -> {
        Event event = (Event) oldEntry;
        int time = event.interval().start().time();
        int duration = event.interval().duration();
        int timeOfDay = time / 720;
        ((ComboBox<?>) entrySpecificValues.get(0))
            .getSelectionModel().select((time / 60) % 12);
        ((ComboBox<?>) entrySpecificValues.get(1))
            .getSelectionModel().select((time % 60) / 10);
        ((ComboBox<?>) entrySpecificValues.get(2))
            .getSelectionModel().select(timeOfDay);
        ((ComboBox<?>) entrySpecificValues.get(3))
            .getSelectionModel().select((duration / 60));
        ((ComboBox<?>) entrySpecificValues.get(4))
            .getSelectionModel().select((duration % 60) / 10);
      }
    }
  }

  /**
   * Sets up the buttons event listeners.
   */
  private void initButtons() {
    save.setOnMouseClicked((event -> {
      Collection<Entry> entries = entryComponent.parent().journal()
          .getEntryMap().get(dayChoice.getValue());
      int currTasks = new FilterTask().organize(entries).size();
      int currEvents = new FilterEvent().organize(entries).size();
      int maxTasks = entryComponent.parent().journal().getTaskMax();
      int maxEvents = entryComponent.parent().journal().getEventMax();
      if ((entryTypeChoice.getValue().equals("Task") && maxTasks > currTasks)
          || (entryTypeChoice.getValue().equals("Event") && maxEvents > currEvents)) {
        entryComponent.fireEvent(
            new EntryModificationEvent(EntryModificationEvent.ADD_ENTRY, updatedEntry()));
        entryComponent.fireEvent(
            new EntryModificationEvent(EntryModificationEvent.REMOVE_ENTRY, oldEntry));
        this.setTitle("create"); // TODO: Extraordinarily sus
        this.getDialogPane().lookupButton(ButtonType.APPLY).fireEvent(new ActionEvent());
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Limit Exceeded");
        if (entryTypeChoice.getValue().equals("Task")) {
          alert.setContentText("Cannot add more tasks. Maximum limit reached.");
        } else if (entryTypeChoice.getValue().equals("Event")) {
          alert.setContentText("Cannot add more events. Maximum limit reached.");
        }
        // TODO: make alert not alert if is modifying.
        alert.showAndWait().ifPresent(buttonType -> {
          // TODO: add apply and cancel button types to alert and decide what to do based off that
        });
      }
    }));
    delete.setOnMouseClicked((event -> {
      entryComponent.fireEvent(
          new EntryModificationEvent(EntryModificationEvent.REMOVE_ENTRY, oldEntry));
      this.close(); // TODO: make event firing conditional if invalid.
    }));
  }

  /**
   * Initializes the name field to automatically add tags.
   */
  private void initAutoTags() {
    nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        String text = nameField.getText();
        Pattern pattern = Pattern.compile("\s+#.*\s*");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find())
        {
          String match = matcher.group(0);
          String category = match.trim().substring(1);
          entryComponent.parent().journal().addCategory(category);
          categoryChoice.getSelectionModel().select(category);
          categoryChoice.getItems().add(category); // TODO: sus, should only add category after confirmation?
          nameField.setText(text.replace(match, ""));
        }
      }
    });
  }

  private Entry updatedEntry() {
    String name = nameField.getText();
    String category = categoryChoice.getValue();
    String description = descriptionField.getText();
    DayOfWeek day = dayChoice.getValue();
    Entry entry = null;
    switch (entryTypeChoice.getValue()) {
      case "Task" -> {
        TaskStatus status = (((CheckBox) entrySpecificValues.get(0)).selectedProperty().get())
            ? TaskStatus.COMPLETE : TaskStatus.INCOMPLETE;
        Task task = new Task(name, day, description, category);
        task.setStatus(status);
        entry = task;
      }
      case "Event" -> {
        int startHour = (Integer) (((ComboBox<?>) entrySpecificValues.get(0)).getValue());
        int startMin = (Integer) (((ComboBox<?>) entrySpecificValues.get(1)).getValue());
        String startMeridiem = ((String) ((ComboBox<?>) entrySpecificValues.get(2)).getValue());
        int durationHour = (Integer) (((ComboBox<?>) entrySpecificValues.get(3)).getValue());
        int durationMin = (Integer) (((ComboBox<?>) entrySpecificValues.get(4)).getValue());
        TimeInterval interval = new TimeInterval(
            new Timestamp(day,
                (((startMeridiem.equals("PM")) ? 12 : 0) + startHour) * 60 + startMin),
            durationHour * 60 + durationMin
        );
        entry = new Event(name, day, interval, description, category);
      }
    }
    return entry;
  }
}