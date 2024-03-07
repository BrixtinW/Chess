package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.InvalidRequest;
import dataAccess.Exceptions.MemoryAlreadyAllocated;
import dataAccess.MemoryDataAccess.MemoryUserDao;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Register extends ServiceProgenitor {

    public static AuthData register(String username, String password, String email) throws DataAccessException, MemoryAlreadyAllocated, InvalidRequest {

        if(username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()){
            throw new InvalidRequest("Error: bad request");
        }

        UserData userObj = getUser(username);
        if (userObj != null){
            throw new MemoryAlreadyAllocated("Error: already taken");
        }

        createUser(username, password, email);

        return createAuth(username);
    }

    private static void createUser(String username, String password, String email) throws DataAccessException{
        SQLUserDao userDao = new SQLUserDao();
        UserData user = new UserData(username, passwordHash(password), email);
        userDao.createUser(user);
    }

}
