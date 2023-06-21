package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TimeInterval;
import cs3500.pa05.model.Timestamp;
import cs3500.pa05.model.Week;
import cs3500.pa05.view.SplashView;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Represents a controller for the application.
 */
public class ApplicationController implements IApplicationController {
  private final ObjectMapper mapper = new ObjectMapper();
  private Event defaultEvent = new Event("", DayOfWeek.SUNDAY, new TimeInterval(
      new Timestamp(DayOfWeek.SUNDAY, 0), 60), null, null);
  private Task defaultTask = new Task("", DayOfWeek.SUNDAY, null, null);
  @FXML
  private Scene scene;
  @FXML
  private TabPane tabs;
  @FXML
  private MenuItem load;
  @FXML
  private MenuItem save;
  @FXML
  private MenuItem newWeek;
  @FXML
  private MenuItem createTask;
  @FXML
  private MenuItem createEvent;
  @FXML
  private MenuItem addCategory;
  @FXML
  private MenuItem removeCategory;

  @Override
  public void run() {
    showSplash();
    startupPopup();
    initMenuBar();
  }

  /**
   * Shows the splash screen.
   */
  private void showSplash() {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getClassLoader().getResource("Splash.fxml"));
    try {
      Stage stage = new Stage();
      Scene scene = loader.load();
      stage.setScene(scene);
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setOnShowing(e -> {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(event -> stage.close());
        delay.play();
      });
      stage.showAndWait();
    } catch (IOException e) {
      throw new RuntimeException("Failed to load splash screen." + e);
    }
  }

  /**
   * Prompts the use to choose the method to start the application.
   *  - load: choose a .bujo to load into the application.
   *  - new: starts the application in a new week bullet journal.
   *  - template: choose .bujo to load as a template for a new week bullet.
   */
  private void startupPopup() {
    ButtonType load = new ButtonType("load");
    ButtonType newWeek = new ButtonType("new");
    ButtonType template = new ButtonType("template");
    Alert alert = new Alert(Alert.AlertType.NONE);
    alert.getButtonTypes().addAll(load, newWeek, template);
    alert.showAndWait().ifPresent(response -> {
      if (response == load) {
        load();
      } else if (response == template) {
        template();
      }
      else if (response == newWeek) {
        Tab tab = new Tab();
        tab.setContent(new JournalComponent(new BulletJournal("new journal"), tabs, tab));
        applyWarning(tab);
        tabs.getTabs().add(tab);
      }
    });
  }

  /**
   * Prompts the user to select a .bujo file to use as a template for a new week bullet journal.
   */
  private void template() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setSelectedExtensionFilter(
        new FileChooser.ExtensionFilter("BUJO File", "*.bujo")
    );
    fileChooser.setInitialDirectory(
        // TODO: replace
//        new File(System.getProperty("user.home") + System.getProperty("file.separator"))
        new File("src/main/resources")
    );
    fileChooser.setTitle("Open .bujo File");
    File file = fileChooser.showOpenDialog(null);
    try {
      JsonNode journalNode = mapper.readTree(file);
      BulletJournal journal = mapper.convertValue(journalNode, BulletJournal.class);
      TextInputDialog dialog = new TextInputDialog("name");
      dialog.setTitle("Enter Name");
      dialog.setHeaderText("Name the new bullet journal.");
      dialog.setContentText("name: ");
      journal.clear();
      dialog.showAndWait();
      String name = dialog.getResult();
      if (name == null) {
        throw new RuntimeException("User exited dialog unexpectedly.");
      }
      journal.setName(name);
      Tab tab = new Tab();
      tab.setContent(new JournalComponent(journal, tabs, tab));
      tab.setText(dialog.getResult());
      applyWarning(tab);
      tabs.getTabs().add(tab);
    } catch (IOException e) {
      throw new RuntimeException(
          String.format("Could not read the chosen file '%s'.", file), e
      );
    }
  }

  /**
   * Initializes the main menu bar.
   */
  private void initMenuBar() {
    Node curr = tabs.getSelectionModel().getSelectedItem().getContent();
    load.setOnAction(e -> tabs.fireEvent(new JournalEvent(JournalEvent.LOAD)));
    save.setOnAction(e -> curr.fireEvent(new JournalEvent(JournalEvent.SAVE)));
    createEvent.setOnAction(e -> curr.fireEvent(
      new EntryModificationEvent(EntryModificationEvent.CREATE_ENTRY, defaultEvent))
    );
    createTask.setOnAction(e -> curr.fireEvent(
        new EntryModificationEvent(EntryModificationEvent.CREATE_ENTRY, defaultTask))
    );
    addCategory.setOnAction(e -> curr.fireEvent(new JournalEvent(JournalEvent.ADD_CATEGORY)));
    removeCategory.setOnAction(e -> curr.fireEvent(new JournalEvent(JournalEvent.REMOVE_CATEGORY)));
    newWeek.setOnAction(e -> newWeek());
    tabs.addEventFilter(JournalEvent.LOAD, e -> load());
    tabs.getTabs().add(newTabButton(tabs));
    initShortcuts();
  }

  /**
   * Initializes shortcuts.
   */
  private void initShortcuts() {
    load.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
    save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
    newWeek.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
    createEvent.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
    createTask.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN));
    addCategory.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));
    removeCategory.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
  }

  /**
   * Creates a tab that acts as a button and adds a new tab and selects it for the tab pane passed
   * in.
   *
   * @param tabPane tabPane to add a 'new tab button' to.
   * @return tab that acts as a new tab button
   */
  private Tab newTabButton(TabPane tabPane) {
    Tab addTab = new Tab("+");
    BulletJournal journal = new BulletJournal("new journal");
    Tab newJournal = new Tab();
    newJournal.setContent(new JournalComponent(journal, tabs, newJournal));
    newJournal.setText(journal.getName());
    applyWarning(newJournal);
    addTab.setClosable(false);
    tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
      if(newTab == addTab) {
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, newJournal);
        tabPane.getSelectionModel().select(Math.max(0, tabPane.getTabs().size() - 2));
      }
    });
    return addTab;
  }

  /**
   * Creates a new week tab.
   */
  private void newWeek() {
    int last = Math.max(0, tabs.getTabs().size() - 1);
    tabs.getSelectionModel().select(tabs.getTabs().get(last));
  }

  /**
   * Prompts the user to selected .bujo files to load into the application.
   */
  private void load() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setSelectedExtensionFilter(
        new FileChooser.ExtensionFilter("BUJO File", "*.bujo")
    );
    // TODO: replace initial directory. Currently like this for convince
    fileChooser.setInitialDirectory(
//        new File(System.getProperty("user.home") + System.getProperty("file.separator"))
        new File("src/main/resources")
    );
    fileChooser.setTitle("Open .bujo File");
    Collection<File> files = fileChooser.showOpenMultipleDialog(null);
    //  TODO: give option to make empty bujo? make a button to make new thing
    for (File file : files) {
      try {
        JsonNode journalNode = mapper.readTree(file);
        BulletJournal journal = mapper.convertValue(journalNode, BulletJournal.class);
        Tab tab = new Tab();
        tab.setContent(new JournalComponent(journal, tabs, tab));
        tab.setText(journal.getName());
        applyWarning(tab);
        int last = Math.max(0, tabs.getTabs().size() - 1);
        tabs.getTabs().add(last, tab);
      } catch (IOException e) {
        throw new RuntimeException(
            String.format("Could not read the chosen file '%s'.", file), e
        );
      }
    }
  }

  /**
   * Sets the close on request action handler of the tab to make a warning pop up.
   *
   * @param tab tab to apply handler to
   */
  private void applyWarning(Tab tab) {
    tab.setOnCloseRequest(e -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Warning!");
      alert.setHeaderText("The bullet journal tab being closed might not have been saved.");
      alert.setContentText("Do you want to continue?");
      alert.showAndWait().ifPresent( buttonType -> {
        if (buttonType != ButtonType.OK) {
          e.consume();
        }
      });
    });
  }
}
