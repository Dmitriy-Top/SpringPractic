package ru.epam.spring.hometask.DAO.DiscountStrategys;

import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Dmitrii_Topolnik on 8/7/2017.
 */
public class BirthDayStrategy extends AbstractStrategy {
    @Override
    public byte calc(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        byte discount = 0;
        LocalDate userBirthDay = user.getBirthDay();
        LocalDate eventAirDate = airDateTime.toLocalDate();

        if (userBirthDay.getMonthValue() == eventAirDate.getMonthValue()){
            if(Math.abs(userBirthDay.getDayOfMonth() - eventAirDate.getDayOfYear()) >= 5) discount=5;
        }
        return discount;
    }
}
