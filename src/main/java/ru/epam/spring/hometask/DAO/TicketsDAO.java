package ru.epam.spring.hometask.DAO;

import ru.epam.spring.hometask.domain.*;
import ru.epam.spring.hometask.service.BookingService;
import ru.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitrii_Topolnik on 8/7/2017.
 */
public class TicketsDAO implements BookingService {
    private DiscountService ds;
    private ArrayList<Ticket> BD = new ArrayList<>();

    public TicketsDAO(DiscountService ds) {
        this.ds = ds;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        double summPrice = 0;
        Long numberOfTickets = (long) seats.size();
        double userDiscount = ds.getDiscount(user, event, dateTime, numberOfTickets);
        EventRating rating = event.getRating();
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        
        for (Long seat : seats) {
            double seatPrice = event.getBasePrice() * rating.getCoefficient() * vipSetCoeff(seat, auditorium);
            seatPrice -= (userDiscount / 100) * seatPrice;
            summPrice += seatPrice;
        }
        
        return summPrice;
    }

    private double vipSetCoeff(Long seat, Auditorium auditorium) {

        return (auditorium.getVipSeats().contains(seat)) ? 2.0 : 1;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        BD.addAll(tickets);

    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        HashSet<Ticket> tickets = new HashSet<>();
        for (Ticket ticket : BD){
            if (ticket.getDateTime().equals(dateTime) && ticket.getEvent().equals(event)) tickets.add(ticket);
        }
        return tickets;
    }
}
