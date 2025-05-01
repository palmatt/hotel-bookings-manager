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

class AvailabilityTest {
    private static final LocalDate RELATIVE_NOW = LocalDate.of(2025,04,30);

    private Command availabilityCommand;

    @BeforeEach
    void setup() {
        availabilityCommand = new Availability(
                "H1",
                RELATIVE_NOW,
                RELATIVE_NOW.plusDays(10),
                "RT1");
    }

    @Test
    void shouldReturnAvailability() {
        availabilityCommand = new Availability(
                "H1",
                RELATIVE_NOW,
                RELATIVE_NOW.plusDays(10),
                "RT1");
        DataWrapper dataWrapper = buildDataWithAvailability();

        String result = availabilityCommand.execute(dataWrapper);

        Assertions.assertEquals("Availability(H1, 20250430-20250510, RT1) : 1", result);
    }

    private DataWrapper buildDataWithAvailability() {
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
                                .arrival(RELATIVE_NOW.minusDays(5))
                                .departure(RELATIVE_NOW.minusDays(5))
                                .hotelId("H1")
                                .roomType("RT1")
                                .build()))
                .build();
    }
}
