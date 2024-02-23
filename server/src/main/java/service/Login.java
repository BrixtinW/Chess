package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDao;
import dataAccess.MemoryUserDao;
import dataAccess.UnauthorizedRequest;

import java.util.Objects;

import static service.ServiceProgenitor.createAuth;

public class Login {

    public static model.authData login(String username, String password) throws DataAccessException, UnauthorizedRequest {
        MemoryUserDao userDao = new MemoryUserDao();

        model.userData userdata = userDao.getUser(username);
        if (userdata == null || !Objects.equals(userdata.password(), password)){
            throw new UnauthorizedRequest("Error: unauthorized");
        }

        return createAuth(username);
    }

}
