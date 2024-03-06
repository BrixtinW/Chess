package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryUserDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.UserData;

import java.util.Objects;

import static service.ServiceProgenitor.createAuth;

public class Login {

    public static AuthData login(String username, String password) throws DataAccessException, UnauthorizedRequest {
        SQLUserDao userDao = new SQLUserDao();

        UserData userdata = userDao.getUser(username);
        if (userdata == null || !Objects.equals(userdata.password(), password)){
            throw new UnauthorizedRequest("Error: unauthorized");
        }

        return createAuth(username);
    }

}
