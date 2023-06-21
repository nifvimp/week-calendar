package cs3500.pa05.controller;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event that signals to a bullet journal to do something.
 */
public class JournalEvent extends Event {
  /**
   * Signals ANY Journal event
   */
  public static final EventType<JournalEvent> ANY =
      new EventType<>("ANY");
  /**
   * Signals Journal to save
   */
  public static final EventType<JournalEvent> SAVE =
      new EventType<>(ANY, "SAVE");
  /**
   * Signals Journal to load different Journal
   */
  public static final EventType<JournalEvent> LOAD =
      new EventType<>(ANY, "LOAD");
  /**
   * Signals Journal to add category
   */
  public static final EventType<JournalEvent> ADD_CATEGORY =
      new EventType<>(ANY, "ADD_CATEGORY");
  /**
   * Signals Journal to remove category
   */
  public static final EventType<JournalEvent> REMOVE_CATEGORY =
      new EventType<>(ANY, "REMOVE_CATEGORY");

  /**
   * Creates a new journal event.
   *
   * @param eventType event type
   */
  public JournalEvent(EventType<? extends Event> eventType) {
    super(eventType);
  }
}