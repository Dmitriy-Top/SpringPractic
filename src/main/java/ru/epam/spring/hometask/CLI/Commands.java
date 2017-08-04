package ru.epam.spring.hometask.CLI;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.utils.UserBundle;
import ru.epam.spring.hometask.utils.wrapper.AuditoriumServiceWrapper;
import ru.epam.spring.hometask.utils.wrapper.EventServiceWrapper;
import ru.epam.spring.hometask.utils.wrapper.UserServiceWraper;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
@Component
public class Commands implements CommandMarker {
    private static ConfigurableApplicationContext ctx;
    private static UserServiceWraper uswraper;
    private static AuditoriumServiceWrapper aswraper;
    private static EventServiceWrapper eswraper;

    //User rules

    @CliAvailabilityIndicator({"login"}) //base
    public boolean isHasUser() {
        return !uswraper.isAuth();
    }

    @CliAvailabilityIndicator({"getAllAuditoriums", "getAuditoriumByName", "getEventById", "getEventByName", "getEventAll"})
    //user level
    public boolean userIsAuth() {
        return uswraper.isAuth();
    }

    @CliAvailabilityIndicator({"user-registration", "user-delete", "getUserById", "getUserByEmail", "getAllUser", "saveEvent", "removeEventById", "removeEventByName"})
    //admin level
    public boolean userIsAdmin() {
        return uswraper.isAdmin();
    }


    //Commands logic

    @CliCommand(value = {"login"})
    public String login(
            @CliOption(key = "email", mandatory = true) String email) {
        return uswraper.authUser(email);
    }

    @CliCommand(value = {"user-registration"})
    public String userRegistration(
            @CliOption(key = "email", mandatory = true) String email,
            @CliOption(key = "role", mandatory = true) String role,
            @CliOption(key = "firstName", mandatory = true) String firstName,
            @CliOption(key = "lastName", mandatory = true) String lastName) {
        return uswraper.regUser(email, role, firstName, lastName);
    }

    @CliCommand(value = {"user-delete"})
    public String userDelete(
            @CliOption(key = "id", mandatory = true) String id) {
        long idl;
        try {
            idl = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "id - wrong";
        }
        uswraper.delUser(idl);
        return "user where id is " + idl + " is delete";
    }

    @CliCommand(value = {"getUserById"})
    public String getUserById(
            @CliOption(key = "id", mandatory = true) String id) {
        long idl;
        try {
            idl = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "id - wrong";
        }
        return uswraper.getUserById(idl);
    }

    @CliCommand(value = {"getUserByEmail"})
    public String getUserByEmail(
            @CliOption(key = "email", mandatory = true) String email) {
        return uswraper.getUserByEmail(email);
    }

    @CliCommand(value = {"getAllUser"})
    public String getAllUser() {
        return uswraper.getAll();
    }

    @CliCommand(value = {"getAllAuditoriums"})
    public String getAllAuditoriums() {
        return aswraper.getAll();
    }

    @CliCommand(value = {"getAuditoriumByName"})
    public String getAuditoriumByName(
            @CliOption(key = "name", mandatory = true) String name) {
        return aswraper.getByName(name);
    }

    @CliCommand(value = {"saveEvent"})
    public String saveEvent(
            @CliOption(key = "name", mandatory = true) String name,
            @CliOption(key = "airDates", mandatory = true) String airDates,
            @CliOption(key = "basePrice", mandatory = true) String basePrice,
            @CliOption(key = "rating", mandatory = true) String rating,
            @CliOption(key = "auditoriums", mandatory = true) String auditoriums) {
        return eswraper.save(name, airDates, basePrice, rating, auditoriums);
    }

    @CliCommand(value = {"removeEventById"})
    public String removeEventById(
            @CliOption(key = "id", mandatory = true) String id) {
        Boolean isRemove = eswraper.remove(id);
        if (isRemove) return id + " is remove";
        return id + " not found";
    }

    @CliCommand(value = {"removeEventByName"})
    public String removeEventByName(
            @CliOption(key = "name", mandatory = true) String name) {
        Boolean isRemove = eswraper.removeByName(name);
        if (isRemove) return name + " is remove";
        return name + " not found";
    }

    @CliCommand(value = {"getEventByName"})
    public String getEventByName(
            @CliOption(key = "name", mandatory = true) String name) {
        String result = eswraper.getByName(name);
        return result;
    }

    @CliCommand(value = {"getEventById"})
    public String getEventById(
            @CliOption(key = "id", mandatory = true) String id) {
        String result = eswraper.getById(id);
        return result;
    }

    @CliCommand(value = {"getEventAll"})
    public String getEventAll() {
        String result = eswraper.getAll();
        return result;
    }


    public static void setCtx(ConfigurableApplicationContext ctx) {
        Commands.ctx = ctx;
        uswraper = ctx.getBean(UserServiceWraper.class);
        aswraper = ctx.getBean(AuditoriumServiceWrapper.class);
        eswraper = ctx.getBean(EventServiceWrapper.class);
    }
}
