package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.InvalidRequest;
import dataAccess.MemoryGameDao;
import dataAccess.UnauthorizedRequest;
import model.gameData;

import java.util.Iterator;

public class CreateGame extends ServiceProgenitor {

    public static gameData createGame(String authToken, String gameName) throws DataAccessException, UnauthorizedRequest, InvalidRequest {
        if (gameName == null) {
            throw new InvalidRequest("Error: bad request");
        }
//        This returns an auth token that I just want to make sure doesn't throw an exception. I don't actually use it at all
        getAuth(authToken);

            MemoryGameDao gameDao = new MemoryGameDao();
            gameData game = new gameData(generateGameID.next(), "", "", gameName, new ChessGame());
            gameDao.createGame(game);
            return game;
    }

    public class generateGameID {
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
