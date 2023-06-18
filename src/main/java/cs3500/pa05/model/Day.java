package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a day in a bullet journal.
 */
@JsonIncludeProperties("entries")
public class Day {
    /**
     * The bullet journal registered under this day.
     */
    @JsonProperty("entries")
    private final Collection<Entry> entries;

    /**
     * Creates a day with the given entries.
     *
     * @param entries entries registered under the day
     */
    @JsonCreator
    public Day(@JsonProperty("entries") Collection<Entry> entries) {
        this.entries = entries;
    }

    /**
     * Creates a new day.
     */
    public Day() {
        this(new ArrayList<>());
    }

    /**
     * Gets the entries of the day.
     *
     * @return entries of the day
     */
    public Collection<Entry> entries() {
        return new ArrayList<>(this.entries);
    }

    /**
     * Gets the entries of the day organized by the passed in organizers.
     *
     * @param organizers organizers to organize the entries by
     * @return entries of the day organized by the passed in organizers
     */
    public Collection<Entry> entries(EntryOrganizer... organizers) {
        Collection<Entry> organized = new ArrayList<>(this.entries);
        for (EntryOrganizer organizer : organizers) {
            organized = organizer.organize(organized);
        }
        return organized;
    }

    /**
     * Adds the specified entry to the day's entries.
     *
     * @param entry entry to add
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Removes the specified entry from the day's entries.
     *
     * @param entry entry to remove
     */
    public void removeEntry(Entry entry) {
        entries.remove(entry);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Day:\n");
        for (Entry entry : entries) {
            sb.append(entry).append("\n");
        }
        return sb.toString();
    }
}
