package cs3500.pa05.controller;

import cs3500.pa05.model.BulletJournal;
import javafx.event.Event;
import javafx.event.EventHandler;

public class JournalEventHandler implements EventHandler<JournalEvent> {
  @Override
  public void handle(JournalEvent event) {
    switch (event.getEventType().getName()) {
      case "SAVE" -> handleSave(event);
      case "ADD_ENTRY" -> handleAddEntry(event);
      case "REMOVE_ENTRY" -> handleRemoveEntry(event);
      case "EDIT_ENTRY" -> handleEditEntry(event);
      case "ADD_CATEGORY" -> handleAddCategory(event);
      case "REMOVE_CATEGORY" -> handleRemoveCategory(event);
      default -> throw new IllegalArgumentException("Not an event");
    }
  }

  private void handleSave(Event event) {
    JournalComponent component = (JournalComponent) event.getTarget();
    // TODO: Complete
  }

  private void handleAddEntry(Event event) {
    // TODO: Complete
  }

  private void handleRemoveEntry(Event event) {
    // TODO: Complete
  }

  private void handleEditEntry(Event event) {
    // TODO: Complete
  }

  private void handleAddCategory(Event event) {
    // TODO: Complete
  }

  private void handleRemoveCategory(Event event) {
    // TODO: Complete
  }
}
