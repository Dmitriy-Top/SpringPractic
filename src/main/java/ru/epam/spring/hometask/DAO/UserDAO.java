package ru.epam.spring.hometask.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.domain.UserRole;
import ru.epam.spring.hometask.service.BookingService;
import ru.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Dmitrii_Topolnik on 7/14/2017.
 */
public class UserDAO implements UserService {
    @Autowired
    private JdbcOperations jdbc;
    @Autowired
    private BookingService ticketDAO;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        User user = jdbc.queryForObject("select * from users where email = ?", new Object[]{email}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                user.setBirthDay(rs.getDate("birthday").toLocalDate());
                user.setTickets(ticketDAO.getTicketForUser(user));
                return user;
            }
        });
        return user;
    }

    @Override
    public User save(@Nonnull User object) {
        if (object.getId()==null){
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbc.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO users (firstname,lastname,role,email,birthday) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, object.getFirstName());
                    statement.setString(2, object.getLastName());
                    statement.setString(3, object.getRole().name());
                    statement.setString(4, object.getEmail());
                    statement.setDate(5, java.sql.Date.valueOf(object.getBirthDay()));
                    return statement;
                }
            }, holder);
            long id = (Long) holder.getKeys().get("userid");
            object.setId(id);
            ticketDAO.saveUserTickets(object);
        } else{
            jdbc.update("UPDATE users SET firstname = ?,lastname = ?,role = ?,email = ?,birthday = ? WHERE userid = ?",object.getFirstName(),object.getLastName(),object.getRole().name(),object.getEmail(),object.getBirthDay(),object.getId());
            ticketDAO.saveUserTickets(object);
        }

        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        jdbc.update("DELETE from users where userid = ?",object.getId());
    }

    @Override
    public User getById(@Nonnull Long id) {
        User user = jdbc.queryForObject("select * from users where userid = ?", new Object[]{id}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                user.setBirthDay(rs.getDate("birthday").toLocalDate());
                user.setTickets(ticketDAO.getTicketForUser(user));
                return user;
            }
        });
        return user;
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        List<User> users = jdbc.query("SELECT * from users",
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        User user = new User();
                        user.setId(rs.getLong("userid"));
                        user.setFirstName(rs.getString("firstname"));
                        user.setLastName(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(UserRole.valueOf(rs.getString("role")));
                        user.setBirthDay(rs.getDate("birthday").toLocalDate());
                        user.setTickets(ticketDAO.getTicketForUser(user));
                        return user;
                    }
                });
        return users;
    }

}
