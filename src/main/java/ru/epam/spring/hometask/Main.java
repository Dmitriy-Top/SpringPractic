package ru.epam.spring.hometask;

import org.springframework.shell.Bootstrap;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        LogManager.getLogManager().reset();
        Bootstrap.main(args);
    }
}
