package dataAccess.SQLDataAccess;

import chess.ChessGame;
import dataAccess.DatabaseManager;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.interfaces.GameDAO;
import model.GameData;

import java.util.Collection;

public class SQLGameDao extends SQLProgenitor implements GameDAO {

    private static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS gameDB (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLGameDao() throws DataAccessException {
        super(createStatements);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {return null;}

    @Override
    public void createGame(GameData game) throws DataAccessException {}

    @Override
    public Collection<GameData> listGames() throws DataAccessException {return null;}

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {}

    @Override
    public void clear() throws  DataAccessException {
        var statement = "TRUNCATE gameDB";
        super.executeUpdate(statement);
    }


}
