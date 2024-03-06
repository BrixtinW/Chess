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
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM authDB WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new AuthData(rs.getString("authToken"), rs.getString("username"));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("ERROR: Unable to read data");
        }
        return null;
    }

    @Override
    public void createAuth(AuthData authObj) throws DataAccessException {
        var statement = "INSERT INTO authDB (authToken, username) VALUES (?, ?)";
        executeUpdate(statement, authObj.authToken(), authObj.username());
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM authDB WHERE authToken=?";
        executeUpdate(statement, authToken);
    }

    @Override
    public void clear() throws  DataAccessException {
        var statement = "TRUNCATE authDB";
        super.executeUpdate(statement);
    }

}
