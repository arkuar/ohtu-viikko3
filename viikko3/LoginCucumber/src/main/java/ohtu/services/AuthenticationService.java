package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        Pattern userPat = Pattern.compile("^([A-Za-z0-9]){3,}$");
        Matcher userMat = userPat.matcher(username);
        Pattern passPat = Pattern.compile("^(?=.*[A-Za-z])(?=.*([0-9]|[@#$%^&+=]))(?=\\S+$).{8,}$");
        Matcher passMat = passPat.matcher(password);
        System.out.println(userMat);
        if (userMat.matches() && passMat.matches()) {
            return false;
        }
        return true;
    }
}
