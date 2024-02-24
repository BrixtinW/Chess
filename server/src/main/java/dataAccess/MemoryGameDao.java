package dataAccess;

import chess.ChessGame;
import model.gameData;
import server.DB;

import java.util.*;

public class MemoryGameDao implements GameDAO {
    @Override
    public model.gameData getGame(int gameID) throws DataAccessException {
        try {
            return DB.gameDataMap.get(gameID);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void createGame(model.gameData game) throws DataAccessException {
        try {
            DB.gameDataMap.put(game.gameID(), game);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public Collection<gameData> listGames() throws DataAccessException {
        try {
            return new ArrayList<>(DB.gameDataMap.values());
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void updateGame(model.gameData gameData) throws DataAccessException {
        try {
            DB.gameDataMap.put(gameData.gameID(), gameData);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
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
