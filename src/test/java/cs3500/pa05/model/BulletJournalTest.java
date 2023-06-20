package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BulletJournalTest {
  BulletJournal journal;

  @BeforeEach
  void setup() {
    journal = new BulletJournal("test");
  }

  @Test
  void addCategoryTest() {
    assertFalse(journal.getCategories().contains("testCategory"));
    journal.addCategory("testCategory");
    assertTrue(journal.getCategories().contains("testCategory"));
  }

  @Test
  void removeCategoryTest() {
    assertFalse(journal.getCategories().contains("testCategory"));
    journal.addCategory("testCategory");
    assertTrue(journal.getCategories().contains("testCategory"));
    journal.removeCategory("testCategory");
    assertFalse(journal.getCategories().contains("testCategory"));
  }

  @Test
  void addEntryTest() {
    Entry newEntry = new Task("test", DayOfWeek.SUNDAY, "boring", null);
    assertFalse(journal.getAllEntries().contains(newEntry));
    journal.addEntry(newEntry);
    assertTrue(journal.getAllEntries().contains(newEntry));
  }

  @Test
  void removeEntryTest() {
    Entry newEntry = new Task("test", DayOfWeek.SUNDAY, "boring", null);
    assertFalse(journal.getAllEntries().contains(newEntry));
    journal.addEntry(newEntry);
    assertTrue(journal.getAllEntries().contains(newEntry));
    journal.removeEntry(newEntry);
    assertFalse(journal.getAllEntries().contains(newEntry));
  }

  @Test
  void taskMaxTest() {
    assertEquals(0, journal.getTaskMax());
    journal.setTaskMax(5);
    assertEquals(5, journal.getTaskMax());
  }

  @Test
  void eventMaxTest() {
    assertEquals(0, journal.getEventMax());
    journal.setEventMax(5);
    assertEquals(5, journal.getEventMax());
  }

  @Test
  void nameTest() {
    assertEquals("test", journal.getName());
    journal.setName("sup");
    assertEquals("sup", journal.getName());
  }

  @Test
  void getAllEntriesTest() {
    ArrayList<Entry> expected = new ArrayList<>();
    ArrayList<Entry> expected2 = new ArrayList<>();
    assertEquals(expected, journal.getAllEntries());
    Entry task1 = new Event("1", DayOfWeek.SUNDAY, new TimeInterval(
        new Timestamp(DayOfWeek.SUNDAY, 0), 10), null, null);
    Entry task2 = new Task("2", DayOfWeek.SUNDAY, null, null);
    expected.add(task1);
    expected.add(task2);
    expected2.add(task2);
    journal.addEntry(task1);
    journal.addEntry(task2);
    assertEquals(expected, journal.getAllEntries());
    assertEquals(expected2, journal.getAllEntries(new FilterTask()));
  }

  @Test
  void getEntryMapTest() {
    assertEquals(0, journal.getEntryMap().get(DayOfWeek.SUNDAY).size());
    ArrayList<Entry> expected = new ArrayList<>();
    ArrayList<Entry> expected2 = new ArrayList<>();
    assertEquals(expected, journal.getAllEntries());
    Entry event = new Event("1", DayOfWeek.SUNDAY, new TimeInterval(
        new Timestamp(DayOfWeek.SUNDAY, 0), 10), null, null);
    Entry task = new Task("2", DayOfWeek.SUNDAY, null, null);
    expected.add(event);
    expected.add(task);
    expected2.add(task);
    journal.addEntry(event);
    journal.addEntry(task);
    assertEquals(2, journal.getEntryMap().get(DayOfWeek.SUNDAY).size());
    assertTrue(journal.getEntryMap().get(DayOfWeek.SUNDAY).contains(task));
    assertFalse(journal.getEntryMap(new FilterEvent()).get(DayOfWeek.SUNDAY).contains(task));
  }
}