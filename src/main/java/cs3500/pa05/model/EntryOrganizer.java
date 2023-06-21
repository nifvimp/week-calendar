package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Collection;

/**
 * An organization function to organize the entries in a bullet journal by.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = FilterByCategory.class, name = "filter-category"),
    @JsonSubTypes.Type(value = FilterByCompletion.class, name = "filter-completion"),
    @JsonSubTypes.Type(value = FilterEvent.class, name = "filter-event"),
    @JsonSubTypes.Type(value = FilterTask.class, name = "filter-task"),
    @JsonSubTypes.Type(value = SortByDay.class, name = "sort-day"),
    @JsonSubTypes.Type(value = SortByDuration.class, name = "sort-duration"),
    @JsonSubTypes.Type(value = SortByName.class, name = "sort-name")
})
public interface EntryOrganizer {
  /**
   * Organizes a collection of entries imposed by the implementation.
   *
   * @param entries entries to organize
   * @return organized collection of entries
   */
  Collection<Entry> organize(Collection<Entry> entries);

  /**
   * Gets the name of the organizer.
   *
   * @return name of organizer
   */
  String type();
}
