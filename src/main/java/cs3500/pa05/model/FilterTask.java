package cs3500.pa05.model;

import java.util.Collection;

/**
 * Filters entries for tasks.
 */
public class FilterTask implements EntryOrganizer {
  /**
   * Filters a collection of entries for tasks.
   *
   * @param entries entries to filter
   */
  @Override
  public Collection<Entry> organize(Collection<Entry> entries) {
    return entries.stream().filter(Entry::isTask).toList();
  }
}
