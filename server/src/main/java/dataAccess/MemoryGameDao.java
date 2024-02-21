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
    public void createGame() throws DataAccessException {

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
        DB.gameDataMap = new HashMap<>();
    }
}
