package org.example.hotelbookingsmanager.domain.exception;

public class UnrecognizedCommandException extends RuntimeException {
    public UnrecognizedCommandException(String message) {
        super(message);
    }
}
