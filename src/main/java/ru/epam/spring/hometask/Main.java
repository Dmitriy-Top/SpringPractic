package ru.epam.spring.hometask;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.shell.Bootstrap;
import ru.epam.spring.hometask.CLI.Commands;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().reset(); // off spring-msg
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        Commands.setCtx(ctx);
        Bootstrap.main(args);
    }
}
