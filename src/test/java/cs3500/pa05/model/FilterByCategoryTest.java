package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

/**
 * Tests filtering by category.
 */
public class FilterByCategoryTest {
  /**
   * Tests category getter.
   */
  @Test
  public void getterTest() {
    FilterByCategory filter = new FilterByCategory("bruh");
    assertEquals("bruh", filter.category());
    assertEquals("Filter Category", filter.type());
  }

  /**
   * Test getting type.
   */
  @Test
  public void typeTest() {
    assertEquals("Filter Category", new FilterByCategory("bruh").type());
  }

  /**
   * Tests organize.
   */
  @Test
  public void organizeTest() {
    final FilterByCategory filter = new FilterByCategory("nerd");
    final Collection<Entry> entries = new ArrayList<>();
    final Collection<Entry> expectedEntries = new ArrayList<>();
    entries.add(new Task("1", DayOfWeek.SUNDAY, null, "nerd"));
    entries.add(new Task("2", DayOfWeek.MONDAY, null, "nerd"));
    entries.add(new Task("3", DayOfWeek.TUESDAY, null, "nerd"));
    entries.add(new Task("4", DayOfWeek.WEDNESDAY, null, null));
    entries.add(new Task("5", DayOfWeek.THURSDAY, null, "bruh"));
    entries.add(new Task("6", DayOfWeek.FRIDAY, null, ""));
    entries.add(new Task("7", DayOfWeek.SATURDAY, null, "nerd"));

    expectedEntries.add(new Task("1", DayOfWeek.SUNDAY, null, "nerd"));
    expectedEntries.add(new Task("2", DayOfWeek.MONDAY, null, "nerd"));
    expectedEntries.add(new Task("3", DayOfWeek.TUESDAY, null, "nerd"));
    expectedEntries.add(new Task("7", DayOfWeek.SATURDAY, null, "nerd"));

    Collection<Entry> actual = filter.organize(entries);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualEntries = mapper.convertValue(actual, JsonNode.class);
    JsonNode expected = mapper.convertValue(expectedEntries, JsonNode.class);
    assertEquals(expected, actualEntries);
  }
}