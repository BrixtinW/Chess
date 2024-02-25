package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryAuthDao;
import dataAccess.MemoryDataAccess.MemoryGameDao;
import dataAccess.MemoryDataAccess.MemoryUserDao;

public class Clear {
    public static void clear() throws DataAccessException {
        MemoryAuthDao authDao = new MemoryAuthDao();
        MemoryGameDao gameDao = new MemoryGameDao();
        MemoryUserDao userDao = new MemoryUserDao();

        authDao.clear();
        gameDao.clear();
        userDao.clear();
    }

}
