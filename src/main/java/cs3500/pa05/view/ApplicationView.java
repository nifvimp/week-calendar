package cs3500.pa05.view;

import cs3500.pa05.controller.ApplicationController;
import cs3500.pa05.controller.IApplicationController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * A simple GUI view for the application.
 */
public class ApplicationView implements IApplicationView {
  private final FXMLLoader loader;
  /**
   * Makes a simple GUI view of the application.
   *
   * @param controller journal controller
   */
  public ApplicationView(IApplicationController controller) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("base.fxml"));
    this.loader.setController(controller);
  }

  @Override
  public Scene load() throws IllegalStateException {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new RuntimeException("Failed to load journal view.", e);
    }
  }
}