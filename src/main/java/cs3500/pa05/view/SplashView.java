package cs3500.pa05.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents a simple welcome GUI view.
 */
public class SplashView {
  private final FXMLLoader loader;

  /**
   * Creates the SplashView to the user
   */
  public SplashView() {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("Splash.fxml"));
  }

  /**
   * @return Loads the fxml file given
   */
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
