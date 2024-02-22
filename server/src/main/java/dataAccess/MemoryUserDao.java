package dataAccess;

import server.DB;

import java.util.HashMap;

public class MemoryUserDao implements UserDAO {
    @Override
    public model.userData getUser(String username) throws DataAccessException {
        try {
            return DB.userDataMap.get(username);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void createUser(model.userData user) throws DataAccessException {
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
