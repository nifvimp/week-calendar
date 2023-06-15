package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;

/**
 * Deserializer for a bullet journal.
 */
public class BulletJournalDeserializer extends StdDeserializer<BulletJournal> {
    /**
     * Creates a standard bullet journal deserializer.
     */
    public BulletJournalDeserializer() {
        this(null);
    }

    private BulletJournalDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BulletJournal deserialize(JsonParser parser, DeserializationContext deserialized)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        JsonNode node = parser.getCodec().readTree(parser);
        Week week = mapper.convertValue(node.get("week"), Week.class);
        BulletJournal journal = new BulletJournal(week);
        journal.setEventMax(node.get("event-max").asInt());
        journal.setTaskMax(node.get("task-max").asInt());
        System.out.println(node.get("categories"));
        ArrayNode categories = mapper.createArrayNode();
        node.get("categories").forEach(categories::add);
        Category.load(categories, mapper);
        return journal;
    }
}
