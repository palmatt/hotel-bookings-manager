package org.example.hotelbookingsmanager.domain.argument;

public enum Arguments {
    HOTEL_TAG("--hotels"),
    BOOKING_TAG("--bookings");

    public final String value;

    Arguments(String value) {
        this.value = value;
    }
}
