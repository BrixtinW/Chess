package dataAccess;

import server.DB;

import java.util.HashMap;

public class MemoryAuthDao implements AuthDAO {
    @Override
    public model.authData getAuth(String authToken) throws DataAccessException {
        try {
            return DB.authDataMap.get(authToken);
        }
        catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void createAuth(model.authData authObj) throws DataAccessException {
        try {
            DB.authDataMap.put(authObj.authToken(), authObj);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void deleteAuth() throws DataAccessException {

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
