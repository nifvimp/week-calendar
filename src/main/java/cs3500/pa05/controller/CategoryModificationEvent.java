package cs3500.pa05.controller;

import javafx.event.Event;
import javafx.event.EventType;

public class CategoryModificationEvent extends JournalEvent {

  private final String category;

  /**
   * Creates a new category modification event.
   *
   * @param eventType event type
   * @param category category to modify
   */
  public CategoryModificationEvent(EventType<? extends Event> eventType, String category) {
    super(eventType);
    this.category = category;
  }

  /**
   * Gets the category being modified.
   *
   * @return category being modified
   */
  public String category() {
    return this.category;
  }
}
