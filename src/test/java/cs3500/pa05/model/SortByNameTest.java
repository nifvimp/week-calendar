package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests sorting by name.
 */
public class SortByNameTest {
  SortByName sortByName;
  Collection<Entry> entries;
  TimeInterval interval;

  /**
   * Sets up list of entries to sort.
   */
  @BeforeEach
  public void setup() {
    sortByName = new SortByName();
    entries = new ArrayList<>();
    interval = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 10);
    entries.add(new Task("a", DayOfWeek.FRIDAY, null, null));
    entries.add(new Task("b", DayOfWeek.SATURDAY, null, null));
    entries.add(new Task("c", DayOfWeek.THURSDAY, null, null));
    entries.add(new Event("d", DayOfWeek.WEDNESDAY, interval, null, null));
    entries.add(new Event("e", DayOfWeek.TUESDAY, interval, null, null));
    entries.add(new Event("/", DayOfWeek.MONDAY, interval, null, null));
    entries.add(new Task("1", DayOfWeek.SUNDAY, null, null));
  }

  /**
   * Tests organize.
   */
  @Test
  public void organizeTest() {
    Collection<Entry> expectedEntries = new ArrayList<>();
    expectedEntries.add(new Event("/", DayOfWeek.MONDAY, interval, null, null));
    expectedEntries.add(new Task("1", DayOfWeek.SUNDAY, null, null));
    expectedEntries.add(new Task("a", DayOfWeek.FRIDAY, null, null));
    expectedEntries.add(new Task("b", DayOfWeek.SATURDAY, null, null));
    expectedEntries.add(new Task("c", DayOfWeek.THURSDAY, null, null));
    expectedEntries.add(new Event("d", DayOfWeek.WEDNESDAY, interval, null, null));
    expectedEntries.add(new Event("e", DayOfWeek.TUESDAY, interval, null, null));

    Collection<Entry> actual = sortByName.organize(entries);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualEntries = mapper.convertValue(actual, JsonNode.class);
    JsonNode expected = mapper.convertValue(expectedEntries, JsonNode.class);
    assertEquals(expected, actualEntries);
  }

  /**
   * Tests compareTo.
   */
  @Test
  public void compareTest() {
    assertEquals(-1, sortByName.compare((Entry) entries.toArray()[0],
        (Entry) entries.toArray()[1]));
    assertEquals(0, sortByName.compare((Entry) entries.toArray()[0],
        (Entry) entries.toArray()[0]));
    assertEquals(49, sortByName.compare((Entry) entries.toArray()[1],
        (Entry) entries.toArray()[6]));
    assertEquals(51, sortByName.compare((Entry) entries.toArray()[1],
        (Entry) entries.toArray()[5]));
  }

  /**
   * Tests getting type.
   */
  @Test
  public void typeTest() {
    assertEquals("Sort Name", sortByName.type());
  }
}