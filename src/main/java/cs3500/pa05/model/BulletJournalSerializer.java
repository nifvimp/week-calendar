package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class BulletJournalSerializer extends StdSerializer<BulletJournal> {
    public BulletJournalSerializer() {
        this(null);
    }

    public BulletJournalSerializer(Class<BulletJournal> t) {
        super(t);
    }

    @Override
    public void serialize(BulletJournal journal, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper mapper = (ObjectMapper) gen.getCodec();
        gen.writeStartObject();
        gen.writeNumberField("event-max", journal.getEventMax());
        gen.writeNumberField("task-max", journal.getTaskMax());
        gen.writeArrayFieldStart("categories");
        Category.write(mapper).forEach(category -> {
            try {
                gen.writeObject(category);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        gen.writeEndArray();
        gen.writeObjectField("week", journal.getWeek());
        gen.writeEndObject();
    }
}
