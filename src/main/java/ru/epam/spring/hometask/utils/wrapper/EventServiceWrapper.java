package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.domain.Auditorium;
import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.EventRating;
import ru.epam.spring.hometask.service.AuditoriumService;
import ru.epam.spring.hometask.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class EventServiceWrapper {
    private EventService es;
    private AuditoriumService as;

    public EventServiceWrapper(EventService es, AuditoriumService as) {
        this.es = es;
        this.as = as;
    }

    public String save(String name, String airDates, String basePrice, String rating, String auditoriums) {
        NavigableSet<LocalDateTime> airDatesS = new TreeSet<>();
        Double br;
        EventRating er;
        NavigableMap<LocalDateTime, Auditorium> auditors = new TreeMap<>();
        try {
            LocalDateTime airDate = LocalDateTime.parse(airDates, Event.formatter);
            airDatesS.add(airDate);
            br = Double.parseDouble(basePrice);
            er = EventRating.valueOf(rating);
            Auditorium auditorium = as.getByName(auditoriums);
            auditors.put(airDate, auditorium);
        } catch (Exception e) {
            return "argument is wrong";
        }
        Event ev = es.save(new Event(name, airDatesS, br, er, auditors));
        return ev.toString();


    }

    public boolean remove(String id) {
        Long idL;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException exc) {
            return false;
        }
        Event ev = new Event();
        ev.setId(idL);
        es.remove(ev);
        return true;
    }

    public Boolean removeByName(String name) {
        Event taget = es.getByName(name);
        if (taget != null) {
            es.remove(taget);
            return true;
        }
        return false;

    }


    public String getByName(String name) {
        Event event = es.getByName(name);
        return event.toString();
    }

    public String getById(String id) {
        Long idL;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException exc) {
            return "not found";
        }
        Event event = es.getById(idL);
        if (event != null) return event.toString();
        return "not found";
    }

    public String getAll() {
        ArrayList<Event> events = (ArrayList<Event>) es.getAll();
        StringBuffer sb = new StringBuffer();
        for (Event event : events) {
            sb.append(event.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
