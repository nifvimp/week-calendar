package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests timestamp.
 */
public class TimestampTest {
  Timestamp timestamp1;
  Timestamp timestamp2;
  Timestamp timestamp3;

  /**
   * Sets up test timestamps.
   */
  @BeforeEach
  public void setup() {
    timestamp1 = new Timestamp(DayOfWeek.SUNDAY, 0);
    timestamp2 = new Timestamp(DayOfWeek.SUNDAY, 20);
    timestamp3 = new Timestamp(DayOfWeek.SUNDAY, 780);
  }

  /**
   * Tests compareTo.
   */
  @Test
  public void compareTo() {
    assertEquals(-20, timestamp1.compareTo(timestamp2));
    assertEquals(780, timestamp3.compareTo(timestamp1));
    assertEquals(760, timestamp3.compareTo(timestamp2));
  }

  /**
   * Tests converting day & minutes to just minutes.
   */
  @Test
  public void numRepresentation() {
    assertEquals(0, timestamp1.numRepresentation());
    assertEquals(20, timestamp2.numRepresentation());
    assertEquals(780, timestamp3.numRepresentation());
  }

  /**
   * Tests toString method.
   */
  @Test
  public void testToString() {
    assertEquals("0:00 AM", timestamp1.toString());
    assertEquals("0:20 AM", timestamp2.toString());
    assertEquals("1:00 PM", timestamp3.toString());
  }
}