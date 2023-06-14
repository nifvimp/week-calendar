package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Timestamp(@JsonProperty("day") DayOfWeek day,
                        @JsonProperty("time") int time) {
}
