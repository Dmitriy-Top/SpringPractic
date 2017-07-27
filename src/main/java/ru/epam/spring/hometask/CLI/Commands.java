package ru.epam.spring.hometask.CLI;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.utils.wrapper.UserServiceWraper;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
@Component
public class Commands implements CommandMarker {
    private static ConfigurableApplicationContext ctx;
    private static UserServiceWraper wraper;

    @CliCommand(value = {"registration"})
    public String registration(
            @CliOption(key = "email", mandatory = true) String email,
            @CliOption(key = "firstName", mandatory = true) String firstName,
            @CliOption(key = "lastName", mandatory = true) String lastName) {
        Long id = wraper.regUser(email,firstName,lastName);
        return email + " is registered, id is " + id;
    }
    @CliCommand(value = {"delete"})
    public String delete(
            @CliOption(key = "id", mandatory = true) String id){
        long idl;
        try{
            idl = Long.parseLong(id);
        } catch (NumberFormatException e){
            return "not user id";
        }
        wraper.delUser(idl);
        return "user where id is "+ idl +" is delete";
    }
    @CliCommand(value = {"getuserbyid"})
    public String getuserbyid(
            @CliOption(key = "id", mandatory = true) String id){
        long idl;
        String result;
        try{
            idl = Long.parseLong(id);
            result = wraper.getUserById(idl);
        } catch (NumberFormatException e){
            return "not user id";
        }
        if (result==null) result = "not found";
        return "user where id is "+ idl +" is "+result;
    }
    @CliCommand(value = {"getuserbyemail"})
    public String getuserbyemail(
            @CliOption(key = "email", mandatory = true) String email){
        String result;
        result = wraper.getUserByEmail(email);
        if (result==null) result = "not found";
        return "user where email is "+ email +" is "+result;
    }
    @CliCommand(value = {"getall"})
    public String getall(){
        String result;
        result = wraper.getAll();
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
        wraper = ctx.getBean(UserServiceWraper.class);
    }
}
