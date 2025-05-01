package org.example.hotelbookingsmanager.command;

import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.domain.model.Booking;
import org.example.hotelbookingsmanager.domain.model.DataWrapper;
import org.example.hotelbookingsmanager.domain.model.Hotel;
import org.example.hotelbookingsmanager.domain.model.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class SearchTest {
    private static final LocalDate RELATIVE_NOW = LocalDate.of(2025, 04, 30);
    private Command searchCommand;

    @BeforeEach
    void setup() {
        searchCommand = new Search(
                "H1",
                RELATIVE_NOW,
                RELATIVE_NOW.plusDays(10),
                "RT1");
    }

    @Test
    void shouldReturnAvailability() {
        DataWrapper dataWrapper = buildAvailabilityData();

        String result = searchCommand.execute(dataWrapper);

        Assertions.assertEquals("(20250430-20250430, 1), (20250501-20250503, 0), (20250504-20250504, 1), (20250505-20250509, 2)", result);
    }

    @Test
    void shouldReturnEmptyAvailability() {
        DataWrapper dataWrapper = buildEmptyAvailabilityData();

        String result = searchCommand.execute(dataWrapper);

        Assertions.assertEquals("", result);
    }

    @Test
    void shouldReturnSingleAvailability() {
        DataWrapper dataWrapper = buildSingleAvailabilityData();

        String result = searchCommand.execute(dataWrapper);

        Assertions.assertEquals("(20250430-20250509, 1)", result);
    }

    private DataWrapper buildAvailabilityData() {
        return DataWrapper.builder()
                .hotels(List.of(
                        Hotel.builder()
                                .id("H1")
                                .rooms(List.of(
                                        Room.builder()
                                                .roomType("RT1")
                                                .build(),
                                        Room.builder()
                                                .roomType("RT1")
                                                .build(),
                                        Room.builder()
                                                .roomType("RT1")
                                                .build(),
                                        Room.builder()
                                                .roomType("RT2")
                                                .build(),
                                        Room.builder()
                                                .build()))
                                .build(),
                        Hotel.builder()
                                .id("H2")
                                .build(),
                        Hotel.builder()
                                .build()))
                .bookings(List.of(
                        Booking.builder()
                                .arrival(RELATIVE_NOW.plusDays(1))
                                .departure(RELATIVE_NOW.plusDays(3))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build(),
                        Booking.builder()
                                .arrival(RELATIVE_NOW.minusDays(5))
                                .departure(RELATIVE_NOW.plusDays(50))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build(),
                        Booking.builder()
                                .arrival(RELATIVE_NOW.minusDays(3))
                                .departure(RELATIVE_NOW.plusDays(4))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build(),
                        Booking.builder()
                                .arrival(RELATIVE_NOW.minusDays(1))
                                .departure(RELATIVE_NOW.plusDays(3))
                                .hotelId("H2")
                                .roomType("RT2")
                                .build(),
                        Booking.builder()
                                .arrival(RELATIVE_NOW.minusDays(1))
                                .departure(RELATIVE_NOW.plusDays(3))
                                .hotelId("H1")
                                .roomType("RT2")
                                .build(),
                        Booking.builder()
                                .departure(RELATIVE_NOW.plusDays(3))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build(),
                        Booking.builder()
                                .arrival(RELATIVE_NOW.minusDays(1))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build(),
                        Booking.builder()
                                .arrival(RELATIVE_NOW.plusDays(50))
                                .departure(RELATIVE_NOW.plusDays(50))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build()))
                .build();
    }

    private DataWrapper buildEmptyAvailabilityData() {
        return DataWrapper.builder()
                .hotels(List.of(
                        Hotel.builder()
                                .id("H1")
                                .rooms(List.of(
                                        Room.builder()
                                                .roomType("RT1")
                                                .build()))
                                .build()))
                .bookings(List.of(
                        Booking.builder()
                                .arrival(RELATIVE_NOW)
                                .departure(RELATIVE_NOW.plusDays(10))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build()))
                .build();
    }

    private DataWrapper buildSingleAvailabilityData() {
        return DataWrapper.builder()
                .hotels(List.of(
                        Hotel.builder()
                                .id("H1")
                                .rooms(List.of(
                                        Room.builder()
                                                .roomType("RT1")
                                                .build()))
                                .build()))
                .bookings(List.of())
                .build();
    }
}
