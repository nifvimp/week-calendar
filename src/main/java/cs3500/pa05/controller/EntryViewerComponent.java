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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A controller for an entry
 */
public class EntryViewerComponent extends Dialog<Entry> {
  @FXML
  private TextField nameField;
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

  private BorderPane root;

  private Entry oldEntry;
  private JournalComponent journalComponent;
  private Rectangle background;



  public EntryViewerComponent(Entry oldEntry, JournalComponent journalComponent) {
    Objects.requireNonNull(journalComponent);
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wahtDayDataEditor.fxml"));
    fxmlLoader.setController(EntryViewerComponent.this);
    try {
      this.setDialogPane(fxmlLoader.load());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    this.oldEntry = oldEntry;
    initElements(journalComponent);
    this.initModality(Modality.NONE);
    this.show();
  }

  public EntryViewerComponent(JournalComponent journalComponent) {
    this(null, journalComponent);
  }

  private void initElements(JournalComponent journalComponent){
    this.journalComponent = journalComponent;
    String name = (oldEntry == null) ? "" : oldEntry.name();
    String category = (oldEntry == null) ? "" : oldEntry.category();
    String description = (oldEntry == null) ? "" : oldEntry.description();
    nameField.setText(name);
    categoryField.setText(category); // TODO: change to dropdown menu (comboBox)
    descriptionField.setText(description);

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

    save.setOnMouseClicked((event -> {
      journalComponent.fireEvent(
          new EntryModificationEvent(EntryModificationEvent.ADD_ENTRY, updatedEntry()));
      journalComponent.fireEvent(
          new EntryModificationEvent(EntryModificationEvent.REMOVE_ENTRY, oldEntry));
    }));
    delete.setOnMouseClicked((event -> journalComponent.fireEvent(
        new EntryModificationEvent(EntryModificationEvent.REMOVE_ENTRY, oldEntry))));
  }

  public Entry updatedEntry() {
    // TODO: change this to compile an new entry based on the FXML fields
    return new Task("No", DayOfWeek.MONDAY, null, null);
  }
}