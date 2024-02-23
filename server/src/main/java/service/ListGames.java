package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryGameDao;
import dataAccess.UnauthorizedRequest;
import model.gameData;
import server.Server;

import java.util.Collection;

public class ListGames extends ServiceProgenitor {

    public static Collection<gameData> listGames(String authToken) throws DataAccessException, UnauthorizedRequest {
        getAuth(authToken);

        MemoryGameDao gameDao = new MemoryGameDao();
        return gameDao.listGames();
    }

}
