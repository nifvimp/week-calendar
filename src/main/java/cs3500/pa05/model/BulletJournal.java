package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Representation of a bullet journal.
 */
@JsonIncludeProperties({"name", "event-max", "task-max", "categories", "week"})
@JsonPropertyOrder({"name", "event-max", "task-max", "categories", "week"})
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
   * The categories registered in the bullet journal.
   */
  @JsonProperty(value = "categories", required = true)
  private Set<String> categories;
  /**
   * A collection of the entries inside the bullet journal.
   */
  @JsonIgnore
  private final Collection<Entry> entries;

  /**
   * Creates a new bullet journal.
   *
   * @param name name of the bullet journal
   */
  public BulletJournal(String name) {
    this(name, new Week(), new HashSet<>());
  }

  /**
   * Creates a bullet journal.
   *
   * @param name name of the bullet journal
   * @param week information of the week in a bullet journal
   * @param categories categories in the bullet journal
   */
  @JsonCreator
  public BulletJournal(
      @JsonProperty("name") String name,
      @JsonProperty("week") Week week,
      @JsonProperty("categories") Set<String> categories
  ) {
    this.entries = new ArrayList<>();
    this.categories = categories;
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
   * Adds a category to the categories set.
   *
   * @param category category to add
   */
  public void addCategory(String category) {
    categories.add(category);
  }

  /**
   * Removes a category from the categories set.
   *
   * @param category category to remove
   */
  public void removeCategory(String category) {
    categories.remove(category);
  }

  /**
   * Gets all the categories registered to the bullet journal.
   *
   * @return the categories registered to the bullet journal
   */
  @JsonGetter("categories")
  public Set<String> getCategories() {
    return new HashSet<>(this.categories);
  }

  /**
   * Gets all the entries in the bullet journal.
   *
   * @return entries in the journal
   */
  public Collection<Entry> getAllEntries() {
    return new ArrayList<>(this.entries);
  }

  /**
   * Gets the sorted collection of entries in the bullet journal organized by the organizers
   * passed in.
   *
   * @param organizers to organize the entries by
   * @return sorted collection of entries in the journal organized by the organizers passed in.
   */
  public Collection<Entry> getAllEntries(Collection<EntryOrganizer> organizers) {
    Collection<Entry> entries = new ArrayList<>(this.entries);
    for (EntryOrganizer organizer : organizers) {
      entries = organizer.organize(entries);
    }
    return entries;
  }

  /**
   * Gets the map of the days of the week to the entries registered under them.
   *
   * @return map of the days of the week to the entries registered under them
   */
  public Map<DayOfWeek, Collection<Entry>> getEntryMap() {
    return this.week.getEntries();
  }

  /**
   * Gets the map of the days of the week to the entries registered under them organized by
   * the organizers passed in.
   *
   * @param organizers organizers to organize the entries by
   * @return map of the days of the week to the entries registered under them organized by
   *         the organizers passed in
   */
  public Map<DayOfWeek, Collection<Entry>> getEntryMap(Collection<EntryOrganizer> organizers) {
    return this.week.getEntries(organizers);
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
   */
  public void removeEntry(Entry entry) {
    week.removeEntry(entry);
  }
}