package ru.epam.spring.hometask.DAO;

import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.domain.UserRole;
import ru.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Dmitrii_Topolnik on 7/14/2017.
 */
public class UserDAO implements UserService {
    private List<User> DB;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        for (User user : DB) {
            if (user.getEmail().equals(email)){
                return user;
            }
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
        //test content
        User admin = new User();
        admin.setEmail("test@email.ru");
        admin.setFirstName("Dmitriy");
        admin.setLastName("Topolnyk");
        admin.setRole(UserRole.ADMIN);
        admin.setBirthDay(LocalDate.parse("1989-08-07"));
        DB.add(admin);


    }

    public String test(){
        return "ok";
    }
}
