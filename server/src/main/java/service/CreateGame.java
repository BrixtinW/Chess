package service;

import chess.ChessGame;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.InvalidRequest;
import dataAccess.MemoryDataAccess.MemoryGameDao;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.GameData;

public class CreateGame extends ServiceProgenitor {

    public static GameData createGame(String authToken, String gameName) throws DataAccessException, UnauthorizedRequest, InvalidRequest {
        if (gameName == null) {
            throw new InvalidRequest("Error: bad request");
        }
//        This returns an auth token that I just want to make sure doesn't throw an exception. I don't actually use it at all
        getAuth(authToken);

            SQLGameDao gameDao = new SQLGameDao();
            GameData game = new GameData(GenerateGameID.next(), null, null, gameName, new ChessGame());
            gameDao.createGame(game);
            return game;
    }

    public static class GenerateGameID {
        private static int current = 1000;
        private static final int end = 9999;

        public static boolean hasNext() {
            return current <= end;
        }

        public static int next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more valid Game IDs.");
            }
            return current++;
        }
    }
}
