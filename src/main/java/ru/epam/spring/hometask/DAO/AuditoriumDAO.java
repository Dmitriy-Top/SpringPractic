package ru.epam.spring.hometask.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.epam.spring.hometask.domain.Auditorium;
import ru.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class AuditoriumDAO implements AuditoriumService {
    @Autowired
    private JdbcOperations jdbc;

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        List<Auditorium> auditoriums = jdbc.query("SELECT * FROM auditoriums", new RowMapper<Auditorium>() {
            @Override
            public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                Auditorium auditor = new Auditorium();
                auditor.setId(resultSet.getLong("auditoriumid"));
                auditor.setName(resultSet.getString("name"));
                auditor.setNumberOfSeats(resultSet.getLong("seats"));
                Long[] vipseats = (Long[]) resultSet.getArray("vipseats").getArray();
                auditor.setVipSeats(new HashSet<Long>(Arrays.asList(vipseats)));
                return auditor;
            }
        });
        return new HashSet<>(auditoriums);
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        Auditorium auditorium = jdbc.queryForObject("SELECT * FROM auditoriums WHERE name=?", new Object[]{name}, new RowMapper<Auditorium>() {
            @Override
            public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                Auditorium auditor = new Auditorium();
                auditor.setId(resultSet.getLong("auditoriumid"));
                auditor.setName(resultSet.getString("name"));
                auditor.setNumberOfSeats(resultSet.getLong("seats"));
                Long[] vipseats = (Long[]) resultSet.getArray("vipseats").getArray();
                auditor.setVipSeats(new HashSet<Long>(Arrays.asList(vipseats)));
                return auditor;
            }
        });
        return auditorium;
    }

    public Auditorium getByID(long auditoriumid) {
        Auditorium auditorium = jdbc.queryForObject("SELECT * FROM auditoriums WHERE auditoriumid=?", new Object[]{auditoriumid}, new RowMapper<Auditorium>() {
            @Override
            public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                Auditorium auditor = new Auditorium();
                auditor.setId(resultSet.getLong("auditoriumid"));
                auditor.setName(resultSet.getString("name"));
                auditor.setNumberOfSeats(resultSet.getLong("seats"));
                Long[] vipseats = (Long[]) resultSet.getArray("vipseats").getArray();
                auditor.setVipSeats(new HashSet<Long>(Arrays.asList(vipseats)));
                return auditor;
            }
        });
        return auditorium;
    }


    @Override
    public Object[] saveAuditoriumsMap(NavigableMap<LocalDateTime, Auditorium> auditoriums) {
        Set<Long> result = new HashSet<Long>();
        for (Map.Entry<LocalDateTime, Auditorium> entry : auditoriums.entrySet()) {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbc.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO auditoriumsmaps (auditoriumid,airdate) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, entry.getValue().getId());
                    statement.setTimestamp(2, Timestamp.valueOf(entry.getKey()));
                    return statement;
                }
            }, holder);
            long id = (Long) holder.getKeys().get("auditoriumsmapid");
            result.add(id);
        }
        return result.toArray();
    }

    @Override
    public void removeAuditoriumMap(List<Long> auditoriumsmapids) {
        for (Long id : auditoriumsmapids){
            jdbc.update("DELETE * FROM auditoriumsmaps WHERE auditoriumsmapid=?",new Object[]{id});
        }

    }

    public NavigableMap<LocalDateTime, Auditorium> getAuditoriumsMap(Long[] mapIDs) {
        TreeMap<LocalDateTime, Auditorium> result = new TreeMap<>();
        for (Long mapID : mapIDs) {
            List<Map<LocalDateTime, Auditorium>> entrys = jdbc.query("SELECT * from auditoriumsmaps where auditoriumsmapid = ?", new Object[]{mapID},
                    new RowMapper<Map<LocalDateTime, Auditorium>>() {
                        @Override
                        public Map<LocalDateTime, Auditorium> mapRow(ResultSet rs, int i) throws SQLException {
                            HashMap<LocalDateTime, Auditorium> entry = new HashMap<>();
                            LocalDateTime airdate = rs.getTimestamp("airdate").toLocalDateTime();
                            Auditorium auditorium = getByID(rs.getLong("auditoriumid"));
                            entry.put(airdate, auditorium);
                            return entry;
                        }
                    });
            for (Map<LocalDateTime, Auditorium> auditor : entrys) {
                result.putAll(auditor);
            }
        }
        return result;
    }
}
