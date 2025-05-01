package org.example.hotelbookingsmanager.parser;

import org.example.hotelbookingsmanager.command.Availability;
import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.command.Exit;
import org.example.hotelbookingsmanager.command.Search;
import org.example.hotelbookingsmanager.domain.exception.UnrecognizedCommandException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final Pattern AVAILABILITY_PATTERN = Pattern.compile(
            "Availability\\s*\\(\\s*(\\w+)\\s*,\\s*([\\d-]+)\\s*,\\s*(\\w+)\\s*\\)",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SEARCH_PATTERN = Pattern.compile(
            "Search\\s*\\(\\s*(\\w+)\\s*,\\s*(\\d+)\\s*,\\s*(\\w+)\\s*\\)",
            Pattern.CASE_INSENSITIVE
    );

    public Command parse(String input) {
        if (input == null || input.isBlank()) {
            return new Exit();
        }

        Matcher availabilityMatcher = AVAILABILITY_PATTERN.matcher(input);
        if (availabilityMatcher.matches()) {
            return parseAvailability(availabilityMatcher);
        }

        Matcher searchMatcher = SEARCH_PATTERN.matcher(input);
        if (searchMatcher.matches()) {
            return parseSearch(searchMatcher);
        }

        throw new UnrecognizedCommandException("Unknown or malformed command: " + input);
    }

    private Availability parseAvailability(Matcher matcher) {
        String hotelCode = matcher.group(1);
        String datePart = matcher.group(2);
        String roomType = matcher.group(3);
        LocalDate startDate;
        LocalDate endDate;

        if (datePart.contains("-")) {
            String[] dates = datePart.split("-");
            startDate = LocalDate.parse(dates[0], DATE_FORMAT);
            endDate = LocalDate.parse(dates[1], DATE_FORMAT);
        } else {
            startDate = LocalDate.parse(datePart, DATE_FORMAT);
            endDate = startDate;
        }

        return new Availability(hotelCode, startDate, endDate, roomType);
    }

    private Search parseSearch(Matcher matcher) {
        String hotelCode = matcher.group(1);
        int daysAhead = Integer.parseInt(matcher.group(2));
        String roomType = matcher.group(3);

        return new Search(hotelCode, LocalDate.now(), LocalDate.now().plusDays(daysAhead), roomType);
    }
}
