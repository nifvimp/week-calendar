package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a day in a bullet journal.
 */
public class Day {
    @JsonProperty("entries")
    private final List<Entry> entries;
    @JsonProperty("day")
    private final DayOfWeek day;


    /**
     * Creates a new day object.
     *
     * @param day day of the week
     */
    @JsonCreator
    public Day(@JsonProperty ("day") DayOfWeek day) {
        this.entries = new ArrayList<>();
        this.day = day;
    }

    /**
     * Gets the entries of this day.
     *
     * @return entries of this day
     */
    @JsonGetter("entries")
    public List<Entry> entries() {
        return new ArrayList<>(this.entries);
    }

//    public int getTaskAmount() {
//        return 0;
//    }
//
//    public int getEventAmount() {
//        return 0;
//    }

    /**
     * Adds the specified entries to this day's entries.
     *
     * @param entries entries to add
     */
    @JsonSetter("entries")
    public void addEntries(List<Entry> entries) {
        this.entries.addAll(entries);
    }

    /**
     * Adds the specified entry to this day's entries.
     *
     * @param entry entry to add
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Removes the specified entry from this day's entries.
     *
     * @param entry entry to remove
     * @return true if removal was successful
     */
    public boolean removeEntry(Entry entry) {
        return entries.remove(entry);
    }
}
