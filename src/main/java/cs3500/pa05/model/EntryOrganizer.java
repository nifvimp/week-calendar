package cs3500.pa05.model;

import java.util.Collection;

/**
 * An organization function to organize the entries in a bullet journal by.
 */
public interface EntryOrganizer {
  /**
   * Organizes a collection of entries imposed by the implementation.
   *
   * @param entries entries to organize
   * @return organized collection of entries
   */
  Collection<Entry> organize(Collection<Entry> entries);
}
