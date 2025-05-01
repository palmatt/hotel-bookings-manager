package org.example.hotelbookingsmanager.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.domain.model.Booking;
import org.example.hotelbookingsmanager.domain.model.DataWrapper;
import org.example.hotelbookingsmanager.domain.model.Hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Availability implements Command {
    private final String hotelCode;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String roomType;

    @Override
    public String execute(DataWrapper dataWrapper) {
        List<Booking> bookings = dataWrapper.getBookings();
        long occupancy = bookings.stream()
                .filter(this::isBookingInRange)
                .count();
        List<Hotel> hotels = dataWrapper.getHotels();
        long totalRooms = hotels.stream()
                .filter(hotel -> hotel.getRooms() != null && hotel.getId().equals(hotelCode))
                .flatMap(hotel -> hotel.getRooms().stream())
                .filter(room -> room.getRoomType() != null && room.getRoomType().equals(roomType))
                .count();
        long totalAvailability = totalRooms - occupancy;

        return "Availability(" + hotelCode + ", " + DateTimeFormatter.BASIC_ISO_DATE.format(startDate) + "-" + DateTimeFormatter.BASIC_ISO_DATE.format(endDate) + ", " + roomType + ") : " + totalAvailability;
    }

    private boolean isBookingInRange(Booking booking) {
        return (hotelCode.equals(booking.getHotelId()) && roomType.equals(booking.getRoomType()))
                && (booking.getDeparture() != null
                && booking.getDeparture().isAfter(startDate)
                && booking.getArrival() != null
                && booking.getArrival().isBefore(endDate));
    }
}
