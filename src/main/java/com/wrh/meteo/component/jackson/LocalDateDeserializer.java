package com.wrh.meteo.component.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/1/13 9:30
 * @describe
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            if (jsonParser != null && !ObjectUtils.isEmpty(jsonParser.getText())) {
                return LocalDate.parse(jsonParser.getText(), ymd);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
