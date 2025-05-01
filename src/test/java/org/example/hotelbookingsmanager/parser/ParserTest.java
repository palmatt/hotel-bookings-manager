package org.example.hotelbookingsmanager.parser;

import org.example.hotelbookingsmanager.command.Availability;
import org.example.hotelbookingsmanager.command.Exit;
import org.example.hotelbookingsmanager.command.Search;
import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.domain.exception.UnrecognizedCommandException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void shouldReturnExitCommandWhenInputIsNull() {
        Command result = parser.parse(null);

        assertEquals(Exit.class, result.getClass());
    }

    @Test
    void shouldReturnExitCommandWhenInputIsBlank() {
        String input = "   ";

        Command result = parser.parse(input);

        assertEquals(Exit.class, result.getClass());
    }

    @Test
    void shouldParseAvailabilityCommandWithExactDate() {
        String input = "Availability(hotel123, 20231103, SINGLE)";

        Command result = parser.parse(input);

        assertEquals(Availability.class, result.getClass());
        Availability availability = (Availability) result;
        assertEquals("hotel123", availability.getHotelCode());
        assertEquals(LocalDate.of(2023, 11, 3), availability.getStartDate());
        assertEquals(LocalDate.of(2023, 11, 3), availability.getEndDate());
        assertEquals("SINGLE", availability.getRoomType());
    }

    @Test
    void shouldParseAvailabilityCommandWithDateRange() {
        String input = "Availability(hotel456, 20231101-20231105, DOUBLE)";

        Command result = parser.parse(input);

        assertEquals(Availability.class, result.getClass());
        Availability availability = (Availability) result;
        assertEquals("hotel456", availability.getHotelCode());
        assertEquals(LocalDate.of(2023, 11, 1), availability.getStartDate());
        assertEquals(LocalDate.of(2023, 11, 5), availability.getEndDate());
        assertEquals("DOUBLE", availability.getRoomType());
    }

    @Test
    void shouldParseSearchCommandCorrectly() {
        String input = "Search(hotel789, 10, SUITE)";

        Command result = parser.parse(input);

        assertEquals(Search.class, result.getClass());
        Search search = (Search) result;
        assertEquals("hotel789", search.getHotelCode());
        assertEquals(LocalDate.now().plusDays(10), search.getEndDate());
        assertEquals("SUITE", search.getRoomType());
    }

    @Test
    void shouldThrowExceptionWhenInputDoesNotMatchAnyPattern() {
        String input = "InvalidCommand(hotel123, 20231103, SINGLE)";

        UnrecognizedCommandException exception = assertThrows(
                UnrecognizedCommandException.class,
                () -> parser.parse(input)
        );

        assertEquals("Unknown or malformed command: " + input, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAvailabilityCommandHasInvalidDate() {
        String input = "Availability(hotel123, invalidDate, SINGLE)";

        UnrecognizedCommandException exception = assertThrows(
                UnrecognizedCommandException.class,
                () -> parser.parse(input)
        );

        assertEquals("Unknown or malformed command: " + input, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSearchCommandHasInvalidDaysAhead() {
        String input = "Search(hotel123, notANumber, SINGLE)";

        UnrecognizedCommandException exception = assertThrows(
                UnrecognizedCommandException.class,
                () -> parser.parse(input)
        );

        assertEquals("Unknown or malformed command: " + input, exception.getMessage());
    }
}
