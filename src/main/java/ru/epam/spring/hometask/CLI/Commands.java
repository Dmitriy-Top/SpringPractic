package ru.epam.spring.hometask.CLI;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import ru.epam.spring.hometask.utils.wrapper.AuditoriumServiceWrapper;
import ru.epam.spring.hometask.utils.wrapper.UserServiceWraper;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
@Component
public class Commands implements CommandMarker {
    private static ConfigurableApplicationContext ctx;
    private static UserServiceWraper uswraper;
    private static AuditoriumServiceWrapper aswraper;

    @CliCommand(value = {"user-registration"})
    public String userRegistration(
            @CliOption(key = "email", mandatory = true) String email,
            @CliOption(key = "firstName", mandatory = true) String firstName,
            @CliOption(key = "lastName", mandatory = true) String lastName) {
        Long id = uswraper.regUser(email,firstName,lastName);
        return email + " is registered, id is " + id;
    }
    @CliCommand(value = {"user-delete"})
    public String userDelete(
            @CliOption(key = "id", mandatory = true) String id){
        long idl;
        try{
            idl = Long.parseLong(id);
        } catch (NumberFormatException e){
            return "not user id";
        }
        uswraper.delUser(idl);
        return "user where id is "+ idl +" is delete";
    }
    @CliCommand(value = {"getUserById"})
    public String getUserById(
            @CliOption(key = "id", mandatory = true) String id){
        long idl;
        String result;
        try{
            idl = Long.parseLong(id);
            result = uswraper.getUserById(idl);
        } catch (NumberFormatException e){
            return "not user id";
        }
        if (result==null) result = "not found";
        return "user where id is "+ idl +" is "+result;
    }
    @CliCommand(value = {"getUserByEmail"})
    public String getUserByEmail(
            @CliOption(key = "email", mandatory = true) String email){
        String result;
        result = uswraper.getUserByEmail(email);
        if (result==null) result = "not found";
        return "user where email is "+ email +" is "+result;
    }
    @CliCommand(value = {"getAllUser"})
    public String getAllUser(){
        String result;
        result = uswraper.getAll();
        return result;
    }
    @CliCommand(value = {"getAllAuditoriums"})
    public String getAllAuditoriums(){
        String result;
        result = aswraper.getAll();
        return result;
    }
    @CliCommand(value = {"getByNameAuditorium"})
    public String getByNameAuditorium(
            @CliOption(key = "name", mandatory = true) String name){
        String result;
        result = aswraper.getByName(name);
        return result;
    }

/*
    @CliCommand(value = { "login" })
    public String login(
            @CliOption(key = "email",mandatory = true) String email) {
        isAuth = true;
        return email + "is authorise";
    }

    @CliAvailabilityIndicator({"view-event"})
    public boolean isCommandAvailable() {
        return isAuth;
    }

    @CliCommand(value = { "view-event" })
    public String viewEvent() {
        return "No event";
    }*/

    public static void setCtx(ConfigurableApplicationContext ctx) {
        Commands.ctx = ctx;
        uswraper = ctx.getBean(UserServiceWraper.class);
        aswraper = ctx.getBean(AuditoriumServiceWrapper.class);
    }
}
