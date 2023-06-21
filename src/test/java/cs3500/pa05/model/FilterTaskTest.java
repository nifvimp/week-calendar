package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

class FilterTaskTest {
  @Test
  void organizeTest() {
    FilterTask filter = new FilterTask();
    Collection<Entry> entries = new ArrayList<>();
    Collection<Entry> expectedEntries = new ArrayList<>();
    TimeInterval interval = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 10);
    entries.add(new Task("1", DayOfWeek.SUNDAY, null, null));
    entries.add(new Task("2", DayOfWeek.MONDAY, null, null));
    entries.add(new Task("3", DayOfWeek.TUESDAY, null, null));
    entries.add(new Event("4", DayOfWeek.WEDNESDAY, interval, null, null));
    entries.add(new Event("5", DayOfWeek.THURSDAY, interval, null, null));
    entries.add(new Event("6", DayOfWeek.FRIDAY, interval, null, null));
    entries.add(new Task("7", DayOfWeek.SATURDAY, null, null));

    expectedEntries.add(new Task("1", DayOfWeek.SUNDAY, null, null));
    expectedEntries.add(new Task("2", DayOfWeek.MONDAY, null, null));
    expectedEntries.add(new Task("3", DayOfWeek.TUESDAY, null, null));
    expectedEntries.add(new Task("7", DayOfWeek.SATURDAY, null, null));

    Collection<Entry> actual = filter.organize(entries);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualEntries = mapper.convertValue(actual, JsonNode.class);
    JsonNode expected = mapper.convertValue(expectedEntries, JsonNode.class);
    assertEquals(expected, actualEntries);
  }
}