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
}
