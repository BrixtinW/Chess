package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryAuthDao;
import dataAccess.MemoryDataAccess.MemoryUserDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class ServiceProgenitor {

    static AuthData getAuth(String authToken) throws DataAccessException, UnauthorizedRequest {
        MemoryAuthDao authDao = new MemoryAuthDao();
        AuthData authObj = authDao.getAuth(authToken);

        if (authObj != null){
            return authObj;
        } else {
            throw new UnauthorizedRequest("Error: unauthorized");
        }

    }

    static UserData getUser(String username) throws DataAccessException {
        MemoryUserDao userDao = new MemoryUserDao();
        return userDao.getUser(username);
    }


    static AuthData createAuth(String username) throws DataAccessException{
        MemoryAuthDao authDao = new MemoryAuthDao();
        AuthData authObj = new AuthData(UUID.randomUUID().toString(), username);
        authDao.createAuth(authObj);

        return authObj;
    }
}
