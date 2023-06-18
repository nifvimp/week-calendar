package cs3500.pa05.controller;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import cs3500.pa05.model.TimeInterval;
import cs3500.pa05.model.Timestamp;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**
 * A controller for an entry
 */
public class EntryViewerComponent extends Dialog<Void> {
  @FXML
  private TextField nameField;
  @FXML
  private ChoiceBox<DayOfWeek> dayChoice;
  @FXML
  private TextField categoryField;
  @FXML
  private TextField descriptionField;
  @FXML
  private Node entrySpecificInfo;
  @FXML
  private Label entrySpecificInfoType; // TODO: sus
  @FXML
  private Button save;
  @FXML
  private Button delete;
  private Entry oldEntry;
  private JournalComponent journalComponent;
  private Rectangle background;


  /**
   * Opens an entry viewer component with the information of an existing entry.
   * @param oldEntry the existing entry
   * @param entryComponent parent journal
   */
  public EntryViewerComponent(Entry oldEntry, EntryComponent entryComponent) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/entryEditor.fxml"));
    this.entryComponent = Objects.requireNonNull(entryComponent);
    this.initModality(Modality.NONE);
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
    this.show();
  }

  public EntryViewerComponent(JournalComponent journalComponent) {
    this(null, journalComponent);
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
    String category = (oldEntry == null) ? "" : oldEntry.category();
    String description = (oldEntry == null) ? "" : oldEntry.description();
    dayChoice.getItems().addAll(DayOfWeek.values());
    nameField.setText(name);
    categoryField.setText(category); // TODO: change to dropdown menu (comboBox)
    descriptionField.setText(description);
  }

  /**
   * Sets up the entry specific information.
   */
  private void initEntrySpecific() {
    if (oldEntry.isEvent()) { // This feels scuffed. Will probably redo when swapped out for  dropdown menu
      if (oldEntry != null) {
        entrySpecificInfo = new TextField(((Event) oldEntry).interval().toString());
      } else {
        Timestamp startTime = new Timestamp(DayOfWeek.TUESDAY, 0);
        TimeInterval interval = new TimeInterval(startTime,15);
        entrySpecificInfo = new TextField(interval.toString());
      }
    } else if (oldEntry.isTask()) {
      entrySpecificInfo = new CheckBox();
      boolean status = ((Task) oldEntry).getStatus().equals(TaskStatus.COMPLETE);
      ((CheckBox) entrySpecificInfo).setSelected(status);
    } else {
      throw new RuntimeException("Entry was not an event nor a task.");
    }
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
      this.close();
    }));
    delete.setOnMouseClicked((event -> {
      entryComponent.fireEvent(
          new EntryModificationEvent(EntryModificationEvent.REMOVE_ENTRY, oldEntry));
      this.close(); // TODO: make event firing conditional if invalid.
    }));
  }

  public Entry updatedEntry() {
    // TODO: change this to compile an new entry based on the FXML fields
    return new Task("No", DayOfWeek.MONDAY, null, null);
  }
}