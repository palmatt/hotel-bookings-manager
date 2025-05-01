package org.example.hotelbookingsmanager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hotelbookingsmanager.argument.ArgumentFileWrapper;
import org.example.hotelbookingsmanager.argument.validator.ArgumentValidator;
import org.example.hotelbookingsmanager.domain.argument.ArgumentWrapper;
import org.example.hotelbookingsmanager.domain.exception.ArgumentException;
import org.example.hotelbookingsmanager.domain.exception.FileLoadException;
import org.example.hotelbookingsmanager.domain.exception.ObjectMappingException;
import org.example.hotelbookingsmanager.domain.exception.UnrecognizedCommandException;
import org.example.hotelbookingsmanager.domain.model.DataWrapper;
import org.example.hotelbookingsmanager.session.InputReader;

@Slf4j
@AllArgsConstructor
public class App {
    private final ArgumentFileWrapper fileWrapper;
    private final InputReader inputReader;

    public void startApp(String[] args) {
        try {
            log.debug("Starting app...");
            final ArgumentWrapper argumentWrapper = ArgumentValidator.validateAndWrapArguments(args);
            log.debug("Arguments validated and wrapped");
            fileWrapper.loadData(argumentWrapper);
            log.debug("Files loaded");
            final DataWrapper dataWrapper = DataWrapper.wrapData(fileWrapper);
            log.debug("Json mapped to pojo");
            log.debug("init complete starting session...");
            inputReader.startSession(dataWrapper);
            log.debug("session complete");
            System.exit(0);
        } catch (ArgumentException e) {
            log.error(e.getMessage());
            System.exit(1);
        } catch (FileLoadException e) {
            log.error(e.getMessage());
            System.exit(2);
        } catch (ObjectMappingException e) {
            log.error(e.getMessage());
            System.exit(3);
        } catch (UnrecognizedCommandException e) {
            log.error(e.getMessage());
            System.exit(4);
        }
    }

}
