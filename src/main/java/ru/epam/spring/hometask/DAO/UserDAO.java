package ru.epam.spring.hometask.DAO;

import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitrii_Topolnik on 7/14/2017.
 */
public class UserDAO implements UserService {
    private static Map <String,User>userMap;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return null;
    }

    @Override
    public User save(@Nonnull User object) {
        return null;
    }

    @Override
    public void remove(@Nonnull User object) {

    }

    @Override
    public User getById(@Nonnull Long id) {
        return null;
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return null;
    }

    private void init(){
        userMap = new HashMap();

    }
}
