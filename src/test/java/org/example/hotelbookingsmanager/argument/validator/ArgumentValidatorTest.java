package org.example.hotelbookingsmanager.argument.validator;

import org.example.hotelbookingsmanager.domain.argument.ArgumentWrapper;
import org.example.hotelbookingsmanager.domain.exception.ArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class ArgumentValidatorTest {
    private static final String HOTELS_TAG = "--hotels";
    private static final String HOTELS_PATH = "path/to/hotels.json";
    private static final String BOOKINGS_TAG = "--bookings";
    private static final String BOOKINGS_PATH = "path/to/bookings.json";

    @Test
    void shouldWrapArgumentsWhenValidArgumentsProvided() {
        String[] args = {HOTELS_TAG, HOTELS_PATH, BOOKINGS_TAG, BOOKINGS_PATH};

        ArgumentWrapper result = ArgumentValidator.validateAndWrapArguments(args);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(HOTELS_PATH, result.hotelJsonPath());
        Assertions.assertEquals(BOOKINGS_PATH, result.bookingJsonPath());
    }

    @Test
    void shouldThrowExceptionWhenInvalidNumberOfArgumentsProvided() {
        String[] args = {HOTELS_TAG, HOTELS_PATH};

        Assertions.assertThrows(ArgumentException.class,() -> ArgumentValidator.validateAndWrapArguments(args));
    }

    @Test
    void shouldThrowExceptionWhenFirstTagIsInvalid() {
        String[] args = {"--invalid", HOTELS_PATH, BOOKINGS_TAG, BOOKINGS_PATH};

        Assertions.assertThrows(ArgumentException.class,() -> ArgumentValidator.validateAndWrapArguments(args));
    }

    @Test
    void shouldThrowExceptionWhenSecondTagIsInvalid() {
        String[] args = {HOTELS_TAG, HOTELS_PATH, "--invalid", BOOKINGS_PATH};

        Assertions.assertThrows(ArgumentException.class,() -> ArgumentValidator.validateAndWrapArguments(args));
    }

    @Test
    void shouldThrowExceptionWhenPathIsInvalid() {
        String[] args = {HOTELS_TAG, "", BOOKINGS_TAG, BOOKINGS_PATH};

        Assertions.assertThrows(ArgumentException.class,() -> ArgumentValidator.validateAndWrapArguments(args));
    }

    @Test
    void shouldThrowExceptionWhenSecondPathIsInvalid() {
        String[] args = {HOTELS_TAG, HOTELS_PATH, BOOKINGS_TAG, ""};

        Assertions.assertThrows(ArgumentException.class,() -> ArgumentValidator.validateAndWrapArguments(args));
    }

    @Test
    void shouldReturnArgumentsInInvertedOrderWhenFirstTagIsBookings() {
        String[] args = {BOOKINGS_TAG, BOOKINGS_PATH, HOTELS_TAG, HOTELS_PATH};

        ArgumentWrapper result = ArgumentValidator.validateAndWrapArguments(args);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(BOOKINGS_PATH, result.bookingJsonPath());
        Assertions.assertEquals(HOTELS_PATH, result.hotelJsonPath());
    }

}
