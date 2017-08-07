package ru.epam.spring.hometask.DAO;

import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.service.DiscountService;
import ru.epam.spring.hometask.DAO.DiscountStrategys.AbstractStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Dmitrii_Topolnik on 8/7/2017.
 */
public class DiscountDAO implements DiscountService {
    private List<AbstractStrategy> strategies;

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        byte discount = 0;
        byte currentDiscount;
        for (AbstractStrategy strategy: strategies){
            currentDiscount = strategy.calc(user,event,airDateTime,numberOfTickets);
            if(currentDiscount > discount) discount = currentDiscount;
        }
        return discount;
    }

    public void setStrategies(List<AbstractStrategy> strategies) {
        this.strategies = strategies;
    }
}
