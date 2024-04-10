package service;

import chess.ChessGame;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.InvalidRequest;
import dataAccess.MemoryDataAccess.MemoryGameDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.GameData;

public class CreateGame extends ServiceProgenitor {

    public static int createGame(String authToken, String gameName) throws DataAccessException, UnauthorizedRequest, InvalidRequest {
        if (gameName == null) {
            throw new InvalidRequest("Error: bad request");
        }
//        This returns an auth token that I just want to make sure doesn't throw an exception. I don't actually use it at all
        getAuth(authToken);

            SQLGameDao gameDao = new SQLGameDao();
            GameData game = new GameData(0, null, null, gameName, new ChessGame());
            game.game().getBoard().resetBoard();
            return gameDao.createGame(game);
    }

}
