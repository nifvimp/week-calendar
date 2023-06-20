package cs3500.pa05.model;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.VisitableEntry;

/**
 * An interface that allows the implementor to visit stable entries.
 */
public interface EntryVisitor {
  /**
   * visits a generic entry.
   *
   * @param entry entry to visit
   */
  default void visit(VisitableEntry entry) {
    entry.accept(this);
  }

  /**
   * visits an event entry.
   *
   * @param event event to visit
   */
  void visit(Event event);

  /**
   * visits an task entry.
   *
   * @param task task to visit
   */
  void visit(Task task);
}
