package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDao;
import dataAccess.UnauthorizedRequest;

import javax.xml.crypto.Data;

public class Logout extends ServiceProgenitor {

    public static void logout(String authToken) throws DataAccessException, UnauthorizedRequest {
        getAuth(authToken);

        MemoryAuthDao authDao = new MemoryAuthDao();
        authDao.deleteAuth(authToken);
    }
}
