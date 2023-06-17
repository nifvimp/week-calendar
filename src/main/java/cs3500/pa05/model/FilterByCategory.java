package cs3500.pa05.model;

import java.util.Collection;

/**
 * Filters entries for a specific category.
 */
public class FilterByCategory implements EntryOrganizer {
  private final Category category;

  /**
   * Creates an entry organizer that filters for entries with the specified category.
   *
   * @param category category to filter for
   */
  public FilterByCategory(Category category) {
    this.category = category;
  }

  /**
   * Filters a collection of entries for a specific category.
   *
   * @param entries entries to filter
   */
  @Override
  public Collection<Entry> organize(Collection<Entry> entries) {
      return entries.stream().filter(entry -> entry.category().equals(category)).toList();
  }
}
