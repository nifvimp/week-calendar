package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {
  Task task;
  Task task2;

  @BeforeEach
  void setup() {
    task = new Task("test", DayOfWeek.WEDNESDAY, null, null);
    task2 = new Task("test2", DayOfWeek.FRIDAY, "bruh", "potato");
  }

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

  @Test
  void isTaskTest() {
    assertTrue(task.isTask());
    assertFalse(task.isEvent());
  }

  @Test
  void acceptTest() {
    mockVisitor visitor = new mockVisitor();
    Entry task = new Task("", DayOfWeek.SUNDAY, null, null);
    visitor.visit(task);
    assertEquals("[Entry:\nName: \nDay: SUNDAY\n\nINCOMPLETE]",
        visitor.getTasksVisited().toString());
  }

  @Test
  void toStringTest() {
    assertEquals("Entry:\nName: test\nDay: WEDNESDAY\n\nINCOMPLETE", task.toString());
    assertEquals("Entry:\nName: test2\nDay: FRIDAY\nDescription: bruh\nCategory: potato\n"
        + "\nINCOMPLETE", task2.toString());
  }
}