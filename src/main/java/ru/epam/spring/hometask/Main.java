package ru.epam.spring.hometask;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.Bootstrap;
import ru.epam.spring.hometask.CLI.Commands;
import ru.epam.spring.hometask.ContextConfig.DAOConfig;
import ru.epam.spring.hometask.ContextConfig.WrapperConfig;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().reset(); // off spring-msg
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(DAOConfig.class, WrapperConfig.class);
        ctx.refresh();
        Commands.setCtx(ctx);
        Bootstrap.main(args);
    }
}
