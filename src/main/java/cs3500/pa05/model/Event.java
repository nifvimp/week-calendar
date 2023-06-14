package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "day", "interval", "description", "category"})
public class Event extends Entry {
    @JsonProperty(value = "interval", required = true)
    private TimeInterval interval;

    @JsonCreator
    public Event(
            @JsonProperty("name") String name,
            @JsonProperty("day") DayOfWeek day,
            @JsonProperty("interval") TimeInterval interval,
            @JsonProperty("description") String description,
            @JsonProperty("category") Category category) {
        super(name, day, description, category);
        this.interval = interval;
    }

    public Event(String name, DayOfWeek day, TimeInterval interval) {
        this(name, day, interval,null, null);
    }
}
