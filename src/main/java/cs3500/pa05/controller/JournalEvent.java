package cs3500.pa05.controller;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event that signals to a bullet journal to do something.
 */
public class JournalEvent extends Event {
  public static final EventType<JournalEvent> SAVE = new EventType<>("SAVE");
  public static final EventType<JournalEvent> LOAD = new EventType<>("LOAD");
  public static final EventType<JournalEvent> ADD_ENTRY = new EventType<>("ADD_ENTRY");
  public static final EventType<JournalEvent> REMOVE_ENTRY = new EventType<>("REMOVE_ENTRY");
  public static final EventType<JournalEvent> EDIT_ENTRY = new EventType<>("EDIT_ENTRY");
  public static final EventType<JournalEvent> ADD_CATEGORY = new EventType<>("ADD_CATEGORY");
  public static final EventType<JournalEvent> REMOVE_CATEGORY = new EventType<>("REMOVE_CATEGORY");
  public static final EventType<JournalEvent> HELP = new EventType<>("HELP");

  /**
   * Creates a new journal event.
   *
   * @param eventType event type
   */
  public JournalEvent(EventType<? extends Event> eventType) {
    super(eventType);
  }
}