package org.example.hotelbookingsmanager.session;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hotelbookingsmanager.command.Exit;
import org.example.hotelbookingsmanager.domain.command.Command;
import org.example.hotelbookingsmanager.domain.model.DataWrapper;
import org.example.hotelbookingsmanager.parser.Parser;

import java.util.Scanner;

@Slf4j
@AllArgsConstructor
public class InputReader {
    private final Parser parser;

    public void startSession(DataWrapper dataWrapper) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("type your command: ");

                String input = scanner.nextLine();
                Command command = parser.parse(input);
                if (command instanceof Exit) {
                    break;
                }
                String result = command.execute(dataWrapper);
                System.out.println(result + "\n");
            }
        }
    }
}
