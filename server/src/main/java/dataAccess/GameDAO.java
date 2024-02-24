package dataAccess;

import chess.ChessGame;

import java.util.Collection;

public interface GameDAO {

    model.gameData getGame(int gameID) throws DataAccessException;

    void createGame(model.gameData game) throws DataAccessException;

    Collection<model.gameData> listGames() throws DataAccessException;

    void updateGame(model.gameData gameData) throws DataAccessException;

    void clear() throws  DataAccessException;

}
