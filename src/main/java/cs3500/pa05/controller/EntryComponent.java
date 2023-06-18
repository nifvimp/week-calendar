package cs3500.pa05.controller;

import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
  private Label description;
  @FXML
  private VBox container;
  private Entry entry;
  private JournalComponent journalComponent;
  private Collection<VBox> EntrySpecificInfo;

  public EntryComponent(JournalComponent journalComponent, Entry entry) {
    this.EntrySpecificInfo = new ArrayList<>();
    this.entry = entry;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/wahtDayData.fxml"));
    fxmlLoader.setController(EntryComponent.this);
    try {
      this.getChildren().add(fxmlLoader.load());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    initElements(journalComponent);
  }

  private void initElements(JournalComponent component) {
    this.journalComponent = component;
    name.setText(entry.name());
    category.setText(entry.category());
    description.setText(entry.description());
    initEntrySpecificInfo(entry);
    container.onMouseClickedProperty().set(event -> new EntryViewerComponent(entry, component));
  }

  private void initEntrySpecificInfo(Entry entry) {
    VBox info = new VBox();
    info.setAlignment(Pos.TOP_CENTER);
    if (entry.isEvent()) {
      info.getChildren().add(new Label("Interval"));
      info.getChildren().add(new Label(((Event) entry).interval().toString()));
    } else if (entry.isTask()) {
      info.getChildren().add(new Label("Progress"));
      info.getChildren().add(new Label(((Task) entry).getStatus().toString()));
    } else {
      throw new RuntimeException("Passed entry is neither an event nor task.");
    }
    EntrySpecificInfo.add(info);
    container.getChildren().add(2, info);
  }
}