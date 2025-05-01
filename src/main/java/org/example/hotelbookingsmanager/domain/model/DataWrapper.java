package org.example.hotelbookingsmanager.domain.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.hotelbookingsmanager.argument.ArgumentFileWrapper;
import org.example.hotelbookingsmanager.domain.exception.ObjectMappingException;
import org.example.hotelbookingsmanager.domain.util.JsonMapper;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DataWrapper {
    private List<Hotel> hotels;
    private List<Booking> bookings;

    public static DataWrapper wrapData(ArgumentFileWrapper fileWrapper) {
        final ObjectMapper jsonMapper = JsonMapper.getInstance();
        try {
            final List<Hotel> hotels = jsonMapper.readValue(fileWrapper.getHotelsJson(), new TypeReference<List<Hotel>>() {
            });
            final List<Booking> bookings = jsonMapper.readValue(fileWrapper.getBookingsJson(), new TypeReference<List<Booking>>() {
            });
            return DataWrapper.builder()
                    .hotels(hotels)
                    .bookings(bookings)
                    .build();
        } catch (JacksonException e) {
            throw new ObjectMappingException("failed to map json to object");
        }
    }

}
