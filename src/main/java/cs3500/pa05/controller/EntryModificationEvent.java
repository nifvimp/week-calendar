package cs3500.pa05.controller;

import cs3500.pa05.model.Entry;
import javafx.event.Event;
import javafx.event.EventType;

public class EntryModificationEvent extends JournalEvent {
  public static final EventType<JournalEvent> ADD_ENTRY =
      new EventType<>(ANY, "ADD_ENTRY");
  public static final EventType<JournalEvent> REMOVE_ENTRY =
      new EventType<>(ANY, "REMOVE_ENTRY");
  public static final EventType<JournalEvent> EDIT_ENTRY =
      new EventType<>(ANY, "EDIT_ENTRY");
  private final Entry entry;

  /**
   * Creates a new entry modification event.
   *
   * @param eventType event type
   * @param entry entry to modify
   */
  public EntryModificationEvent(EventType<? extends Event> eventType, Entry entry) {
    super(eventType);
    this.entry = entry;
  }

  public Entry entry() {
    return this.entry;
  }
}
