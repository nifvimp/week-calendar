package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Week {
    private final Map<DayOfWeek, Day> days = new LinkedHashMap<>();

    public Week() {
        for (DayOfWeek day : DayOfWeek.values()) {
            days.put(day, new Day(day));
        }
    }

    @JsonAnyGetter
    public Map<DayOfWeek, Day> getDays() {
        return new LinkedHashMap<>(days);
    }

    @JsonAnySetter
    private void add(String dayOfWeek, Day day) {
        days.put(DayOfWeek.valueOf(dayOfWeek), day);
    }

}
