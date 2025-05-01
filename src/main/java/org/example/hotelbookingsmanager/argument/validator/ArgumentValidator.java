package org.example.hotelbookingsmanager.argument.validator;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.example.hotelbookingsmanager.domain.argument.ArgumentWrapper;
import org.example.hotelbookingsmanager.domain.exception.ArgumentException;

import static org.example.hotelbookingsmanager.domain.argument.Arguments.BOOKING_TAG;
import static org.example.hotelbookingsmanager.domain.argument.Arguments.HOTEL_TAG;

@UtilityClass
public class ArgumentValidator {
    public static ArgumentWrapper validateAndWrapArguments(String[] args) throws IllegalArgumentException {
        if (args.length != 4) {
            throw new ArgumentException("Invalid number of arguments. Expected 4 arguments.");
        }
        final String firstTag = args[0];
        final String secondTag = args[2];
        if (isInvalidTagArgument(firstTag) || isInvalidTagArgument(secondTag)) {
            throw new ArgumentException("Invalid argument. Expected either '--hotels' or '--bookings'.");
        }
        final String firstPath = args[1];
        final String secondPath = args[3];
        if (isInvalidPathArgument(firstPath) || isInvalidPathArgument(secondPath)) {
            throw new ArgumentException("Invalid path. Path cannot be empty.");
        }
        return firstTag.equals(HOTEL_TAG.value)
                ? new ArgumentWrapper(firstPath, secondPath)
                : new ArgumentWrapper(secondPath, firstPath);
    }

    private boolean isInvalidTagArgument(String tag) {
        return !(tag.equals(HOTEL_TAG.value) || tag.equals(BOOKING_TAG.value));
    }

    private boolean isInvalidPathArgument(String path) {
        return path.isBlank();
    }
}
