package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.service.UserService;

/**
 * Created by Dmitrii_Topolnik on 7/25/2017.
 */
public class UserServiceWraper {
    private UserService us;

    public UserServiceWraper(UserService us) {
        this.us = us;
    }

    public String test(){
        return "ok";
    }

    public Long regUser(String email, String firstName, String lastName) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        user = us.save(user);
        return user.getId();
    }

    public void delUser(long id) {

        User user = new User();
        user.setId(id);

        us.remove(user);
    }
}
