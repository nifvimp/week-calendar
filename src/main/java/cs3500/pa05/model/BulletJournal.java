package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonSerialize(using = BulletJournalSerializer.class)
@JsonDeserialize(using = BulletJournalDeserializer.class)
@JsonPropertyOrder({"name", "event-max", "task-max", "categories"})
public class BulletJournal {
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "event-max", required = true)
    private int eventMax;
    @JsonProperty(value = "task-max", required = true)
    private int taskMax;
    @JsonProperty(value = "week", required = true)
    private Week week;
    @JsonIgnore
    private List<Entry> entries = new ArrayList<>();

    public BulletJournal(String name, Week week) {
        this.name = name;
        this.week = week;
        eventMax = 0;
        taskMax = 1;
        Map<DayOfWeek, Day> days = week.days();
        for (Day day : days.values()) {
            this.entries.addAll(day.entries());
        }

        // TODO: Create stats class here. Bind shit to it?
    }

    @JsonGetter("name")
    public String name() {
        return this.name;
    }

    @JsonGetter("event-max")
    public int getEventMax() {
    return this.eventMax;
    }

    @JsonGetter("task-max")
    public int getTaskMax() {
        return this.taskMax;
    }

    @JsonSetter("event-max")
    public void setEventMax(int eventMax) {
        this.eventMax = eventMax;
    }

    @JsonSetter("task-max")
    public void setTaskMax(int taskMax) {
        this.taskMax = taskMax;
    }

    @JsonGetter("week")
    public Week getWeek() {
        return this.week;
    }

    public void add(DayOfWeek day, Entry entry) {
        // week.add(day, entry);
        // TODO: implement
    }

    public boolean remove(DayOfWeek day, Entry entry) {
        // week.remove(day, entry);
        // TODO: implement
        // returns true if removal successful
        return false;
    }
}
