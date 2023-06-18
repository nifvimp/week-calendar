package cs3500.pa05.controller;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event that signals to a bullet journal to do something.
 */
public class JournalEvent extends Event {
  public static final EventType<JournalEvent> ANY =
      new EventType<>("ANY");
  public static final EventType<JournalEvent> SAVE =
      new EventType<>(ANY, "SAVE");
  public static final EventType<JournalEvent> LOAD =
      new EventType<>(ANY,"LOAD");
  public static final EventType<JournalEvent> ADD_CATEGORY =
      new EventType<>(ANY, "ADD_CATEGORY");
  public static final EventType<JournalEvent> REMOVE_CATEGORY =
      new EventType<>(ANY, "REMOVE_CATEGORY");
  public static final EventType<JournalEvent> HELP =
      new EventType<>("HELP");

  /**
   * Creates a new journal event.
   *
   * @param eventType event type
   */
  public JournalEvent(EventType<? extends Event> eventType) {
    super(eventType);
  }
}