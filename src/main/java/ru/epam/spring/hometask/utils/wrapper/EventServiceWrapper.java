package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.domain.Auditorium;
import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.EventRating;
import ru.epam.spring.hometask.service.AuditoriumService;
import ru.epam.spring.hometask.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public String save(String name, String[] airDates, String basePrice, String rating, String auditoriumString) {
        NavigableSet<LocalDateTime> airDatesSet = new TreeSet<>();
        Double br;
        EventRating er;
        NavigableMap<LocalDateTime, Auditorium> auditoriumMap = new TreeMap<>();
        for(String airDateString : airDates){
            try {
                LocalDateTime airDate = LocalDateTime.parse(airDateString, Event.FORMATTER);
                airDatesSet.add(airDate);
                Auditorium auditorium = as.getByName(auditoriumString);
                auditoriumMap.put(airDate, auditorium);

            } catch (DateTimeParseException e) {
                return "argument 'air dates' is wrong";
            } catch (NullPointerException e){
                return "argument 'auditorium' is wrong";
            }
        }
        try {

            br = Double.parseDouble(basePrice);
            er = EventRating.valueOf(rating);

        } catch (NumberFormatException e) {
            return "argument 'base price' is wrong";
        } catch (IllegalArgumentException e) {
            return "argument 'rating' is wrong";
        }
        Event ev = es.save(new Event(name, airDatesSet, br, er, auditoriumMap));
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

    private void init(){
        //test data
        //todo: clean after
        String[] dates = {"2017-08-11 12:00","2017-08-11 14:00"};
        save("Love and Rose",dates,"150","LOW","betta");
        String[] dates_2 = {"2017-08-12 11:00","2017-08-14 15:00","2017-08-17 17:00"};
        save("Piece of shit",dates_2,"270","HIGH","alpha");
    }
}
