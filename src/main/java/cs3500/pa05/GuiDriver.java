package cs3500.pa05;

import cs3500.pa05.controller.ApplicationController;
import cs3500.pa05.view.ApplicationView;
import cs3500.pa05.view.IApplicationView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point of the GUI.
 */
public class GuiDriver extends Application {
  @Override
  public void start(Stage primaryStage) {
    ApplicationController controller = new ApplicationController();
    IApplicationView view = new ApplicationView(controller);
    try {
      //primaryStage.getStylesheets().add("css.css");
      primaryStage.setScene(view.load());
      controller.run();
      primaryStage.show();
    } catch (IllegalStateException e) {
      e.printStackTrace();
      System.err.println("Unable to load GUI.");
    }
  }
}
