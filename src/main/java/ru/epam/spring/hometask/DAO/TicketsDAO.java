package ru.epam.spring.hometask.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.epam.spring.hometask.domain.*;
import ru.epam.spring.hometask.service.BookingService;
import ru.epam.spring.hometask.service.DiscountService;
import ru.epam.spring.hometask.service.EventService;
import ru.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dmitrii_Topolnik on 8/7/2017.
 */
public class TicketsDAO implements BookingService {
    @Autowired
    private DiscountService ds;
    @Autowired
    private EventService eventDAO;
    @Autowired
    private UserService userDAO;
    @Autowired
    private JdbcOperations jdbc;

    @Override
    public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
        double summPrice = 0;
        Long numberOfTickets = (long) seats.size();
        double userDiscount = ds.getDiscount(user, event, dateTime, numberOfTickets);
        EventRating rating = event.getRating();
        Auditorium auditorium = event.getAuditoriums().get(dateTime);

        for (Long seat : seats) {
            double seatPrice = event.getBasePrice() * rating.getCoefficient() * vipSetCoeff(seat, auditorium);
            seatPrice -= (userDiscount / 100) * seatPrice;
            summPrice += seatPrice;
        }

        return summPrice;
    }

    private double vipSetCoeff(Long seat, Auditorium auditorium) {

        return (auditorium.getVipSeats().contains(seat)) ? 2.0 : 1;
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == null) {
                jdbc.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement statement = connection.prepareStatement("INSERT INTO tickets (eventid,seat,userid,airdate) VALUE (?,?,?,?,?)");
                        statement.setLong(1, ticket.getEvent().getId());
                        statement.setLong(2, ticket.getSeat());
                        statement.setLong(3, ticket.getUser().getId());
                        statement.setTimestamp(4, Timestamp.valueOf(ticket.getDateTime()));
                        return statement;
                    }
                });
            } else {
                jdbc.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement statement = connection.prepareStatement("UPDATE tickets SET eventid = ?,seat = ?,userid = ?,airdate = ? WHERE ticketid=?");
                        statement.setLong(1, ticket.getEvent().getId());
                        statement.setLong(2, ticket.getSeat());
                        statement.setLong(3, ticket.getUser().getId());
                        statement.setTimestamp(4, Timestamp.valueOf(ticket.getDateTime()));
                        statement.setLong(5, ticket.getId());
                        return statement;
                    }
                });
            }
        }
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        List<Ticket> tickets = jdbc.query("SELECT * from tickets WHERE eventid=? AND airdate=?", new Object[]{event.getId(),Timestamp.valueOf(dateTime)},new RowMapper<Ticket>(){
                    @Override
                    public Ticket mapRow(ResultSet rs, int i) throws SQLException {
                        User user = userDAO.getById(rs.getLong("userid"));
                        Long seat = rs.getLong("seat");
                        Ticket ticket = new Ticket(user,event,dateTime,seat);
                        ticket.setId(rs.getLong("ticketid"));
                        return ticket;
                    }
                });
        return new HashSet<>(tickets);
    }

    @Override
    public NavigableSet<Ticket> getTicketForUser(User user) {
            List<Ticket> tickets = jdbc.query("SELECT * FROM tickets WHERE userid=?", new Object[]{user.getId()},new RowMapper<Ticket>(){
                @Override
                public Ticket mapRow(ResultSet rs, int i) throws SQLException {
                    Long seat = rs.getLong("seat");
                    Event event = eventDAO.getById(rs.getLong("eventid"));
                    LocalDateTime dateTime = rs.getTimestamp("airdate").toLocalDateTime();
                    Ticket ticket = new Ticket(user,event,dateTime,seat);
                    ticket.setId(rs.getLong("ticketid"));
                    return ticket;
                }
            });
            return new TreeSet<>(tickets);
    }

    @Override
    public void saveUserTickets(User user) {
        bookTickets(user.getTickets());
    }

}
