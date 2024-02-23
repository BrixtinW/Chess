package service;

import dataAccess.*;
import model.authData;
import model.userData;

import java.util.UUID;

public class Register extends ServiceProgenitor {

    public static authData register(String username, String password, String email) throws DataAccessException, MemoryAlreadyAllocated, InvalidRequest {

        if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
            throw new InvalidRequest("Error: bad request");
        }

        userData userObj = getUser(username);
        if (userObj != null){
            throw new MemoryAlreadyAllocated("Error: already taken");
        }

        createUser(username, password, email);

        return createAuth(username);
    }

    private static void createUser(String username, String password, String email) throws DataAccessException{
        MemoryUserDao userDao = new MemoryUserDao();
        userData user = new userData(username, password, email);
        userDao.createUser(user);
    }

}
