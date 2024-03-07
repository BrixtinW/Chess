package dataAccess.SQLDataAccess;

import chess.ChessGame;
import dataAccess.DatabaseManager;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.interfaces.GameDAO;
import model.GameData;
import com.google.gson.Gson;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class SQLGameDao extends SQLProgenitor implements GameDAO {

    private static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS gameDB (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public SQLGameDao() throws DataAccessException {
        super(createStatements);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gameDB WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        var serializer = new Gson();
                        var game = serializer.fromJson(rs.getString("game"), ChessGame.class);
                        return new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), game);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("ERROR: Unable to read data");
        }
        return null;
    }

    @Override
    public int createGame(GameData game) throws DataAccessException {
        var statement = "INSERT INTO gameDB (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";
        Gson gson = new Gson();
        String gameJSON = gson.toJson(game.game());
        return executeUpdate(statement, game.whiteUsername(), game.blackUsername(), game.gameName(), gameJSON);
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        ArrayList<GameData> gamesList = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName FROM gameDB";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
//                        var serializer = new Gson();
//                        var game = serializer.fromJson(rs.getString("game"), ChessGame.class);
                        gamesList.add( new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), null));
                    }
                    return gamesList;
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("ERROR: Unable to read data");
        }
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {
        var statement = "UPDATE gameDB SET whiteUsername = ?, blackUsername = ?, game = ?, WHERE gameID = ?";
        Gson gson = new Gson();
        String gameJSON = gson.toJson(gameData.game());
        executeUpdate(statement, gameData.whiteUsername(), gameData.blackUsername(), gameJSON, gameData.gameID());
    }

    @Override
    public void clear() throws  DataAccessException {
        var statement = "TRUNCATE gameDB";
        super.executeUpdate(statement);
    }


}
