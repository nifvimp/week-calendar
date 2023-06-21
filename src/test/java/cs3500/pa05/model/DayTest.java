package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests Day functionality.
 */
public class DayTest {
  Day day;

  /**
   * Sets up a new day.
   */
  @BeforeEach
  public void setup() {
    day = new Day();
  }

  /**
   * Tests adding entries to a day.
   */
  @Test
  public void addEntryTest() {
    assertEquals(0, day.entries().size());
    Task newTask = new Task("test", DayOfWeek.SUNDAY, null, null);
    day.addEntry(newTask);
    assertTrue(day.entries().contains(newTask));
    assertFalse(day.entries(new FilterEvent()).contains(newTask));
  }

  /**
   * Tests removing entries from a day.
   */
  @Test
  public void removeEntry() {
    assertEquals(0, day.entries().size());
    Task newTask = new Task("test", DayOfWeek.SUNDAY, null, null);
    day.addEntry(newTask);
    assertTrue(day.entries().contains(newTask));
    assertFalse(day.entries(new FilterEvent()).contains(newTask));
    day.removeEntry(newTask);
    assertEquals(0, day.entries().size());
  }

  /**
   * Tests the toString method.
   */
  @Test
  public void testToString() {
    assertEquals("Day:\n", day.toString());
    day.addEntry(new Task("test", DayOfWeek.SUNDAY, null, null));
    assertEquals("Day:\nEntry:\nName: test\nDay: SUNDAY\n\nINCOMPLETE\n", day.toString());
  }
}