package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests week functionality.
 */
public class WeekTest {
  Week week;
  Week week2;
  Task task3;

  /**
   * Sets up test week.
   */
  @BeforeEach
  void setup() {
    week = new Week();
    week2 = new Week();
    week.addEntry(new Task("2", DayOfWeek.SUNDAY, null, null));
    week.addEntry(new Task("1", DayOfWeek.SUNDAY, null, null));
    task3 = new Task("3", DayOfWeek.MONDAY, null, null);
    week.addEntry(task3);
    week2.addEntry(task3);
  }

  /**
   * Tests bulk adding entries to week.
   */
  @Test
  void addEntriesToTest() {
    ArrayList<Entry> entryList = new ArrayList<>();
    week.addEntriesTo(entryList);
    assertEquals("Entry:\nName: 2\nDay: SUNDAY\n\nINCOMPLETE", entryList.get(0).toString());
    assertEquals("Entry:\nName: 1\nDay: SUNDAY\n\nINCOMPLETE", entryList.get(1).toString());
  }

  /**
   * Tests getting entries from week.
   */
  @Test
  void getEntriesTest() {
    assertEquals("Entry:\nName: 2\nDay: SUNDAY\n\nINCOMPLETE",
        week.getEntries().get(DayOfWeek.SUNDAY).toArray()[0].toString());
    assertEquals("Entry:\nName: 1\nDay: SUNDAY\n\nINCOMPLETE",
        week.getEntries().get(DayOfWeek.SUNDAY).toArray()[1].toString());
    assertEquals(2, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);

    assertEquals("Entry:\nName: 1\nDay: SUNDAY\n\nINCOMPLETE",
        week.getEntries(new SortByName()).get(DayOfWeek.SUNDAY).toArray()[0].toString());
    assertEquals("Entry:\nName: 2\nDay: SUNDAY\n\nINCOMPLETE",
        week.getEntries(new SortByName()).get(DayOfWeek.SUNDAY).toArray()[1].toString());
    assertEquals(2, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);
  }

  /**
   * Tests adding entries to week.
   */
  @Test
  void addEntryTest() {
    assertEquals(2, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);
    assertEquals(1, week.getEntries().get(DayOfWeek.MONDAY).toArray().length);
    week.addEntry(new Task("4", DayOfWeek.SUNDAY, null, null));
    week.addEntry(new Task("5", DayOfWeek.SUNDAY, null, null));
    week.addEntry(new Task("6", DayOfWeek.SUNDAY, null, null));
    assertEquals(5, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);
    assertEquals(1, week.getEntries().get(DayOfWeek.MONDAY).toArray().length);
  }

  /**
   * Tests removing entries from week.
   */
  @Test
  void removeEntryTest() {
    assertEquals(2, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);
    assertEquals(1, week.getEntries().get(DayOfWeek.MONDAY).toArray().length);
    week.removeEntry(task3);
    assertEquals(2, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);
    assertEquals(0, week.getEntries().get(DayOfWeek.MONDAY).toArray().length);
  }

  /**
   * Tests toString method.
   */
  @Test
  void toStringTest() {
    assertEquals("""
        Week:
        SUNDAY:
        Entry:
        Name: 2
        Day: SUNDAY

        INCOMPLETE
        Entry:
        Name: 1
        Day: SUNDAY

        INCOMPLETE

        MONDAY:
        Entry:
        Name: 3
        Day: MONDAY

        INCOMPLETE

        TUESDAY:

        WEDNESDAY:

        THURSDAY:

        FRIDAY:

        SATURDAY:

        """, week.toString());
    assertEquals("""
        Week:
        SUNDAY:

        MONDAY:
        Entry:
        Name: 3
        Day: MONDAY

        INCOMPLETE

        TUESDAY:

        WEDNESDAY:

        THURSDAY:

        FRIDAY:

        SATURDAY:

        """, week2.toString());
  }

  /**
   * Tests clearing the week.
   */
  @Test
  void clear() {
    assertEquals(2, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);
    assertEquals(1, week.getEntries().get(DayOfWeek.MONDAY).toArray().length);
    week.clear();
    assertEquals(0, week.getEntries().get(DayOfWeek.SUNDAY).toArray().length);
    assertEquals(0, week.getEntries().get(DayOfWeek.MONDAY).toArray().length);
  }
}