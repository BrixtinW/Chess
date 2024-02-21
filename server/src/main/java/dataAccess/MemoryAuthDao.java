package dataAccess;

import server.DB;

import java.util.HashMap;

public class MemoryAuthDao implements AuthDAO {
    @Override
    public void getAuth() throws DataAccessException {

    }

    @Override
    public void createAuth() throws DataAccessException {

    }

    @Override
    public void deleteAuth() throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {
        DB.authDataMap = new HashMap<>();
    }
}
