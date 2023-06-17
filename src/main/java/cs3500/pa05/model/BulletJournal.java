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

/**
 * Representation of a bullet journal.
 */
@JsonSerialize(using = BulletJournalSerializer.class)
@JsonDeserialize(using = BulletJournalDeserializer.class)
@JsonPropertyOrder({"name", "event-max", "task-max", "categories"})
public class BulletJournal {
    /**
     * The name of the journal.
     */
    @JsonProperty(value = "name", required = true)
    private String name;
    /**
     * The daily max number of events.
     */
    @JsonProperty(value = "event-max", required = true)
    private int eventMax;
    /**
     * The daily max number of tasks.
     */
    @JsonProperty(value = "task-max", required = true)
    private int taskMax;
    /**
     * Information of the week represented in the bullet journal.
     */
    @JsonProperty(value = "week", required = true)
    private Week week;
    /**
     * List of all the entries inside the bullet journal.
     */
    @JsonIgnore
    private List<Entry> entries = new ArrayList<>();

    /**
     * Creates a bullet journal.
     *
     * @param name name of the bullet journal.
     * @param week information in the bullet journal.
     */
    public BulletJournal(String name, Week week) {
        this.name = name;
        this.week = week;
        eventMax = 0;
        taskMax = 0;
        week.addEntriesTo(entries);
    }

    /**
     * Gets the name of the bullet journal.
     *
     * @return name of the bullet journal
     */
    @JsonGetter("name")
    public String name() {
        return this.name;
    }

    /**
     * Gets the daily max number of events.
     *
     * @return daily max number of events
     */
    @JsonGetter("event-max")
    public int getEventMax() {
    return this.eventMax;
    }

    /**
     * Gets the daily max number of tasks.
     *
     * @return daily max number of tasks.
     */
    @JsonGetter("task-max")
    public int getTaskMax() {
        return this.taskMax;
    }

    /**
     * Sets the daily max number of events.
     *
     * @param eventMax number to set daily max number of events to
     */
    @JsonSetter("event-max")
    public void setEventMax(int eventMax) {
        this.eventMax = eventMax;
    }

    /**
     * Sets the daily max number of tasks.
     *
     * @param taskMax number to set daily max number of tasks to
     */
    @JsonSetter("task-max")
    public void setTaskMax(int taskMax) {
        this.taskMax = taskMax;
    }

    /**
     * Gets the information of the week in the bullet journal.
     *
     * @return week object
     */
    @JsonGetter("week")
    public Week getWeek() {
        return this.week;
    }

    /**
     * Adds an entry into the bullet journal.
     *
     * @param entry entry to add
     */
    public void addEntry(Entry entry) {
        week.addEntry(entry);
    }

    /**
     * Removes an entry from the bullet journal.
     *
     * @param entry entry to remove
     * @return true if removal was successful
     */
    public boolean removeEntry(Entry entry) {
        return week.removeEntry(entry);
    }
}
