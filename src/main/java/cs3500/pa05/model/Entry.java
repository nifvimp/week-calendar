package cs3500.pa05.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Date;
import java.util.Objects;

/**
 * An entry in a bullet journal.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Event.class, name = "event"),
        @JsonSubTypes.Type(value = Task.class, name = "task")
})
@JsonPropertyOrder({"name", "day", "description", "category"})
public abstract class Entry {
//    @JsonProperty(value = "creation-time")
    private final Date creationTime;
    @JsonProperty(value = "category")
    private final Category category;
    @JsonProperty(value = "description")
    private final String description;
    @JsonProperty(value = "day", required = true)
    private final DayOfWeek day;
    @JsonProperty(value = "name", required = true)
    private final String name;

    @JsonCreator
    protected Entry(
            @JsonProperty("name") String name,
            @JsonProperty("day") DayOfWeek day,
            @JsonProperty("description") String description,
            @JsonProperty("category") Category category) {
        this.name = Objects.requireNonNull(name);
        this.day = Objects.requireNonNull(day);
        this.description = description;
        this.category = category;
        this.creationTime = new Date();
    }

    @JsonGetter("name")
    public String name() {
        return this.name;
    }

    @JsonGetter("day")
    public DayOfWeek day() {
        return this.day;
    }

    @JsonGetter("description")
    public String description() {
        return this.description;
    }

    @JsonGetter("category")
    public Category category() {
        return this.category;
    }

//    @JsonGetter("creation-time")
    public Date creationTime() {
        return (Date) this.creationTime.clone();
    }

    @JsonIgnore
    public boolean isTask() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", name, day, description, category);
    }
}