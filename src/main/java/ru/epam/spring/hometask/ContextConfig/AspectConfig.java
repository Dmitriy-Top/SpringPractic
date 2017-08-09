package ru.epam.spring.hometask.ContextConfig;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.epam.spring.hometask.DAO.DiscountDAO;
import ru.epam.spring.hometask.aspects.CounterAspect;
import ru.epam.spring.hometask.aspects.DiscountAspect;

/**
 * Created by Dmitrii_Topolnik on 8/8/2017.
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
    @Bean
    public CounterAspect getEventStatisticsAspect(){
        return new CounterAspect();
    }

    @Bean
    public DiscountAspect getDiscountAspect(){
        return new DiscountAspect();
    }
}
