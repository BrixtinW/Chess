package dataAccess.MemoryDataAccess;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.interfaces.GameDAO;
import model.GameData;
import server.DB;

import java.util.*;

public class MemoryGameDao implements GameDAO {
    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try {
            return DB.gameDataMap.get(gameID);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void createGame(GameData game) throws DataAccessException {
        try {
            DB.gameDataMap.put(game.gameID(), game);
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        try {
            return new ArrayList<>(DB.gameDataMap.values());
        } catch (Exception e) {
            throw new DataAccessException("Error: Data Access Exception");
        }
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {
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
