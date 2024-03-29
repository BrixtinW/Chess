package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryAuthDao;
import dataAccess.MemoryDataAccess.MemoryUserDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.SQLDataAccess.SQLAuthDao;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class ServiceProgenitor {

    static AuthData getAuth(String authToken) throws DataAccessException, UnauthorizedRequest {
        SQLAuthDao authDao = new SQLAuthDao();
        AuthData authObj = authDao.getAuth(authToken);

        if (authObj != null){
            return authObj;
        } else {
            throw new UnauthorizedRequest("Error: unauthorized");
        }

    }

    static UserData getUser(String username) throws DataAccessException {
        SQLUserDao userDao = new SQLUserDao();
        return userDao.getUser(username);
    }


    static AuthData createAuth(String username) throws DataAccessException{
        SQLAuthDao authDao = new SQLAuthDao();
        AuthData authObj = new AuthData(UUID.randomUUID().toString(), username);
        authDao.createAuth(authObj);

        return authObj;
    }

    static String passwordHash(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
