package cs3500.pa05.controller;

import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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

  /**
   * Constructs an entry component.
   * @param journalComponent
   * @param entry
   */
  public EntryComponent(JournalComponent journalComponent, Entry entry) { // TODO: setup text wrap
    this.EntrySpecificInfo = new ArrayList<>();
    this.entry = entry;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/entry.fxml"));
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
    container.onMouseClickedProperty().set(
        event -> new EntryViewerComponent(entry, this)
    );
  }

  private void initEntrySpecificInfo(Entry entry) {
    VBox info = new VBox();
    info.setAlignment(Pos.TOP_CENTER);
    HBox box = new HBox();
    box.setAlignment(Pos.CENTER_LEFT);
    box.setPadding(new Insets(10, 0 , 0, 10));
    info.getChildren().add(box);
    Label name = new Label();
    Label val = new Label();
    box.getChildren().add(name);
    box.getChildren().add(val);
    if (entry.isEvent()) {
      name.setText("Interval: ");
      val.setText((((Event) entry).interval().toString()));
    } else if (entry.isTask()) {
      name.setText("Progress: ");
      val.setText(((Task) entry).getStatus().toString());
    } else {
      throw new RuntimeException("Passed entry is neither an event nor task.");
    }
    EntrySpecificInfo.add(info);
    container.getChildren().add(2, info);
  }

  /**
   * Gets the journal parent of the entry.
   *
   * @return journal parent.
   */
  public JournalComponent parent() {
    return this.journalComponent;
  }
}