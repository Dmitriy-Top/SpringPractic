package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.service.EventService;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class EventServiceWrapper {
    private EventService es;

    /*public EventServiceWrapper(EventService es) {
        this.es = es;
    }

    public Long regEvent(String email, String firstName, String lastName) {
        Event event = new Event();
        event.setEmail(email);
        event.setFirstName(firstName);
        event.setLastName(lastName);

        event = es.save(event);
        return event.getId();
    }

    public void delEvent(long id) {

        Event event = new Event();
        event.setId(id);

        es.remove(event);
    }

    public String getEventById(long id){
        Event event = es.getById(id);
        return event.toString();
    }

    public String getEventByEmail(String email){
        Event event = es.getEventByEmail(email);
        return (event != null) ? event.toString() : "not found";
    }

    public String getAll() {
        StringBuffer sb = new StringBuffer();
        for (Event event : es.getAll()){
            sb.append(event.toString());
            sb.append("\n");
        }
        return sb.toString();
    }*/
}
