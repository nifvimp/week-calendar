package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts entries by the day of the week they fall under.
 */
public class SortByDay implements EntryOrganizer, Comparator<Entry> {
  /**
   * Sorts a collection of entries by the day of the week they fall under.
   *
   * @param entries entries to sort
   */
  @Override
  public Collection<Entry> organize(Collection<Entry> entries) {
    List<Entry> organized = new ArrayList<>(entries);
    organized.sort(this);
    return organized;
  }

  @Override
  public int compare(Entry entry1, Entry entry2) {
    return entry1.day().ordinal() - entry2.day().ordinal();
  }

  @Override
  public String type() {
    return "Sort Day";
  }
}