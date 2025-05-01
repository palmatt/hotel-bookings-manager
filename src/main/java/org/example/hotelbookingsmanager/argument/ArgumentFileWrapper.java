package org.example.hotelbookingsmanager.argument;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.hotelbookingsmanager.domain.argument.ArgumentWrapper;
import org.example.hotelbookingsmanager.domain.exception.FileLoadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@NoArgsConstructor
@Getter
public class ArgumentFileWrapper {

    private String hotelsJson;
    private String bookingsJson;

    public void loadData(ArgumentWrapper wrapper) throws FileLoadException {
        try {
            hotelsJson = Files.readString(Path.of(wrapper.hotelJsonPath()));
            bookingsJson = Files.readString(Path.of(wrapper.bookingJsonPath()));
        } catch (IOException e) {
            throw new FileLoadException("failed to load json file", e);
        }
    }


}
