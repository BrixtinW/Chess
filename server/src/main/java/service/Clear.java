package service;

import dataAccess.*;

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
