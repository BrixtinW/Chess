package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryAuthDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.SQLDataAccess.SQLAuthDao;

public class Logout extends ServiceProgenitor {

    public static void logout(String authToken) throws DataAccessException, UnauthorizedRequest {
        getAuth(authToken);

        SQLAuthDao authDao = new SQLAuthDao();
        authDao.deleteAuth(authToken);
    }
}
