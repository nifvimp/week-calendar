package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

/**
 * An event entry in a bullet journal.
 */
@JsonPropertyOrder({"name", "day", "interval", "description", "category"})
public class Event extends Entry {
    /**
     * Time interval where this event takes place
     */
    @JsonProperty(value = "interval", required = true)
    private TimeInterval interval;

    /**
     * Creates a new event bullet journal entry.
     *
     * @param name name of the event
     * @param day day of the week the event falls on
     * @param interval interval where the event takes place
     * @param description description of the event (optional)
     * @param category category the event falls under (optional)
     */
    @JsonCreator
    public Event(
            @JsonProperty("name") String name,
            @JsonProperty("day") DayOfWeek day,
            @JsonProperty("interval") TimeInterval interval,
            @JsonProperty("description") String description,
            @JsonProperty("category") Category category) {
        super(name, day, description, category);
        this.interval = Objects.requireNonNull(interval);
    }

    @Override
    public boolean isEvent() {
        return true;
    }
}
