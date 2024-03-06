package dataAccess.SQLDataAccess;

import chess.ChessGame;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.interfaces.UserDAO;
import model.AuthData;
import model.UserData;
import dataAccess.DatabaseManager;


public class SQLUserDao extends SQLProgenitor implements UserDAO {

    private static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS userDB (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLUserDao() throws DataAccessException {
        super(createStatements);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "SELECT username, password, email FROM userDB WHERE username=?";
                try (var ps = conn.prepareStatement(statement)) {
                    ps.setString(1, username);
                    try (var rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                        }
                    }
                }
            } catch (Exception e) {
                throw new DataAccessException("ERROR: Unable to read data");
            }
            return null;
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        var statement = "INSERT INTO userDB (username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, user.username(), user.password(), user.email());
    }

    @Override
    public void clear() throws  DataAccessException {
        var statement = "TRUNCATE userDB";
        super.executeUpdate(statement);
    }


}
