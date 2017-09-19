package ru.epam.spring.hometask.ContextConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.epam.spring.hometask.DAO.*;
import ru.epam.spring.hometask.DAO.DiscountStrategys.AbstractStrategy;
import ru.epam.spring.hometask.DAO.DiscountStrategys.BirthDayStrategy;
import ru.epam.spring.hometask.DAO.DiscountStrategys.Every10thTicketStrategy;
import ru.epam.spring.hometask.domain.Auditorium;
import ru.epam.spring.hometask.service.*;
import ru.epam.spring.hometask.utils.UserBundle;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitrii_Topolnik on 8/8/2017.
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:jdbc.properties")
})
public class DAOConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        ds.setUrl(environment.getProperty("jdbc.url"));
        ds.setUsername(environment.getProperty("jdbc.username"));
        ds.setPassword(environment.getProperty("jdbc.password"));
        return ds;
    }
    @Bean
    public JdbcOperations jdbcTemplate(){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserBundle UserBundle() {
        return new UserBundle();
    }

    @Bean
    public UserDAO UserDAO() {
        return new UserDAO();
    }

    @Bean
    public TicketsDAO TicketsDAO(){
        return new TicketsDAO();
    }

    @Bean
    public EventDAO EventDAO (){
        return new EventDAO();
    }

    @Bean
    public AuditoriumDAO AuditoriumDAO(){
        return new AuditoriumDAO();
    }

    @Bean
    public DiscountDAO DiscountDAO(){
        List<AbstractStrategy> list = new ArrayList<>();
        list.add(new BirthDayStrategy());
        list.add(new Every10thTicketStrategy());
        DiscountDAO dao = new DiscountDAO();
        dao.setStrategies(list);
        return dao;
    }





}
