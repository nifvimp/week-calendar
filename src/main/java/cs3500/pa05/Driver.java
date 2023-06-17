package cs3500.pa05;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Week;
import cs3500.pa05.view.JournalViewImpl;
import java.util.HashSet;

/**
 * The entry point of the program.
 */
public class Driver {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        Week week = new Week();
        week.addEntry(new Task("Hello", DayOfWeek.MONDAY, "noo way", "Hello"));
        BulletJournal journal = new BulletJournal("Journal", week, new HashSet<>());
        journal.addCategory("Hello");
        journal.addCategory("World");
        JsonNode node = mapper.convertValue(journal, JsonNode.class);
        System.out.println(node);
        BulletJournal res = mapper.convertValue(node, BulletJournal.class);
        System.out.println(res);
        System.out.println(mapper.convertValue(res, JsonNode.class));

        new JournalViewImpl(new JournalControllerImpl());
//        launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // TODO: implement
//    }
}
