package dataAccess.MemoryDataAccess;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.interfaces.AuthDAO;
import model.AuthData;
import server.DB;

import java.util.HashMap;

public class MemoryAuthDao implements AuthDAO {
    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try {
            return DB.authDataMap.get(authToken);
        }
        catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void createAuth(AuthData authObj) throws DataAccessException {
        try {
            DB.authDataMap.put(authObj.authToken(), authObj);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try {
            DB.authDataMap.remove(authToken);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try {
            DB.authDataMap = new HashMap<>();
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }
}
