package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts entries by duration.
 */
public class SortByDuration implements EntryOrganizer, Comparator<Entry> {
  /**
   * Sorts a collection of entries by duration. Assumes tasks have a duration
   * of 0 minutes.
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
    int firstVal = (entry1.isEvent()) ? ((Event) entry1).interval().duration() : 0;
    int secondVal = (entry2.isEvent()) ? ((Event) entry2).interval().duration() : 0;
    return firstVal - secondVal;
  }

  @Override
  public String type() {
    return "Sort Duration";
  }
}
