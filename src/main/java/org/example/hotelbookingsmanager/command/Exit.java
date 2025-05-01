package org.example.hotelbookingsmanager.command;

import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.domain.model.DataWrapper;

public class Exit implements Command {
    @Override
    public String execute(DataWrapper dataWrapper) {
        return "";
    }
}

