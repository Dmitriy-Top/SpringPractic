package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.domain.User;
import ru.epam.spring.hometask.domain.UserRole;
import ru.epam.spring.hometask.service.UserService;
import ru.epam.spring.hometask.utils.UserBundle;

import java.time.LocalDate;

/**
 * Created by Dmitrii_Topolnik on 7/25/2017.
 */
public class UserServiceWraper {
    private UserService us;
    private UserBundle userBundle;

    public UserServiceWraper(UserService us, UserBundle userBundle) {
        this.us = us;
        this.userBundle = userBundle;
    }

    public String regUser(String email, String firstName, String lastName, String role, String birthDate) {
        User user = new User();
        try {
            user.setRole(UserRole.valueOf(role));
        } catch (IllegalArgumentException exp) {
            return "User role not found";
        }
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDay(LocalDate.parse(birthDate));

        user = us.save(user);
        return email + " is registered, id is " + user.getId();
    }

    public void delUser(long id) {

        User user = new User();
        user.setId(id);

        us.remove(user);
    }

    public String getUserById(long id) {
        User user = us.getById(id);
        return (user != null) ? "user where id is " + id + " is " + user.toString() : "user not found";
    }

    public String getUserByEmail(String email) {
        User user = us.getUserByEmail(email);
        return (user != null) ? user.toString() : "user not found";
    }

    public String getAll() {
        StringBuffer sb = new StringBuffer();
        for (User user : us.getAll()) {
            sb.append(user.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public Boolean isAuth() {
        if (userBundle.getCurrnetUser() == null) return false;
        return true;
    }

    public String authUser(String email) {
        User user = us.getUserByEmail(email);
        if (user != null) {
            try {
                userBundle.setCurrnetUser(user);
            } catch (Exception e) {
                return "User already auth";
            }
            return user.getFirstName() + " " + user.getLastName() + " is auth";
        }
        return "User not found";
    }

    public boolean isAdmin() {
        User user = userBundle.getCurrnetUser();
        if (user == null) return false;
        if (user.getRole() == UserRole.USER) return false;
        return true;

    }
}
