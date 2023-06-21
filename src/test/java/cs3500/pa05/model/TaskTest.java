package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests task functionality.
 */
class TaskTest {
  Task task;
  Task task2;

  /**
   * Sets up test tasks.
   */
  @BeforeEach
  void setup() {
    task = new Task("test", DayOfWeek.WEDNESDAY, null, null);
    task2 = new Task("test2", DayOfWeek.FRIDAY, "bruh", "potato");
  }

  /**
   * Tests task getters.
   */
  @Test
  void gettersTest() {
    assertEquals(TaskStatus.INCOMPLETE, task.getStatus());
    task.setStatus(TaskStatus.COMPLETE);
    assertEquals(TaskStatus.COMPLETE, task.getStatus());
    assertEquals(DayOfWeek.WEDNESDAY, task.day());
    assertEquals("test", task.name());
    assertNull(task.description());
    assertNull(task.category());
  }

  /**
   * Tests isTask.
   */
  @Test
  void isTaskTest() {
    assertTrue(task.isTask());
    assertFalse(task.isEvent());
  }

  /**
   * Tests visitable functionality.
   */
  @Test
  void acceptTest() {
    MockVisitor visitor = new MockVisitor();
    Entry task = new Task("", DayOfWeek.SUNDAY, null, null);
    visitor.visit(task);
    assertEquals("[Entry:\nName: \nDay: SUNDAY\n\nINCOMPLETE]",
        visitor.getTasksVisited().toString());
  }

  /**
   * Tests toString method.
   */
  @Test
  void toStringTest() {
    assertEquals("Entry:\nName: test\nDay: WEDNESDAY\n\nINCOMPLETE", task.toString());
    assertEquals("Entry:\nName: test2\nDay: FRIDAY\nDescription: bruh\nCategory: potato\n"
        + "\nINCOMPLETE", task2.toString());
  }
}