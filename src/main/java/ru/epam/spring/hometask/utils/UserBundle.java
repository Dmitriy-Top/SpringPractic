package ru.epam.spring.hometask.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.epam.spring.hometask.domain.User;

/**
 * Created by Dmitrii_Topolnik on 8/4/2017.
 */
public class UserBundle {
    private User currnetUser;

    public User getCurrnetUser() {
        return currnetUser;
    }

    public void setCurrnetUser(User currnetUser) throws Exception {
        if (this.currnetUser==null) this.currnetUser = currnetUser;
        else throw new Exception("User already registered");
    }


}
