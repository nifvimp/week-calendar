package cs3500.pa05.view;

import javafx.scene.Scene;

/**
 * Represents a GUI view for a bullet journal.
 */
public interface JournalView {
  /**
   * Loads a scene from a bullet journal GUI layout.
   *
   * @return the layout
   * @throws IllegalStateException if the FXML cannot be loaded
   */
  Scene load() throws IllegalStateException;
}
