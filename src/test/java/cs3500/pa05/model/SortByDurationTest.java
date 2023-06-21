package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SortByDurationTest {
  SortByDuration sortByDuration;
  Collection<Entry> entries;
  TimeInterval interval1;
  TimeInterval interval2;
  TimeInterval interval3;
  TimeInterval interval4;
  TimeInterval interval5;
  Event event1;
  Event event2;
  Event event3;
  Event event4;
  Event event5;
  Task task1;
  Task task2;

  @BeforeEach
  void setup() {
    sortByDuration = new SortByDuration();
    entries = new ArrayList<>();
    interval1 = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 10);
    interval2 = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 20);
    interval3 = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 30);
    interval4 = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 40);
    interval5 = new TimeInterval(new Timestamp(DayOfWeek.THURSDAY, 10), 50);

    event1 = new Event("1", DayOfWeek.SUNDAY, interval1, null, null);
    event2 = new Event("2", DayOfWeek.MONDAY, interval2, null, null);
    event3 = new Event("3", DayOfWeek.TUESDAY, interval3, null, null);
    event4 = new Event("4", DayOfWeek.WEDNESDAY, interval4, null, null);
    event5 = new Event("5", DayOfWeek.THURSDAY, interval5, null, null);
    task1 = new Task("6", DayOfWeek.FRIDAY, null, null);
    task2 = new Task("7", DayOfWeek.SATURDAY, null, null);

    entries.addAll(List.of(event1, event2, event3, event4, event5, task1, task2));
  }

  @Test
  void organizeTest() {
    Collection<Entry> expectedEntries =
        new ArrayList<>(List.of(task1, task2, event1, event2, event3, event4, event5));

    Collection<Entry> actual = sortByDuration.organize(entries);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualEntries = mapper.convertValue(actual, JsonNode.class);
    JsonNode expected = mapper.convertValue(expectedEntries, JsonNode.class);
    assertEquals(expected, actualEntries);
  }

  @Test
  void compareTest() {
    assertEquals(-10, sortByDuration.compare((Entry) entries.toArray()[0], (Entry) entries.toArray()[1]));
    assertEquals(20, sortByDuration.compare((Entry) entries.toArray()[1], (Entry) entries.toArray()[6]));
    assertEquals(-20, sortByDuration.compare((Entry) entries.toArray()[6], (Entry) entries.toArray()[1]));
  }

  @Test
  void typeTest() {
    assertEquals("Sort Duration",sortByDuration.type());
  }
}