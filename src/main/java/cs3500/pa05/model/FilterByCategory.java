package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.Objects;

/**
 * Filters entries for a specific category.
 *
 * @implNote is case-sensitive
 */
public class FilterByCategory implements EntryOrganizer {
  @JsonProperty("category")
  private final String category;

  /**
   * Creates an entry organizer that filters for entries with the specified category.
   *
   * @param category category to filter for
   */
  @JsonCreator
  public FilterByCategory(@JsonProperty("category") String category) {
    this.category = Objects.requireNonNull(category);
  }

  /**
   * Filters a collection of entries for a specific category.
   *w
   * @param entries entries to filter
   * @implNote is case-sensitive
   */
  @Override
  public Collection<Entry> organize(Collection<Entry> entries) {
    return entries.stream().filter(entry -> category.equals(entry.category())).toList();
  }

  @Override
  public String type() {
    return "Filter Category";
  }

  /**
   * Gets the category this filter is filtering for.
   *
   * @return category the filter is filtering for
   */
  public String category() {
    return this.category;
  }
}
