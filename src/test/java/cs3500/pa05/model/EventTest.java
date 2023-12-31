package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests event functionality.
 */
public class EventTest {
  Event event;
  TimeInterval interval;
  Event event2;

  /**
   * Sets up test events and intervals.
   */
  @BeforeEach
  public void setup() {
    interval = new TimeInterval(new Timestamp(DayOfWeek.SUNDAY, 0), 10);
    event = new Event("test", DayOfWeek.SUNDAY, interval, null, "nerd");
    TimeInterval interval2 = new TimeInterval(new Timestamp(DayOfWeek.SUNDAY, 0), 20);
    event2 = new Event("test2", DayOfWeek.MONDAY, interval2, null, null);
  }

  /**
   * Tests event getters.
   */
  @Test
  public void gettersTest() {
    assertEquals(interval, event.interval());
    assertEquals(DayOfWeek.SUNDAY, event.day());
    assertEquals("test", event.name());
    assertNull(event.description());
    assertEquals("nerd", event.category());
  }

  /**
   * Tests isEvent.
   */
  @Test
  public void isEventTest() {
    assertTrue(event.isEvent());
    assertTrue(event2.isEvent());
    assertFalse(event.isTask());
  }

  /**
   * Tests removing the category.
   */
  @Test
  public void removeCategoryTest() {
    assertEquals("nerd", event.category());
    event.removeCategory();
    assertNull(event.category());
  }

  /**
   * Tests visitable functionality.
   */
  @Test
  public void acceptTest() {
    MockVisitor visitor = new MockVisitor();
    Entry event = new Event("", DayOfWeek.SUNDAY,
        new TimeInterval(new Timestamp(DayOfWeek.SUNDAY, 0),
            10), null, null);
    visitor.visit(event);
    assertEquals("[Entry:\nName: \nDay: SUNDAY\n\n0:00 AM - 0:10 AM]",
        visitor.getEventsVisited().toString());
  }

  /**
   * Tests toString method.
   */
  @Test
  public void toStringTest() {
    assertEquals("Entry:\nName: test\nDay: SUNDAY\nCategory: nerd\n\n0:00 AM - 0:10 AM",
        event.toString());
    assertEquals("Entry:\nName: test2\nDay: MONDAY\n\n0:00 AM - 0:20 AM", event2.toString());
  }
}