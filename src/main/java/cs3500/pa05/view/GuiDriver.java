package cs3500.pa05.view;

import cs3500.pa05.controller.ApplicationController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point of the GUI.
 */
public class GuiDriver extends Application {
  @Override
  public void start(Stage primaryStage) {
    ApplicationController controller = new ApplicationController();
    InterfaceApplicationView view = new ApplicationView(controller);
    try {
      Scene scene = view.load();
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
