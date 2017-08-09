package ru.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.epam.spring.hometask.domain.Event;
import ru.epam.spring.hometask.domain.User;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dmitrii_Topolnik on 8/9/2017.
 */
@Aspect
public class DiscountAspect {
    Map<User, Integer> userDiscounts = new HashMap<>();

    @Pointcut("execution(* ru.epam.spring.hometask.DAO.DiscountDAO.getDiscount(..))")
    private void countUserDiscounts() {
    }

    @AfterReturning(pointcut = "countUserDiscounts()", returning = "discount")
    public void countAfterUserDiscount(JoinPoint jp, byte discount) {
        Object[] args = jp.getArgs();
        User user = (User) args[0];
        if (!userDiscounts.containsKey(user)) {
            userDiscounts.put(user, 0);
        }
        if (discount > 0){
            userDiscounts.put(user, userDiscounts.get(user) + 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("How many times each discount was given total and for specific user: \n");
        Integer summDiscountCount = 0;
        for (Map.Entry<User, Integer> userDiscount : userDiscounts.entrySet()) {
            sb.append("..." + userDiscount.getKey().getFirstName() + " " + userDiscount.getKey().getLastName() + " - " + userDiscount.getValue() + " given discount\n");
            summDiscountCount += userDiscount.getValue();
        }
        sb.append("=====================================================================\n");
        sb.append("Total: " + summDiscountCount);
        sb.append("\n");

        return sb.toString();
    }
}
