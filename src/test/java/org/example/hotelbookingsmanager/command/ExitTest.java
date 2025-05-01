package org.example.hotelbookingsmanager.command;

import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.domain.model.DataWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExitTest {
    private Command exit = new Exit();

    @Test
    void shouldReturnEmptyString() {
        String result = exit.execute(new DataWrapper());

        Assertions.assertEquals("", result);
    }
}
