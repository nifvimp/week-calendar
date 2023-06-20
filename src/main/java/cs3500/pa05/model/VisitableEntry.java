package cs3500.pa05.model;

/**
 * Denotes that an entry is visitable by an entry visitor.
 */
public interface VisitableEntry {
  /**
   * Accepts the visitor to act on the visitable entry.
   *
   * @param visitor visitor to use
   */
  void accept(EntryVisitor visitor);
}