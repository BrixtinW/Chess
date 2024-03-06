package dataAccess.SQLDataAccess;

import chess.ChessGame;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.interfaces.UserDAO;
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
    public UserData getUser(String username) throws DataAccessException {return null;}

    @Override
    public void createUser(UserData user) throws DataAccessException {}

    @Override
    public void clear() throws  DataAccessException {
        var statement = "TRUNCATE userDB";
        super.executeUpdate(statement);
    }


}
