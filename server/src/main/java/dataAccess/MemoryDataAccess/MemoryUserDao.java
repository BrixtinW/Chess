package dataAccess.MemoryDataAccess;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.interfaces.UserDAO;
import model.UserData;
import server.DB;

import java.util.HashMap;

public class MemoryUserDao implements UserDAO {
    @Override
    public UserData getUser(String username) throws DataAccessException, UnauthorizedRequest {
        try {
            return DB.userDataMap.get(username);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        try {
            DB.userDataMap.put(user.username(), user);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void clear() throws DataAccessException {
    try {
        DB.userDataMap = new HashMap<>();
    } catch (Exception e) {
        throw new DataAccessException("Error: Data Access Exception");
    }
    }
}
