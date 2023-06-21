package cs3500.pa05;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.controller.ApplicationController;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.FilterByCompletion;
import cs3500.pa05.model.FilterEvent;
import cs3500.pa05.model.TaskStatus;
import cs3500.pa05.model.TimeInterval;
import cs3500.pa05.model.Timestamp;
import cs3500.pa05.model.Week;
import cs3500.pa05.view.ApplicationView;
import cs3500.pa05.view.IApplicationView;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point of the program.
 */
public class Driver {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
//        Week week = new Week();
//        week.addEntry(new Event("Hello", DayOfWeek.MONDAY,
//            new TimeInterval(new Timestamp(DayOfWeek.MONDAY, 100), 10),
//            "noo way", "Hello"));
//        BulletJournal journal = new BulletJournal("Journal", week, new LinkedHashSet<>());
//        journal.addCategory("Hello");
//        journal.addCategory("World");
//        journal.setOrganizers(List.of(new FilterEvent(), new FilterByCompletion(TaskStatus.INCOMPLETE)));
//        JsonNode node = mapper.convertValue(journal, JsonNode.class);
//        System.out.println(node);
//        BulletJournal res = mapper.convertValue(node, BulletJournal.class);
//        System.out.println(res);
//        System.out.println(mapper.convertValue(res, JsonNode.class));

//        new JournalViewImpl(new JournalControllerImpl());
        GuiDriver.launch(GuiDriver.class, args);
    }
}
