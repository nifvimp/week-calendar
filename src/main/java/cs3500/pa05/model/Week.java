package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Container for the information of a week shown in a bullet journal.
 */
@JsonIncludeProperties("days-of-week")
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
      days.put(day, new Day());
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
   * Gets a map of the day of the week to the entries registered under them.
   *
   * @return map of the days of the week to the entries registered under them
   */
  public Map<DayOfWeek, Collection<Entry>> getEntries() {
    Map<DayOfWeek, Collection<Entry>> organized = new LinkedHashMap<>();
    for (DayOfWeek day : DayOfWeek.values()) {
      organized.put(day, days.get(day).entries());
    }
    return organized;
  }

  /**
   * Gets a map of the day of the week to the entries registered under them organized by the
   * passed in organizers.
   *
   * @param organizers organizers to organize the entries by
   * @return map of the days of the week to the entries registered under them organized by the
   *        organizers
   */
  public Map<DayOfWeek, Collection<Entry>> getEntries(EntryOrganizer... organizers) {
    Map<DayOfWeek, Collection<Entry>> organized = new LinkedHashMap<>();
    for (DayOfWeek day : DayOfWeek.values()) {
      organized.put(day, days.get(day).entries(organizers));
    }
    return organized;
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
   */
  public void removeEntry(Entry entry) {
    DayOfWeek day = entry.day();
    days.get(day).removeEntry(entry);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Week:\n");

    for (Map.Entry<DayOfWeek, Day> entry : days.entrySet()) {
      sb.append(entry.getKey()).append(":\n");
      Collection<Entry> entries = entry.getValue().entries();
      for (Entry e : entries) {
        sb.append(e).append("\n");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Clears the week of entries.
   */
  public void clear() {
    for (DayOfWeek day : DayOfWeek.values()) {
      days.put(day, new Day());
    }
  }
}