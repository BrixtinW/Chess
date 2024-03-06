package dataAccess.SQLDataAccess;

import chess.ChessGame;
import dataAccess.DatabaseManager;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.interfaces.AuthDAO;
import model.AuthData;

public class SQLAuthDao extends SQLProgenitor implements AuthDAO {

    private static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS authDB (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLAuthDao() throws DataAccessException {
        super(createStatements);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {return null;}

    @Override
    public void createAuth(AuthData authObj) throws DataAccessException {}

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {}

    @Override
    public void clear() throws  DataAccessException {
        var statement = "TRUNCATE authDB";
        super.executeUpdate(statement);
    }

}
