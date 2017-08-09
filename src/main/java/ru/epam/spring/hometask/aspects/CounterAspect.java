package ru.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dmitrii_Topolnik on 8/8/2017.
 */
@Aspect
public class CounterAspect {
    Map<Event, Long> getEventCounter = new HashMap<>();
    Map<Event, Long> getEventPriceCounter = new HashMap<>();
    Map<Event, Long> bookedTicketsCounter = new HashMap<>();

    @Pointcut("execution(* ru.epam.spring.hometask.DAO.EventDAO.getById(..)) || execution(* ru.epam.spring.hometask.DAO.EventDAO.getByName(..))")
    private void eventGetById() {
    }

    ;

    @Pointcut("execution(* ru.epam.spring.hometask.DAO.TicketsDAO.getTicketsPrice(..))")
    private void getEventPrice() {
    }

    ;

    @Pointcut("execution(* ru.epam.spring.hometask.DAO.TicketsDAO.bookTickets(..))")
    private void bookTickets() {
    }

    ;

    @AfterReturning(pointcut = "eventGetById()", returning = "event")
    public void countEventByIdCall(Event event) {
        if (!getEventCounter.containsKey(event)) {
            getEventCounter.put(event, 0l);
        }
        getEventCounter.put(event, getEventCounter.get(event) + 1);

    }

    @After("getEventPrice()")
    public void countGetEventPrice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        Event event = (Event) args[0];
        if (!getEventPriceCounter.containsKey(event)) {
            getEventPriceCounter.put(event, 0l);
        }
        getEventPriceCounter.put(event, getEventPriceCounter.get(event) + 1);
    }

    @After("bookTickets()")
    public void countBookTickets(JoinPoint jp) {
        Object[] args = jp.getArgs();
        Set<Ticket> tickets = (Set<Ticket>) args[0];
        for (Ticket ticket : tickets) {
            Event event = ticket.getEvent();
            if (!bookedTicketsCounter.containsKey(event)) {
                bookedTicketsCounter.put(event, 0l);
            }
            bookedTicketsCounter.put(event, bookedTicketsCounter.get(event) + 1);
        }

    }

    public Map<Event, Long> getGetEventCounter() {
        return getEventCounter;
    }

    public Map<Event, Long> getGetEventPriceCounter() {
        return getEventPriceCounter;
    }

    public Map<Event, Long> getBookedTicketsCounter() {
        return bookedTicketsCounter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event booking statistic \n");
        sb.append("1) How many times each event was accessed by name or id: \n");
        for (Map.Entry<Event, Long> eventLongEntry : getEventCounter.entrySet()) {
            sb.append("..." + eventLongEntry.getKey().getName() + " - " + eventLongEntry.getValue() + " times \n");
        }
        sb.append("\n");
        sb.append("2) How many times each event prices were queried: \n");
        for (Map.Entry<Event, Long> eventLongEntry : getEventPriceCounter.entrySet()) {
            sb.append("..." + eventLongEntry.getKey().getName() + " - " + eventLongEntry.getValue() + " times \n");
        }
        sb.append("\n");
        sb.append("3) How many times each event tickets were booked: \n");
        for (Map.Entry<Event, Long> eventLongEntry : bookedTicketsCounter.entrySet()) {
            sb.append("..." + eventLongEntry.getKey().getName() + " - " + eventLongEntry.getValue() + " tickets were booked \n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
