package cs3500.pa05.controller;

import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * A controller for an entry
 */
public class EntryComponent extends VBox {
  @FXML
  private Label name;
  @FXML
  private Label category;
  @FXML
  private Label Description;
  @FXML
  private Label EntrySpecificInfo;
  private Entry entry;

  private Rectangle background;



  public EntryComponent() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/MySwitch.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(EntryComponent.this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    initElements();
  }

  private void initElements(){
    name = new Label(entry.name());
    category = new Label(entry.category());
    Description = new Label(entry.description());
    if (entry.isEvent()) {
      EntrySpecificInfo = new Label(((Event) entry).interval().toString());
    } else if (entry.isTask()) {
      EntrySpecificInfo = new Label(((Task) entry).getStatus().toString());
    } else {
      //TODO: What are you even doing??? Not an event or a task???
    }
    //background.onMouseClickedProperty().set(event -> TODO: Handle popping up with mini viewer);
  }
}
