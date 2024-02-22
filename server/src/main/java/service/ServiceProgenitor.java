package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDao;
import dataAccess.MemoryUserDao;
import dataAccess.UnauthorizedRequest;
import model.authData;

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
}
