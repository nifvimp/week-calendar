package cs3500.pa05.controller;

import cs3500.pa05.model.Entry;
import javafx.event.Event;
import javafx.event.EventType;

public class EntryModificationEvent extends JournalEvent {
  public static final EventType<EntryModificationEvent> ADD_ENTRY =
      new EventType<>(ANY, "ADD_ENTRY");
  public static final EventType<EntryModificationEvent> REMOVE_ENTRY =
      new EventType<>(ANY, "REMOVE_ENTRY");
  public static final EventType<EntryModificationEvent> CREATE_ENTRY =
      new EventType<>(ANY, "CREATE_ENTRY");
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

  /**
   * Gets the entry being modified.
   *
   * @return entry being modified
   */
  public Entry entry() {
    return this.entry;
  }
}
