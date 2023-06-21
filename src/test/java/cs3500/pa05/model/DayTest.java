package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DayTest {
  Day day;

  @BeforeEach
  void setup() {
    day = new Day();
  }

  @Test
  void addEntryTest() {
    assertEquals(0, day.entries().size());
    Task newTask = new Task("test", DayOfWeek.SUNDAY, null, null);
    day.addEntry(newTask);
    assertTrue(day.entries().contains(newTask));
    assertFalse(day.entries(new FilterEvent()).contains(newTask));
  }

  @Test
  void removeEntry() {
    assertEquals(0, day.entries().size());
    Task newTask = new Task("test", DayOfWeek.SUNDAY, null, null);
    day.addEntry(newTask);
    assertTrue(day.entries().contains(newTask));
    assertFalse(day.entries(new FilterEvent()).contains(newTask));
    day.removeEntry(newTask);
    assertEquals(0, day.entries().size());
  }

  @Test
  void testToString() {
    assertEquals("Day:\n", day.toString());
    day.addEntry(new Task("test", DayOfWeek.SUNDAY, null, null));
    assertEquals("Day:\nEntry:\nName: test\nDay: SUNDAY\n\nINCOMPLETE\n", day.toString());
  }
}