package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Container for the information of a week shown in a bullet journal.
 */
public class Week {
    /**
     * Map of a day of the week to the day object that contains the information
     * on the day.
     */
    @JsonProperty("days-of-week")
    private final Map<DayOfWeek, Day> days = new LinkedHashMap<>();

    /**
     * Creates a new week.
     */
    public Week() {
        for (DayOfWeek day : DayOfWeek.values()) {
            days.put(day, new Day(day));
        }
    }

    /**
     * Adds all the entries in the week to the passed in collection.
     *
     * @param entryList collection to add entries to.
     */
    public void addEntriesTo(Collection<Entry> entryList) {
        for (DayOfWeek day : DayOfWeek.values()) {
            entryList.addAll(days.get(day).entries());
        }
    }

    /**
     * Adds the specified entry to week.
     *
     * @param entry entry to add
     */
    public void addEntry(Entry entry) {
        DayOfWeek day = entry.day();
        days.get(day).addEntry(entry);
    }

    /**
     * Removes the specified entry from the week.
     *
     * @param entry entry to remove
     * @return true if removal was successful
     */
    public boolean removeEntry(Entry entry) {
        DayOfWeek day = entry.day();
        return days.get(day).removeEntry(entry);
    }
}
