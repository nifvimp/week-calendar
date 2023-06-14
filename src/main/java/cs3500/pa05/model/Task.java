package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"name", "day", "status", "description", "category"})
public class Task extends Entry {
    @JsonProperty(value = "status", required = true)
    private TaskStatus status;

    @JsonCreator
    public Task(
            @JsonProperty("name") String name,
            @JsonProperty("day") DayOfWeek day,
            @JsonProperty("description") String description,
            @JsonProperty("category") Category category) {
        super(name, day, description, category);
        this.status = TaskStatus.INCOMPLETE;
    }

    public Task(String name, DayOfWeek day) {
        this(name, day, null, null);
    }

    @JsonGetter("status")
    public TaskStatus getStatus() {
        return this.status;
    }

    @JsonSetter("status")
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean isTask() {
        return true;
    }
}
