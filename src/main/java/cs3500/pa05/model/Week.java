package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;

// TODO: idk if this is necessary

public class Week {
    @JsonProperty("days-of-week")
    private final Map<DayOfWeek, Day> days = new LinkedHashMap<>();

    public Week() {
        for (DayOfWeek day : DayOfWeek.values()) {
            days.put(day, new Day(day));
        }
    }

    public Map<DayOfWeek, Day> days() {
        return new LinkedHashMap<>(days);
    }
}
