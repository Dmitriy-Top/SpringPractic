package ru.epam.spring.hometask.DAO.DiscountStrategys;

import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

/**
 * Created by Dmitrii_Topolnik on 8/7/2017.
 */
public class Every10thTicketStrategy extends AbstractStrategy {

    @Override
    public byte calc(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        byte discount = 0;

        if (numberOfTickets % 10 == 0 || user.getTickets().size() % 10 == 9) discount = 50;
        return discount;
    }
}
