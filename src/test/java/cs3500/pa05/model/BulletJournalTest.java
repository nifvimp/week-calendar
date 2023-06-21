package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests bullet journal functionality.
 */
public class BulletJournalTest {
  BulletJournal journal;

  /**
   * Sets up empty journal.
   */
  @BeforeEach
  public void setup() {
    journal = new BulletJournal("test");
  }

  /**
   * Tests getting and setting password.
   */
  @Test
  public void passwordTest() {
    assertNull(journal.getPassword());
    journal.setPassword("coconut");
    assertEquals("coconut", journal.getPassword());
  }

  /**
   * Tests adding categories.
   */
  @Test
  public void addCategoryTest() {
    assertFalse(journal.getCategories().contains("testCategory"));
    journal.addCategory("testCategory");
    assertTrue(journal.getCategories().contains("testCategory"));
  }

  /**
   * Tests removing categories.
   */
  @Test
  public void removeCategoryTest() {
    assertFalse(journal.getCategories().contains("testCategory"));
    journal.addCategory("testCategory");
    assertTrue(journal.getCategories().contains("testCategory"));
    journal.removeCategory("testCategory");
    assertFalse(journal.getCategories().contains("testCategory"));
  }

  /**
   * Tests adding entries.
   */
  @Test
  public void addEntryTest() {
    Entry newEntry = new Task("test", DayOfWeek.SUNDAY, "boring", null);
    assertFalse(journal.getAllEntries().contains(newEntry));
    journal.addEntry(newEntry);
    assertTrue(journal.getAllEntries().contains(newEntry));
  }

  /**
   * Tests removing entire.
   */
  @Test
  public void removeEntryTest() {
    Entry newEntry = new Task("test", DayOfWeek.SUNDAY, "boring", null);
    assertFalse(journal.getAllEntries().contains(newEntry));
    journal.addEntry(newEntry);
    assertTrue(journal.getAllEntries().contains(newEntry));
    journal.removeEntry(newEntry);
    assertFalse(journal.getAllEntries().contains(newEntry));
  }

  /**
   * Tests getting and setting max tasks.
   */
  @Test
  public void taskMaxTest() {
    assertEquals(0, journal.getTaskMax());
    journal.setTaskMax(5);
    assertEquals(5, journal.getTaskMax());
  }

  /**
   * Tests getting and setting max events.
   */
  @Test
  public void eventMaxTest() {
    assertEquals(0, journal.getEventMax());
    journal.setEventMax(5);
    assertEquals(5, journal.getEventMax());
  }

  /**
   * Tests getting and setting the name.
   */
  @Test
  public void nameTest() {
    assertEquals("test", journal.getName());
    journal.setName("sup");
    assertEquals("sup", journal.getName());
  }

  /**
   * Tests getting entries from the journal.
   */
  @Test
  public void getAllEntriesTest() {
    final ArrayList<Entry> expected = new ArrayList<>();
    final ArrayList<Entry> expected2 = new ArrayList<>();
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

  /**
   * Tests getting the entries map.
   */
  @Test
  public void getEntryMapTest() {
    assertEquals(0, journal.getEntryMap().get(DayOfWeek.SUNDAY).size());
    ArrayList<Entry> expected = new ArrayList<>();
    assertEquals(expected, journal.getAllEntries());
    Entry event = new Event("1", DayOfWeek.SUNDAY, new TimeInterval(
        new Timestamp(DayOfWeek.SUNDAY, 0), 10), null, null);
    Entry task = new Task("2", DayOfWeek.SUNDAY, null, null);
    expected.add(event);
    expected.add(task);
    journal.addEntry(event);
    journal.addEntry(task);
    assertEquals(2, journal.getEntryMap().get(DayOfWeek.SUNDAY).size());
    assertTrue(journal.getEntryMap().get(DayOfWeek.SUNDAY).contains(task));
    assertFalse(journal.getEntryMap(new FilterEvent()).get(DayOfWeek.SUNDAY).contains(task));
  }

  /**
   * Tests clearing the journal.
   */
  @Test
  public void clearTest() {
    assertEquals(0, journal.getAllEntries().size());
    journal.addEntry(new Task("1", DayOfWeek.SUNDAY, null, null));
    journal.addEntry(new Task("2", DayOfWeek.SUNDAY, null, null));
    journal.addEntry(new Task("3", DayOfWeek.SUNDAY, null, null));
    assertEquals(3, journal.getAllEntries().size());
    journal.clear();
    assertEquals(0, journal.getAllEntries().size());
  }

  /**
   * Tests getting the currently applied organizers.
   */
  @Test
  public void organizersTest() {
    assertEquals("[]", journal.getOrganizers().toString());
    journal.setOrganizers(List.of(new FilterTask(), new SortByDay()));
    assertEquals(List.of("Filter Task", "Sort Day"), journal.getOrganizers().stream()
        .map(EntryOrganizer::type).toList());
  }
}