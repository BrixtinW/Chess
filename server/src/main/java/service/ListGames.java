package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryGameDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.GameData;

import java.util.Collection;

public class ListGames extends ServiceProgenitor {

    public static Collection<GameData> listGames(String authToken) throws DataAccessException, UnauthorizedRequest {
        getAuth(authToken);

        SQLGameDao gameDao = new SQLGameDao();
        return gameDao.listGames();
    }

}
