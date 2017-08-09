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
    public double getTicketsPrice(Event event, LocalDateTime dateTime, User user,Set<Long> seats) {
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
    public void bookTickets(Set<Ticket> tickets) {
        BD.addAll(tickets);

    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        HashSet<Ticket> tickets = new HashSet<>();
        for (Ticket ticket : BD){
            if (ticket.getDateTime().equals(dateTime) && ticket.getEvent().equals(event)) tickets.add(ticket);
        }
        return tickets;
    }
}
