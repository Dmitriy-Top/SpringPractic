package ru.epam.spring.hometask.CLI;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
@Component
public class Commands implements CommandMarker {
    private static Boolean isAuth = false;

    @CliAvailabilityIndicator({"view-event"})
    public boolean isCommandAvailable() {
        return isAuth;
    }

    @CliCommand(value = { "login" })
    public String login(
            @CliOption(key = "email",mandatory = true) String email) {
        isAuth = true;
        return email + "is authorise";
    }

    @CliCommand(value = { "registrtion" })
    public String registrtion(
            @CliOption(key = "email",mandatory = true) String email,
            @CliOption(key = "firstName",mandatory = true) String firstName,
            @CliOption(key = "lastName",mandatory = true) String lastName) {
        return email + "is registered";
    }
    @CliCommand(value = { "view-event" })
    public String viewEvent() {
        return "No event";
    }


}
