package cs3500.pa05.view;

import javafx.scene.Scene;

/**
 * Represents a GUI view for the application.
 */
public interface IApplicationView {
  /**
   * Loads a scene from GUI layout of the application.
   *
   * @return the layout
   * @throws IllegalStateException if the FXML cannot be loaded
   */
  Scene load() throws IllegalStateException;
}
