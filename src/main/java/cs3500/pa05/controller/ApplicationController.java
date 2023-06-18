package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BulletJournal;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

/**
 * Represents a controller for the application.
 */
public class ApplicationController implements IApplicationController {
  private final ObjectMapper mapper = new ObjectMapper();
  @FXML
  private Scene scene;
  @FXML
  private TabPane tabs;
  @FXML
  private MenuItem load;
  @FXML
  private MenuItem save;
  @FXML
  private MenuItem addEntry;
  @FXML
  private MenuItem removeEntry;
  @FXML
  private MenuItem editEntry;
  @FXML
  private MenuItem addCategory;
  @FXML
  private MenuItem removeCategory;
  @FXML
  private MenuItem about;

  @Override
  public void run() {
    load();
    initMenuBar();
  }

  /**
   * Initializes the main menu bar.
   */
  private void initMenuBar() {
    Node curr = tabs.getSelectionModel().getSelectedItem().getContent();
    load.setOnAction(e -> tabs.fireEvent(
        new JournalEvent(JournalEvent.LOAD)));
    save.setOnAction(e -> curr.fireEvent(
        new JournalEvent(JournalEvent.SAVE)));
    addEntry.setOnAction(e -> curr.fireEvent(
        new JournalEvent(EntryModificationEvent.ADD_ENTRY)));
    removeEntry.setOnAction(e -> curr.fireEvent(
        new JournalEvent(EntryModificationEvent.REMOVE_ENTRY)));
    editEntry.setOnAction(e -> curr.fireEvent(
        new JournalEvent(EntryModificationEvent.EDIT_ENTRY)));
    addCategory.setOnAction(e -> curr.fireEvent(
        new JournalEvent(JournalEvent.ADD_CATEGORY)));
    removeCategory.setOnAction(e -> curr.fireEvent(
        new JournalEvent(JournalEvent.REMOVE_CATEGORY)));
    about.setOnAction(e -> tabs.fireEvent(
        new JournalEvent(JournalEvent.HELP)));
    tabs.addEventFilter(JournalEvent.LOAD, e -> load());
    tabs.addEventFilter(JournalEvent.HELP, e -> about());
    tabs.getTabs().add(newTabButton(tabs));
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
    newJournal.setContent(new JournalComponent(journal, tabs));
    newJournal.setText(journal.name());
    addTab.setClosable(false);
    tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
      if(newTab == addTab) {
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, newJournal);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
      }
    });
    return addTab;
  }

  /**
   * Prompts the user to selected .bujo files to load into the application.
   */
  private void load() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setSelectedExtensionFilter(
        new FileChooser.ExtensionFilter("BUJO File", "*.bujo")
    );
    // TODO: replace intial directory. Currently like this for convince
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
        tab.setContent(new JournalComponent(journal, tabs));
        tab.setText(journal.name());
        // TODO: set close action to make popup 'you didn't save!' thing
//        tab.setOnCloseRequest(e.bujo -> {
//
//        });
        tabs.getTabs().add(tab);
      } catch (IOException e) {
        throw new RuntimeException(
            String.format("Could not read the chosen file '%s'.", file), e
        );
      }
    }
  }

  private void about() {
    // TODO: do some helpful stuff. IDK if necessary.
  }
}
