package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryAuthDao;
import dataAccess.MemoryDataAccess.MemoryGameDao;
import dataAccess.MemoryDataAccess.MemoryUserDao;
import dataAccess.SQLDataAccess.SQLAuthDao;
import dataAccess.SQLDataAccess.SQLGameDao;
import dataAccess.SQLDataAccess.SQLUserDao;

public class Clear {
    public static void clear() throws DataAccessException {
        SQLAuthDao authDao = new SQLAuthDao();
        SQLGameDao gameDao = new SQLGameDao();
        SQLUserDao userDao = new SQLUserDao();

        authDao.clear();
        gameDao.clear();
        userDao.clear();
    }

}
