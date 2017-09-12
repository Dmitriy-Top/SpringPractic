package ru.epam.spring.hometask.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.epam.spring.hometask.domain.*;
import ru.epam.spring.hometask.service.AuditoriumService;
import ru.epam.spring.hometask.service.EventService;
import ru.epam.spring.hometask.utils.wrapper.AuditoriumServiceWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class EventDAO implements EventService {
    @Autowired
    private JdbcOperations jdbc;
    @Autowired
    private AuditoriumService auditoriumDAO;

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        Event event = jdbc.queryForObject("select * from events where name = ?", new Object[]{name}, new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet rs, int i) throws SQLException {
                Event event = new Event();
                event.setId(rs.getLong("eventid"));
                event.setName(rs.getString("name"));
                event.setBasePrice(rs.getDouble("baseprice"));
                event.setRating(EventRating.valueOf(rs.getString("rating")));
                Timestamp[] timestamps = (Timestamp[]) rs.getArray("airdates").getArray();
                event.setAirDatesFromeTimeStamps(timestamps);
                Long[] mapIDs = (Long[]) rs.getArray("auditoriumsmapid").getArray();
                event.setAuditoriums(auditoriumDAO.getAuditoriumsMap(mapIDs));
                return event;
            }
        });
        return event;
    }

    @Override
    public Event save(@Nonnull Event object) {
        if (object.getId() == null) {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbc.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO events (name,baseprice,rating,auditoriumsmapid,airdates) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, object.getName());
                    statement.setDouble(2, object.getBasePrice());
                    statement.setString(3, object.getRating().name());
                    NavigableMap<LocalDateTime, Auditorium> auditoriums = object.getAuditoriums();
                    Object[] objects = auditoriumDAO.saveAuditoriumsMap(auditoriums);
                    Array bigint = con.createArrayOf("bigint",objects);
                    statement.setArray(4, bigint);
                    statement.setArray(5, con.createArrayOf("timestamp with time zone", object.getTimeStampsFromeAirDates()));
                    return statement;
                }
            }, holder);
            long id = (Long) holder.getKeys().get("eventid");
            object.setId(id);
        } else {
            jdbc.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("UPDATE events SET name=?,baseprice=?,rating=?,auditoriumsmapid=?,airdates=? WHERE eventid = ?");
                    statement.setString(1, object.getName());
                    statement.setDouble(2, object.getBasePrice());
                    statement.setString(3, object.getRating().name());
                    statement.setArray(4, con.createArrayOf("bigint", auditoriumDAO.saveAuditoriumsMap(object.getAuditoriums())));
                    statement.setArray(5, con.createArrayOf("timestamp with time zone", object.getTimeStampsFromeAirDates()));
                    statement.setLong(6, object.getId());
                    return statement;
                }
            });
        }

        return object;
    }


    @Override
    public void remove(@Nonnull Event object) {
        List<Long> auditoriumsmapids = jdbc.queryForObject("SELECT auditoriumsmapid from events WHERE eventid=?", new Object[]{object.getId()},
                new RowMapper<List<Long>>() {
                    public List<Long> mapRow(ResultSet rs, int i) throws SQLException {
                        Long[] longs = (Long[]) rs.getArray("auditoriumsmapid").getArray();
                        return Arrays.asList(longs);
                    }
                });
        auditoriumDAO.removeAuditoriumMap(auditoriumsmapids);

        jdbc.update("DELETE frome events where eventid = ?", object.getId());
    }

    @Override
    public Event getById(@Nonnull Long id) {
        Event event = jdbc.queryForObject("select * from events where eventid = ?", new Object[]{id}, new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet rs, int i) throws SQLException {
                Event event = new Event();
                event.setId(rs.getLong("eventid"));
                event.setName(rs.getString("name"));
                event.setBasePrice(rs.getDouble("baseprice"));
                event.setRating(EventRating.valueOf(rs.getString("rating")));
                Timestamp[] timestamps = (Timestamp[]) rs.getArray("airdates").getArray();
                event.setAirDatesFromeTimeStamps(timestamps);
                Long[] mapIDs = (Long[]) rs.getArray("auditoriumsmapid").getArray();
                event.setAuditoriums(auditoriumDAO.getAuditoriumsMap(mapIDs));
                return event;
            }
        });
        return event;
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        List<Event> events = jdbc.query("SELECT * FROM events", new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet rs, int i) throws SQLException {
                Event event = new Event();
                event.setId(rs.getLong("eventid"));
                event.setName(rs.getString("name"));
                event.setBasePrice(rs.getDouble("baseprice"));
                event.setRating(EventRating.valueOf(rs.getString("rating")));
                Timestamp[] timestamps = (Timestamp[]) rs.getArray("airdates").getArray();
                event.setAirDatesFromeTimeStamps(timestamps);
                Long[] mapIDs = (Long[]) rs.getArray("auditoriumsmapid").getArray();
                event.setAuditoriums(auditoriumDAO.getAuditoriumsMap(mapIDs));
                return event;
            }
        });
        return events;
    }

}
