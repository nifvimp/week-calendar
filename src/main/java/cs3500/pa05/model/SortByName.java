package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts entries by name.
 * @implNote is case-sensitive
 */
public class SortByName implements EntryOrganizer, Comparator<Entry> {
  /**
   * Sorts a collection of entries by name.
   *
   * @param entries entries to filter
   * @implNote is case-sensitive
   */
  @Override
  public Collection<Entry> organize(Collection<Entry> entries) {
    List<Entry> organized = new ArrayList<>(entries);
    organized.sort(this);
    return organized;
  }

  @Override
  public int compare(Entry entry1, Entry entry2) {
    return entry1.name().compareTo(entry2.name());
  }

  @Override
  public String type() {
    return "Sort Name";
  }
}
