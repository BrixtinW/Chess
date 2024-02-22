package dataAccess;

import chess.ChessGame;
import server.DB;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDao implements GameDAO {
    @Override
    public void getGame() throws DataAccessException {

    }

    @Override
    public void createGame(model.gameData game) throws DataAccessException {
        try {
            DB.gameDataMap.put(game.gameName(), game);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public Collection<ChessGame> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame() throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {
        try {
            DB.gameDataMap = new HashMap<>();
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }
}
