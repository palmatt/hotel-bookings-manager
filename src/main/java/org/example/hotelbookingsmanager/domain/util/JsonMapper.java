package org.example.hotelbookingsmanager.domain.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonMapper {
    private static ObjectMapper instance;

    private JsonMapper() {
    }

    public static ObjectMapper getInstance() {
        if (instance == null) {
            instance = new ObjectMapper();
            configureJavaTimeModule(instance);
        }
        return instance;
    }

    private static void configureJavaTimeModule(ObjectMapper objectMapper) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(formatter));

        objectMapper.registerModule(javaTimeModule);
    }
}
