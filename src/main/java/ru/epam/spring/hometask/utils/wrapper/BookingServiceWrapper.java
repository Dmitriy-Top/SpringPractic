package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.Ticket;
import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.service.BookingService;
import ru.epam.spring.hometask.service.DiscountService;
import ru.epam.spring.hometask.service.EventService;
import ru.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Created by Dmitrii_Topolnik on 8/7/2017.
 */
public class BookingServiceWrapper {
    private BookingService bs;
    private EventService es;
    private UserService us;

    public BookingServiceWrapper(BookingService bs, EventService es, UserService us) {
        this.bs = bs;
        this.es = es;
        this.us = us;
    }

    public String getTicketPrice(String event, String airDate, String userEmail, String seatsStr[]) {
        double summPrice = 0;
        try {
            Long eventID = Long.parseLong(event);
            Event currentEvent = es.getById(eventID);

            if (currentEvent == null) return "Event not found";

            LocalDateTime airDateTime = LocalDateTime.parse(airDate, Event.FORMATTER);
            User user = us.getUserByEmail(userEmail);

            if (user == null) return "User not found";

            Set<Long> seats = convertStringToSetLong(seatsStr);

            if(currentEvent.getAuditoriums().get(airDateTime)==null)return "Event in choose air date not found";

            if(!currentEvent.getAuditoriums().get(airDateTime).getAllSeats().containsAll(seats))return "Seats not found";

            String result = String.valueOf(bs.getTicketsPrice(currentEvent, airDateTime, user, seats));
            return result;

        } catch (NumberFormatException e) {
            return "argument 'eventID' is wrong";
        } catch (DateTimeParseException e) {
            return "argument 'air date' is wrong";
        } catch (NullPointerException e) {
            return "argument is wrong";
        }

    }

    private Set<Long> convertStringToSetLong(String[] seatsStr) {
        Set<Long> set = new HashSet<>();
        for (String seatStr : seatsStr) {
            Long seat = Long.parseLong(seatStr);
            set.add(seat);
        }
        return set;
    }

    public String bookTicket(String event, String airDate, String userEmail, String seatsStr[]) {
        try {
            Long eventID = Long.parseLong(event);
            Event currentEvent = es.getById(eventID);

            if (currentEvent == null) return "Event not found";

            LocalDateTime airDateTime = LocalDateTime.parse(airDate, Event.FORMATTER);
            User user = us.getUserByEmail(userEmail);

            if (user == null) return "User not found";

            Set<Long> seats = convertStringToSetLong(seatsStr);

            if(currentEvent.getAuditoriums().get(airDateTime)==null)return "Event in choose air date not found";

            if(!currentEvent.getAuditoriums().get(airDateTime).getAllSeats().containsAll(seats))return "Seats not found";
            Set<Ticket> tickets = new HashSet<>();
            for (Long seat : seats){
            Ticket ticket = new Ticket(user,currentEvent,airDateTime,seat);
            tickets.add(ticket);
            }
            bs.bookTickets(tickets);
            return "seats: " + seats.toString() + " is booked";

        } catch (NumberFormatException e) {
            return "argument 'eventID' is wrong";
        } catch (DateTimeParseException e) {
            return "argument 'air date' is wrong";
        } catch (NullPointerException e) {
            return "argument is wrong";
        }
    }

    public String getPurchasedTicketsForEvent(String event, String airDate) {
        try {
            Long eventID = Long.parseLong(event);
            Event currentEvent = es.getById(eventID);

            if (currentEvent == null) return "Event not found";

            LocalDateTime airDateTime = LocalDateTime.parse(airDate, Event.FORMATTER);

            if(currentEvent.getAuditoriums().get(airDateTime)==null)return "Event in choose air date not found";

            Set<Ticket> tickets = bs.getPurchasedTicketsForEvent(currentEvent,airDateTime);

            return "Tickets, sell to event '" + currentEvent.getName() + "' on " + airDate + ", is " + tickets.size();

        } catch (NumberFormatException e) {
            return "argument 'eventID' is wrong";
        } catch (DateTimeParseException e) {
            return "argument 'air date' is wrong";
        } catch (NullPointerException e) {
            return "argument is wrong";
        }
    }
}
