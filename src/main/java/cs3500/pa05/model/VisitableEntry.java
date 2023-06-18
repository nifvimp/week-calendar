package cs3500.pa05.model;

import cs3500.pa05.controller.EntryVisitor;

/**
 * Denotes that an entry is visitable by an entry visitor.
 */
public interface VisitableEntry {
  /**
   * Accepts the visitor to act on the visitable entry.
   *
   * @param visitor visitor to use
   */
  default void accept(EntryVisitor visitor) {
    visitor.visit(this);
  }
}