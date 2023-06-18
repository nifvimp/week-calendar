package cs3500.pa05.controller;


import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import javafx.scene.Node;

public class EntryComponentBuilder implements EntryVisitor {
  // TODO: decide what will make up a entry
  /* parameters for creating a new group of UI elements for an entry */

  public EntryComponentBuilder() {
    configure();
  }

  /**
   * Sets the default parameters used to make entry components for the view.
   */
  private void configure() {
    // TODO: implement
  }

  /**
   * Creates a UI element representing the given event.
   *
   * @param event event entry
   * @return UI element
   */
  public Node create(Event event) {
    // TODO: implement
    return null;
  }

  /**
   * Creates a UI element represents the given task.
   *
   * @param task task entry
   * @return UI element
   */
  public Node create(Task task) {
    // TODO: implement
    return null;
  }

  @Override
  public void visit(Event event) {

  }

  @Override
  public void visit(Task task) {

  }
}