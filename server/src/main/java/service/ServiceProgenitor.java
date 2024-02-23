package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDao;
import dataAccess.MemoryUserDao;
import dataAccess.UnauthorizedRequest;
import model.authData;

import java.util.UUID;

public class ServiceProgenitor {

    static model.authData getAuth(String authToken) throws DataAccessException, UnauthorizedRequest {
        MemoryAuthDao authDao = new MemoryAuthDao();
        authData authObj = authDao.getAuth(authToken);

        if (authObj != null){
            return authObj;
        } else {
            throw new UnauthorizedRequest("Error: unauthorized");
        }

    }

    static model.userData getUser(String username) throws DataAccessException {
        MemoryUserDao userDao = new MemoryUserDao();
        return userDao.getUser(username);
    }


    static model.authData createAuth(String username) throws DataAccessException{
        MemoryAuthDao authDao = new MemoryAuthDao();
        authData authObj = new authData(UUID.randomUUID().toString(), username);
        authDao.createAuth(authObj);

        return authObj;
    }
}
