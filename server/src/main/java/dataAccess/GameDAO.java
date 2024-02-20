package dataAccess;

import chess.ChessGame;

import java.util.Collection;

public interface GameDAO {

    void getGame() throws DataAccessException;

    void createGame() throws DataAccessException;

    Collection<ChessGame> listGames() throws DataAccessException;

    void updateGame() throws DataAccessException;

    void clear() throws  DataAccessException;

}
