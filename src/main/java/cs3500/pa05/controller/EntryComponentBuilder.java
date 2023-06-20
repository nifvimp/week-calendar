package cs3500.pa05.controller;


import cs3500.pa05.model.Entry;
import cs3500.pa05.model.EntryVisitor;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.util.Objects;

/**
 * Builds entry components that match the given entries.
 */
public class EntryComponentBuilder implements EntryVisitor {
  private final JournalComponent parent;
  private EntryComponent component;

  /**
   * Creates a entry component builder for the passed in journal component.
   *
   * @param parent journal parent
   */
  public EntryComponentBuilder(JournalComponent parent) {
    this.parent = Objects.requireNonNull(parent);
  }

  /**
   * builds a UI element representing the given entry.
   *
   * @param entry entry
   * @return UI element
   */
  public EntryComponent build(Entry entry) {
    component = new EntryComponent(parent, entry);
    this.visit(entry);
    return component;
  }

  @Override
  public void visit(Event event) {
    component.addParameter("Interval", event.interval().toString());
  }

  @Override
  public void visit(Task task) {
    component.addParameter("Status", task.getStatus().toString());
  }
}