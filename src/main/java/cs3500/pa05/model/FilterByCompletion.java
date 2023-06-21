package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;

/**
 * Filters entries for incomplete tasks.
 */
public class FilterByCompletion extends FilterTask {
  @JsonProperty("status")
  private TaskStatus status;

  /**
   * Creates an organizer that filters for entries with specified task status.
   *
   * @param status task status to filter for
   */
  @JsonCreator
  public FilterByCompletion(@JsonProperty("status") TaskStatus status) {
    this.status = status;
  }

  /**
   * Filters a collection of entries for tasks that are incomplete.
   *
   * @param entries entries to filter
   */
  @Override
  public Collection<Entry> organize(Collection<Entry> entries) {
    entries = super.organize(entries);
    return entries.stream().filter(entry -> ((Task) entry).getStatus() == status).toList();
  }

  @Override
  public String type() {
    return "Filter Completion";
  }

  /**
   * Gets the status this filter is filtering for.
   *
   * @return status this filter is filtering for
   */
  public TaskStatus status() {
    return this.status;
  }
}
