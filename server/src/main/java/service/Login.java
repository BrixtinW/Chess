package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryUserDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

import static service.ServiceProgenitor.createAuth;
import static service.ServiceProgenitor.passwordHash;

public class Login {

    public static AuthData login(String username, String password) throws DataAccessException, UnauthorizedRequest {
        SQLUserDao userDao = new SQLUserDao();

        UserData userdata = userDao.getUser(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, userdata.password())){
            throw new UnauthorizedRequest("Error: unauthorized");
        }

        return createAuth(username);
    }

}
