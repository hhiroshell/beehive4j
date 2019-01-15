package jp.gr.java_conf.hhiroshell.beehive4j;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime datetime, JsonGenerator generator, SerializerProvider serializerProvider)
            throws IOException {
        generator.writeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(datetime));
    }

}