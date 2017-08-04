package ru.epam.spring.hometask.DAO;

import ru.epam.spring.hometask.domain.Auditorium;
import ru.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class AuditoriumDAO  implements AuditoriumService{

    private Set<Auditorium> DB;


    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return DB;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        for (Auditorium auditorium : DB){
            if (auditorium.getName().equals(name)) return auditorium;
        }
        return null;
    }

    public void setDB(Set<Auditorium> DB) {
        this.DB = DB;
    }
}
