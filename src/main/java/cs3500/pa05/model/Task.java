package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * A task entry in a bullet journal.
 */
@JsonIncludeProperties({"name", "day", "status", "description", "category"})
@JsonPropertyOrder({"name", "day", "status", "description", "category"})
public class Task extends Entry {
    /**
     * The current completion status of the task.
     */
    @JsonProperty(value = "status", required = true)
    private TaskStatus status;

    /**
     * Creates a new task bullet journal entry.
     *
     * @param name name of the task
     * @param day day of the week the task falls on
     * @param description description of the task (optional)
     * @param category category the task falls under (optional)
     */
    @JsonCreator
    public Task(
            @JsonProperty("name") String name,
            @JsonProperty("day") DayOfWeek day,
            @JsonProperty("description") String description,
            @JsonProperty("category") String category) {
        super(name, day, description, category);
        this.status = TaskStatus.INCOMPLETE;
    }

    /**
     * Gets the current completion status of the task.
     *
     * @return current completion status of the task
     */
    @JsonGetter("status")
    public TaskStatus getStatus() {
        return this.status;
    }

    /**
     * Updates the current completion status of the task.
     *
     * @param status completion status to set the task to
     */
    @JsonSetter("status")
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", super.toString(), status.toString());
    }
}
