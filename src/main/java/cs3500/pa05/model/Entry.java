package cs3500.pa05.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
@JsonIncludeProperties({"name", "day", "description", "category"})
@JsonPropertyOrder({"name", "day", "description", "category"})
public abstract class Entry implements VisitableEntry {
    /**
     * Description of the bullet journal entry. (Optional)
     */
    @JsonProperty(value = "description")
    private final String description;
    /**
     * The day of the week the bullet journal entry falls under.
     */
    @JsonProperty(value = "day", required = true)
    private final DayOfWeek day;
    /**
     * The name of the bullet journal entry.
     */
    @JsonProperty(value = "name", required = true)
    private final String name;
    /**
     * Category the bullet journal entry falls under. (Optional)
     */
    @JsonProperty(value = "category")
    private String category;

    /**
     * Constructor for subclasses.
     *
     * @param name name of the entry
     * @param day day of the week the entry falls under
     * @param description description of the entry (optional)
     * @param category category the entry falls under (optional)
     */
    @JsonCreator
    protected Entry(
            @JsonProperty("name") String name,
            @JsonProperty("day") DayOfWeek day,
            @JsonProperty("description") String description,
            @JsonProperty("category") String category) {
        this.name = Objects.requireNonNull(name);
        this.day = Objects.requireNonNull(day);
        this.description = description;
        this.category = category;
    }

    /**
     * Gets the name of the entry.
     *
     * @return name of the entry
     */
    @JsonGetter("name")
    public String name() {
        return this.name;
    }

    /**
     * Gets the day of the week the entry falls under.
     *
     * @return day of the week the entry falls under
     */
    @JsonGetter("day")
    public DayOfWeek day() {
        return this.day;
    }

    /**
     * Gets the description of the entry. Returns null if no description was provided.
     *
     * @return description of the entry or null if no description was provided
     */
    @JsonGetter("description")
    public String description() {
        return this.description;
    }

    /**
     * Gets the category the entry falls under. Returns null if the entry does not fall under
     * a category.
     *
     * @return category the entry falls under or null if the entry does not fall under any category
     */
    @JsonGetter("category")
    public String category() {
        return this.category;
    }

    /**
     * Removes the entry from the category it currently falls under.
     */
    public void removeCategory() {
        this.category = null;
    }

    /**
     * Returns true if the entry is a task entry.
     *
     * @return true if the entry is a task entry
     */
    @JsonIgnore
    public boolean isTask() {
        return false;
    }

    /**
     * Returns true if the entry is an event.
     *
     * @return true if the entry is an event
     */
    @JsonIgnore
    public boolean isEvent() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entry:\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Day: ").append(day).append("\n");
        if (description != null) {
            sb.append("Description: ").append(description).append("\n");
        }
        if (category != null) {
            sb.append("Category: ").append(category).append("\n");
        }
        return sb.toString();
    }
}