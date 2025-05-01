package org.example.hotelbookingsmanager.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.domain.model.Booking;
import org.example.hotelbookingsmanager.domain.model.DataWrapper;
import org.example.hotelbookingsmanager.domain.model.Period;
import org.example.hotelbookingsmanager.domain.model.sweepline.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class Search implements Command {
    private String hotelCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String roomType;

    @Override
    public String execute(DataWrapper dataWrapper) {
        LocalDate currentDate = startDate;
        int totalRooms = (int) dataWrapper.getHotels().stream()
                .filter(hotel -> hotelCode.equals(hotel.getId()))
                .flatMap(hotel -> hotel.getRooms().stream())
                .filter(room -> roomType.equals(room.getRoomType()))
                .count();
        List<Booking> relevantBookings = dataWrapper.getBookings().stream()
                .filter(this::isRelevantBooking)
                .toList();

        List<Period> periods = sweepLineProcessing(relevantBookings, totalRooms, currentDate);

        if (periods.size() == 1 && periods.getFirst().getFreeRooms() == 0) {
            return "";
        }

        return formatResponse(periods);
    }

    private List<Period> sweepLineProcessing(List<Booking> bookings,
                                             int totalRooms,
                                             LocalDate periodStart) {
        List<Event> events = new ArrayList<>();
        events.add(new Event(periodStart, 0));
        events.add(new Event(endDate, 0));

        for (Booking booking : bookings) {
            events.add(new Event(booking.getArrival(), -1));
            events.add(new Event(booking.getDeparture().plusDays(1), 1));
        }
        events.sort(Comparator.comparing(Event::getDate));

        List<Period> periods = new ArrayList<>();
        int currentOccupiedRooms = totalRooms;
        LocalDate currentStart = periodStart;

        for (Event event : events) {
            if (event.getDate().isAfter(endDate.plusDays(1))) {
                break;
            }

            if (event.getDate().isAfter(currentStart)) {
                if (periods.isEmpty() || periods.getLast().getFreeRooms() != currentOccupiedRooms) {
                    periods.add(new Period(currentStart, event.getDate().minusDays(1), currentOccupiedRooms));
                }
                currentStart = event.getDate();
            }
            currentOccupiedRooms += event.getDelta();
        }

        return periods;
    }

    private String formatResponse(List<Period> periods) {
        return periods.stream()
                .map(p -> String.format("(%s-%s, %d)",
                        DateTimeFormatter.BASIC_ISO_DATE.format(p.getFrom()),
                        DateTimeFormatter.BASIC_ISO_DATE.format(p.getTo()),
                        p.getFreeRooms()))
                .collect(Collectors.joining(", "));
    }

    private boolean isRelevantBooking(Booking booking) {
        return hotelCode.equals(booking.getHotelId()) && roomType.equals(booking.getRoomType())
                && (booking.getArrival() != null && booking.getDeparture() != null)
                && (booking.getArrival().isBefore(endDate) || booking.getDeparture().isBefore(endDate));
    }
}

