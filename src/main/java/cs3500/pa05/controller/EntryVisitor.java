package cs3500.pa05.controller;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.VisitableEntry;

public interface EntryVisitor {
  default void visit(VisitableEntry entry) {
    entry.accept(this);
  }

  void visit(Event event);

  void visit(Task task);
}
