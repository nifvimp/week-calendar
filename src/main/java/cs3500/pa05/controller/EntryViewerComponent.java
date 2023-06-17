package cs3500.pa05.controller;

import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * A controller for an entry
 */
public class EntryViewerComponent {
  @FXML
  private TextField nameField;
  @FXML
  private TextField categoryField;
  @FXML
  private TextField descriptionField;
  @FXML
  private Node entrySpecificInfo;
  @FXML
  private Label entrySpecificInfoType;
  @FXML
  private Button save;
  @FXML
  private Button delete;

  private Entry entry;

  private Rectangle background;



  public EntryViewerComponent(Entry entry) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wahtDayDataEditor.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(EntryViewerComponent.this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    initElements(entry);
  }

  private void initElements(Entry entry){
    this.entry = entry;
    nameField = new TextField(entry.name());
    categoryField = new TextField(entry.category());
    descriptionField = new TextField(entry.description());
    if (entry.isEvent()) {
      entrySpecificInfo = new TextField(((Event) entry).interval().toString());
    } else if (entry.isTask()) {
      entrySpecificInfo = new CheckBox();
      boolean status = ((Task) entry).getStatus().equals(TaskStatus.COMPLETE);
      ((CheckBox) entrySpecificInfo).setSelected(status);
    } else {
      //TODO: What are you even doing??? Not an event or a task???
    }
    JournalEventHandler journalEventHandler = new JournalEventHandler();
    save.setOnMouseClicked((event -> {
      journalEventHandler.handle(new JournalEvent(JournalEvent.ADD_ENTRY));
      journalEventHandler.handle(new JournalEvent(JournalEvent.REMOVE_ENTRY)); // TODO: somehow specify
    }));

    delete.setOnMouseClicked((event -> journalEventHandler.handle(new JournalEvent(JournalEvent.REMOVE_ENTRY))));

  }
}
