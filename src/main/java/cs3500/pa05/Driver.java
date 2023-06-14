package cs3500.pa05;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BulletJournal;
import cs3500.pa05.model.Category;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Week;

public class Driver {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        Week week = new Week();
        Category.add("Hello");
        Category.add("World");
        week.getDays().get(DayOfWeek.MONDAY)
                .addEntry(new Task("Hello", DayOfWeek.MONDAY, "noo way", Category.get("Hello")));
        BulletJournal journal = new BulletJournal(week);
        JsonNode node = mapper.convertValue(journal, JsonNode.class);
        System.out.println(node);
        BulletJournal res = mapper.convertValue(node, BulletJournal.class);
        System.out.println(res);
        System.out.println(mapper.convertValue(res, JsonNode.class));
    }
}
