package cs3500.pa05.view;

import cs3500.pa05.controller.JournalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class JournalViewImpl implements JournalView {
  private final FXMLLoader loader;
  /**
   * Makes a simple bullet journal GUI view.
   *
   * @param controller journal controller
   */
  public JournalViewImpl(JournalController controller) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("journal.fxml"));
    this.loader.setController(controller);
  }

  @Override
  public Scene load() throws IllegalStateException {
    // TODO: implement
    return null;
  }
}