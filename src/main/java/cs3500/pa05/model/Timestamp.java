package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Timestamp in a bullet journal.
 *
 * @param day day the timestamp is on
 * @param time minutes from 00:00 of the specified day
 */
public record Timestamp(@JsonProperty("day") DayOfWeek day,
                        @JsonProperty("time") int time) {
//    implements Comparable<Timestamp> {
//  @Override
//  public int compareTo(Timestamp other) {
//    return this.total() - other.total();
//  }
//  /**
//   * Calculates the total number of minutes away from Sunday 00:00
//   * (the minimum time on a bullet journal) the time the timestamp is on.
//   *
//   * @return integer representation of the timestamp
//   */
//  private int total() {
//    return (day.ordinal() * 1440 + time);
//  }
}