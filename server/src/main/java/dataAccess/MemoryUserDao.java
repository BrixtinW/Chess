package dataAccess;

import server.DB;

import java.util.HashMap;

public class MemoryUserDao implements UserDAO {
    @Override
    public void getUser() throws DataAccessException {

    }

    @Override
    public void createUser() throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {
        DB.userDataMap = new HashMap<>();
    }
}
