package ru.epam.spring.hometask.ContextConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.epam.spring.hometask.DAO.*;
import ru.epam.spring.hometask.DAO.DiscountStrategys.AbstractStrategy;
import ru.epam.spring.hometask.DAO.DiscountStrategys.BirthDayStrategy;
import ru.epam.spring.hometask.DAO.DiscountStrategys.Every10thTicketStrategy;
import ru.epam.spring.hometask.domain.Auditorium;
import ru.epam.spring.hometask.service.DiscountService;
import ru.epam.spring.hometask.service.UserService;
import ru.epam.spring.hometask.utils.UserBundle;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitrii_Topolnik on 8/8/2017.
 */
@Configuration
@PropertySource("classpath:auditorium.properties")
public class DAOConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private DiscountService discountDAO;

    @Bean
    public UserBundle getUserBundle() {
        return new UserBundle();
    }

    @Bean
    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    @Bean
    public EventDAO getEventDAO (){
        return new EventDAO();
    }

    @Bean
    public AuditoriumDAO getAuditoriumDAO(){
        Auditorium alpha = new Auditorium(environment.getProperty("auditorium.alpha"),environment.getProperty("auditorium.alpha.seats",Long.class), environment.getProperty("auditorium.alpha.vipseats",String[].class));
        Auditorium betta = new Auditorium(environment.getProperty("auditorium.betta"),environment.getProperty("auditorium.betta.seats",Long.class), environment.getProperty("auditorium.betta.vipseats",String[].class));
        Set<Auditorium> set = new HashSet<>();
        set.add(alpha);
        set.add(betta);
        AuditoriumDAO dao = new AuditoriumDAO();
        dao.setDB(set);
        return dao;
    }

    @Bean
    public DiscountDAO getDiscountDAO(){
        List<AbstractStrategy> list = new ArrayList<>();
        list.add(new BirthDayStrategy());
        list.add(new Every10thTicketStrategy());
        DiscountDAO dao = new DiscountDAO();
        dao.setStrategies(list);
        return dao;
    }

    @Bean
    public TicketsDAO getTicketsDAO(){
        return new TicketsDAO(discountDAO);
    }



}
