package ru.epam.spring.hometask.DAO;

import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class EventDAO implements EventService {
    private List<Event> DB;

    private void init(){
        this.DB = new ArrayList<Event>();
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        for (Event event : DB) {
            if (event.getName().equals(name)) return event;
        }
        return null;
    }

    @Override
    public Event save(@Nonnull Event object) {
        object.setId(Event.counter++);
        DB.add(object);
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        DB.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        for (Event event : DB){
            if (event.getId() == id) return event;
        }
        return null;
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return DB;
    }
}
