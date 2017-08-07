package ru.epam.spring.hometask.DAO.DiscountStrategys;

import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

/**
 * Created by Dmitrii_Topolnik on 8/7/2017.
 */
public abstract class AbstractStrategy {
    public abstract byte calc(User user,Event event,LocalDateTime airDateTime, long numberOfTickets);
}
