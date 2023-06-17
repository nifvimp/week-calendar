package cs3500.pa05.model;

import java.util.Collection;

/**
 * Filters entries for events.
 */
public class FilterEvent implements EntryOrganizer {
  /**
   * Filters a collection of entries for events.
   *
   * @param entries entries to filter
   */
  @Override
  public Collection<Entry> organize(Collection<Entry> entries) {
    return entries.stream().filter(Entry::isEvent).toList();
  }
}
