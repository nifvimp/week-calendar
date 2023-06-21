package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock visitor for testing.
 */
public class mockVisitor implements EntryVisitor {
  List<Event> eventsVisited = new ArrayList<>();
  List<Task> tasksVisited = new ArrayList<>();

  /**
   * Gets the visited events.
   * @return List of events visited
   */
  public List<Event> getEventsVisited() {
    return eventsVisited;
  }

  /**
   * Gets the visited tasks.
   * @return List of tasks visited
   */
  public List<Task> getTasksVisited() {
    return tasksVisited;
  }

  /**
   * visits an event entry.
   *
   * @param event event to visit
   */
  @Override
  public void visit(Event event) {
    eventsVisited.add(event);
  }

  /**
   * visits an task entry.
   *
   * @param task task to visit
   */
  @Override
  public void visit(Task task) {
    tasksVisited.add(task);
  }
}
