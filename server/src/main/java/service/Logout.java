package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryAuthDao;
import dataAccess.Exceptions.UnauthorizedRequest;

public class Logout extends ServiceProgenitor {

    public static void logout(String authToken) throws DataAccessException, UnauthorizedRequest {
        getAuth(authToken);

        MemoryAuthDao authDao = new MemoryAuthDao();
        authDao.deleteAuth(authToken);
    }
}
