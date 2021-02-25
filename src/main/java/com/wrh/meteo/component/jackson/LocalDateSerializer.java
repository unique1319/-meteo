package com.wrh.meteo.component.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/1/13 9:30
 * @describe
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localDate.format(ymd));
    }
}
