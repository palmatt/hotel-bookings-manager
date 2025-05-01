package org.example.hotelbookingsmanager.domain.command;

import org.example.hotelbookingsmanager.domain.model.DataWrapper;

public interface Command {
    String execute(DataWrapper dataWrapper);
}
