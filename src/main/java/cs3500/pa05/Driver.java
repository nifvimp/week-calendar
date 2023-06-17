package cs3500.pa05;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.controller.ApplicationController;
import cs3500.pa05.view.ApplicationView;
import cs3500.pa05.view.IApplicationView;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point of the program.
 */
public class Driver extends Application {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
//        Week week = new Week();
//        week.addEntry(new Event("Hello", DayOfWeek.MONDAY,
//            new TimeInterval(new Timestamp(DayOfWeek.MONDAY, 100), 10),
//            "noo way", "Hello"));
//        BulletJournal journal = new BulletJournal("Journal", week, new HashSet<>());
//        journal.addCategory("Hello");
//        journal.addCategory("World");
//        JsonNode node = mapper.convertValue(journal, JsonNode.class);
//        System.out.println(node);
//        BulletJournal res = mapper.convertValue(node, BulletJournal.class);
//        System.out.println(res);
//        System.out.println(mapper.convertValue(res, JsonNode.class));

//        new JournalViewImpl(new JournalControllerImpl());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO: implement
    }
}
