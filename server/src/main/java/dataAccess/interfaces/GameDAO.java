package dataAccess.interfaces;

import dataAccess.Exceptions.DataAccessException;
import model.GameData;

import java.util.Collection;

public interface GameDAO {

    GameData getGame(int gameID) throws DataAccessException;

    int createGame(GameData game) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(GameData gameData) throws DataAccessException;

    void clear() throws  DataAccessException;

}
