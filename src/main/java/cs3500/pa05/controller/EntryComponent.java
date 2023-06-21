package cs3500.pa05.controller;

import cs3500.pa05.model.Entry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
  private JournalComponent parent;
  private Collection<VBox> EntrySpecificInfo;

  /**
   * Constructs an visual entry component that displays the given entry.
   * @param parent journal parent
   * @param entry entry to display
   */
  public EntryComponent(JournalComponent parent, Entry entry) {
    this.EntrySpecificInfo = new ArrayList<>();
    this.parent = parent;
    this.entry = entry;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/entry.fxml"));
    fxmlLoader.setController(EntryComponent.this);
    try {
      this.getChildren().add(fxmlLoader.load());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    initElements();
    initAction();
  }

  private void initElements() {
    name.setText(entry.name());
    category.setText(entry.category());
    description.setText(entry.description());
  }

  private void initAction() {
    container.onMouseClickedProperty().set(
        event -> new EntryViewerComponent(entry, this, true)
    );
  }

  /**
   * Updates the display information.
   */
  public void update() {
    initElements();
  }

  /**
   * Adds a parameter to the entries displayed info.
   *
   * @param name name of the parameter
   * @param value string representation of the parameter
   */
  public void addParameter(String name, String value) {
    HBox box = new HBox();
    box.setAlignment(Pos.CENTER_LEFT);
    box.setPadding(new Insets(10, 0 , 0, 10));
    Label paramName = new Label(name + ": ");
    Label paramValue = new Label(value);
    paramValue.setPadding(new Insets(0, 0, 0, 5));
    box.getChildren().addAll(paramName, paramValue);
    container.getChildren().add(box);
  }

  /**
   * Gets the entry this entry component displays.
   *
   * @return entry the component displays
   */
  public Entry entry() {
    return this.entry;
  }

  /**
   * Gets the journal parent of the entry.
   *
   * @return journal parent.
   */
  public JournalComponent parent() {
    return this.parent;
  }
}