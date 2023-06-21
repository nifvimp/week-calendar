package cs3500.pa05;

import cs3500.pa05.controller.ApplicationController;
import cs3500.pa05.view.ApplicationView;
import cs3500.pa05.view.IApplicationView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Entry point of the GUI.
 * (Work around found from <a href="https://rb.gy/kpfxs">...</a>)
 */
public class GuiDriver extends Application {
  @Override
  public void start(Stage primaryStage) {
    ApplicationController controller = new ApplicationController();
    IApplicationView view = new ApplicationView(controller);
    try {
      Scene scene = view.load();
      scene.getStylesheets().add("src/main/resources/StyleSheet.css");
      primaryStage.setScene(scene);
      primaryStage.setTitle("Bullet Journal");
      controller.run();
      primaryStage.show();
    } catch (IllegalStateException e) {
      e.printStackTrace();
      System.err.println("Unable to load GUI.");
    }
  }
}
