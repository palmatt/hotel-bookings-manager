package org.example.hotelbookingsmanager;


import org.example.hotelbookingsmanager.argument.ArgumentFileWrapper;
import org.example.hotelbookingsmanager.parser.Parser;
import org.example.hotelbookingsmanager.session.InputReader;

public class HotelBookingsManagerApplication {

    public static void main(String[] args) {
        ArgumentFileWrapper argumentFileWrapper = new ArgumentFileWrapper();
        Parser parser = new Parser();
        InputReader inputReader = new InputReader(parser);
        new App(argumentFileWrapper, inputReader).startApp(args);
    }

}
