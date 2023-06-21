package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

/**
 * Tests filtering for event.
 */
public class FilterEventTest {
  /**
   * Tests organize.
   */
  @Test
  public void organize() {
    final FilterEvent filter = new FilterEvent();
    final Collection<Entry> entries = new ArrayList<>();
    final Collection<Entry> expectedEntries = new ArrayList<>();
    final TimeInterval interval = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 10);
    entries.add(new Task("1", DayOfWeek.SUNDAY, null, null));
    entries.add(new Task("2", DayOfWeek.MONDAY, null, null));
    entries.add(new Task("3", DayOfWeek.TUESDAY, null, null));
    entries.add(new Event("4", DayOfWeek.WEDNESDAY, interval, null, null));
    entries.add(new Event("5", DayOfWeek.THURSDAY, interval, null, null));
    entries.add(new Event("6", DayOfWeek.FRIDAY, interval, null, null));
    entries.add(new Task("7", DayOfWeek.SATURDAY, null, null));

    expectedEntries.add(new Event("4", DayOfWeek.WEDNESDAY, interval, null, null));
    expectedEntries.add(new Event("5", DayOfWeek.THURSDAY, interval, null, null));
    expectedEntries.add(new Event("6", DayOfWeek.FRIDAY, interval, null, null));

    Collection<Entry> actual = filter.organize(entries);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualEntries = mapper.convertValue(actual, JsonNode.class);
    JsonNode expected = mapper.convertValue(expectedEntries, JsonNode.class);
    assertEquals(expected, actualEntries);
  }

  /**
   * Tests getting type.
   */
  @Test
  public void typeTest() {
    FilterEvent filter = new FilterEvent();
    assertEquals("Filter Event", filter.type());
  }
}