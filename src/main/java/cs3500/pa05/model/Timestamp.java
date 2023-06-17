package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Timestamp in a bullet journal.
 *
 * @param day day the timestamp is on
 * @param time minutes from 00:00 of the specified day
 */
public record Timestamp(@JsonProperty("day") DayOfWeek day,
                        @JsonProperty("time") int time)
    implements Comparable<Timestamp> {
  @Override
  public int compareTo(Timestamp other) {
    return this.numRepresentation() - other.numRepresentation();
  }

  /**
   * Calculates the total number of minutes away from Sunday 00:00
   * (the minimum time on a bullet journal) the time the timestamp is on.
   *
   * @return integer representation of the timestamp
   */
  public int numRepresentation() {
    return (day.ordinal() * 1440 + time);
  }

  // TODO: decide if you want to use Military Time or normal time
  @Override
  public String toString() {
    // military
    return String.format("%02d:%02d", time / 60, time % 60);

    /*
      // normal time

      int hour = time / 60;
      String meridiem = (hour < 12) ? AM : PM;
      return String.format("%d:%02d %s", hour % 12, time % 60, meridiem);
     */
  }
}