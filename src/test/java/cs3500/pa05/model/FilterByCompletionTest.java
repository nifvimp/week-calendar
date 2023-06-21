package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests filtering by completion.
 */
public class FilterByCompletionTest {
  /**
   * Tests getting type.
   */
  @Test
  public void typeTest() {
    FilterByCompletion filter = new FilterByCompletion(TaskStatus.COMPLETE);
    assertEquals("Filter Completion", filter.type());
  }

  /**
   * Tests status getter.
   */
  @Test
  public void statusTest() {
    assertEquals(new FilterByCompletion(TaskStatus.COMPLETE).status(), TaskStatus.COMPLETE);
    assertEquals(new FilterByCompletion(TaskStatus.INCOMPLETE).status(), TaskStatus.INCOMPLETE);
  }

  /**
   * Tests organize.
   */
  @Test
  public void organizeTest() {
    final FilterByCompletion filter = new FilterByCompletion(TaskStatus.COMPLETE);
    final Collection<Entry> entries = new ArrayList<>();
    Task task1 = new Task("1", DayOfWeek.SUNDAY, null, null);
    task1.setStatus(TaskStatus.COMPLETE);
    Task task2 = new Task("2", DayOfWeek.MONDAY, null, null);
    task2.setStatus(TaskStatus.COMPLETE);
    Task task3 = new Task("3", DayOfWeek.TUESDAY, null, null);
    task3.setStatus(TaskStatus.COMPLETE);
    entries.add(new Task("4", DayOfWeek.WEDNESDAY, null, null));
    entries.add(new Task("5", DayOfWeek.THURSDAY, null, null));
    entries.add(new Task("6", DayOfWeek.FRIDAY, null, null));
    Task task7 = new Task("7", DayOfWeek.SATURDAY, null, null);
    task7.setStatus(TaskStatus.COMPLETE);

    entries.addAll(List.of(task1, task2, task3, task7));
    Collection<Entry> expectedEntries = new ArrayList<>(List.of(task1, task2, task3, task7));
    Collection<Entry> actual = filter.organize(entries);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualEntries = mapper.convertValue(actual, JsonNode.class);
    JsonNode expected = mapper.convertValue(expectedEntries, JsonNode.class);
    assertEquals(expected, actualEntries);
  }
}