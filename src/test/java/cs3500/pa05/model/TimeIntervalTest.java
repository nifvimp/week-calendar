package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeIntervalTest {
  TimeInterval interval;
  Timestamp start;
  TimeInterval interval2;
  TimeInterval interval3;

  @BeforeEach
  void setup() {
    start = new Timestamp(DayOfWeek.SUNDAY, 0);
    interval = new TimeInterval(start, 10);
    interval2 = new TimeInterval(new Timestamp(DayOfWeek.SUNDAY, 60), 10);
    interval3 = new TimeInterval(new Timestamp(DayOfWeek.SUNDAY, 780), 780);
  }

  @Test
  void gettersTest() {
    assertEquals(start, interval.start());
    Timestamp end = new Timestamp(DayOfWeek.SUNDAY, 10);
    assertEquals(end, interval.end());
    assertEquals(10, interval.duration());
  }

  @Test
  void toStringTest() {
    assertEquals("0:00 AM - 0:10 AM", interval.toString());
    assertEquals("1:00 AM - 1:10 AM", interval2.toString());
    assertEquals("1:00 PM - MONDAY 2:00 AM", interval3.toString());
  }

  @Test
  void declarationTest() {
    assertThrows(IllegalArgumentException.class, () -> new TimeInterval(
        new Timestamp(DayOfWeek.SUNDAY, 0), -10));
    TimeInterval interval4 = new TimeInterval(new Timestamp(DayOfWeek.SATURDAY, 780), 780);
    assertEquals(1560, interval4.end().time());
  }
}