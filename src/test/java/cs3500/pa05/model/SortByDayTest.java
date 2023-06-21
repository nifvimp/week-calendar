package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SortByDayTest {
  SortByDay sortByDay;
  Collection<Entry> entries;
  TimeInterval interval;

  @BeforeEach
  void setup() {
    sortByDay = new SortByDay();
    entries = new ArrayList<>();
    interval = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 10);
    entries.add(new Task("1", DayOfWeek.FRIDAY, null, null));
    entries.add(new Task("2", DayOfWeek.SATURDAY, null, null));
    entries.add(new Task("3", DayOfWeek.THURSDAY, null, null));
    entries.add(new Event("4", DayOfWeek.WEDNESDAY, interval, null, null));
    entries.add(new Event("5", DayOfWeek.TUESDAY, interval, null, null));
    entries.add(new Event("6", DayOfWeek.MONDAY, interval, null, null));
    entries.add(new Task("7", DayOfWeek.SUNDAY, null, null));
  }

  @Test
  void organizeTest() {
    Collection<Entry> expectedEntries = new ArrayList<>();
    expectedEntries.add(new Task("7", DayOfWeek.SUNDAY, null, null));
    expectedEntries.add(new Event("6", DayOfWeek.MONDAY, interval, null, null));
    expectedEntries.add(new Event("5", DayOfWeek.TUESDAY, interval, null, null));
    expectedEntries.add(new Event("4", DayOfWeek.WEDNESDAY, interval, null, null));
    expectedEntries.add(new Task("3", DayOfWeek.THURSDAY, null, null));
    expectedEntries.add(new Task("1", DayOfWeek.FRIDAY, null, null));
    expectedEntries.add(new Task("2", DayOfWeek.SATURDAY, null, null));

    Collection<Entry> actual = sortByDay.organize(entries);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualEntries = mapper.convertValue(actual, JsonNode.class);
    JsonNode expected = mapper.convertValue(expectedEntries, JsonNode.class);
    assertEquals(expected, actualEntries);
  }

  @Test
  void compareTest() {
    assertEquals(-1, sortByDay.compare((Entry) entries.toArray()[0], (Entry) entries.toArray()[1]));
    assertEquals(0, sortByDay.compare((Entry) entries.toArray()[0], (Entry) entries.toArray()[0]));
    assertEquals(6, sortByDay.compare((Entry) entries.toArray()[1], (Entry) entries.toArray()[6]));
  }
}