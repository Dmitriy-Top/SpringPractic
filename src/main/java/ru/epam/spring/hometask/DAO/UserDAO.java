package ru.epam.spring.hometask.DAO;

import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Dmitrii_Topolnik on 7/14/2017.
 */
public class UserDAO implements UserService {
    private static List<User> DB;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        User result = null;
        for (User user : DB) {
            if (user.getEmail().equals(email)) return result;
        }
        return null;
    }

    @Override
    public User save(@Nonnull User object) {
        if (object.getId() == null) {
            object.setId(User.counter++);
        }
        DB.add(object);
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        User target = null;
        for (User user : DB){
            if (user.getId() == object.getId()) {
                target = user;
                break;
            }
        }
        if (target != null)DB.remove(target);
    }

    @Override
    public User getById(@Nonnull Long id) {
        for (User user : DB){
            if (user.getId() == id) return user;
        }
        return null;
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return DB;
    }

    private void init() {
        DB = new ArrayList<User>();


    }

    public String test(){
        return "ok";
    }
}
