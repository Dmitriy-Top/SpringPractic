package ru.epam.spring.hometask.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ru.epam.spring.hometask.domain.Auditorium;

/**
 * @author Yuriy_Tkach
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     *
     * @return set of all auditoriums
     */
    public @Nonnull Set<Auditorium> getAll();

    /**
     * Finding auditorium by name
     *
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public @Nullable Auditorium getByName(@Nonnull String name);

    public Object[] saveAuditoriumsMap(NavigableMap<LocalDateTime, Auditorium> auditoriums);

    void removeAuditoriumMap(List<Long> auditoriumsmapids);

    public NavigableMap<LocalDateTime, Auditorium> getAuditoriumsMap(Long[] mapIDs);

    public Auditorium getByID(long auditoriumid);
}
