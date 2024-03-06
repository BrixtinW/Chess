package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.InvalidRequest;
import dataAccess.Exceptions.MemoryAlreadyAllocated;
import dataAccess.Exceptions.UnauthorizedRequest;
import dataAccess.MemoryDataAccess.MemoryGameDao;
import dataAccess.SQLDataAccess.SQLGameDao;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.GameData;

import java.util.Objects;

public class JoinGame extends ServiceProgenitor {

    public static void joinGame(String playerColor, int gameID, String authToken) throws DataAccessException, UnauthorizedRequest, InvalidRequest, MemoryAlreadyAllocated {
        if (((!Objects.equals(playerColor, "WHITE")) && (!Objects.equals(playerColor, "BLACK")) && (!Objects.equals(playerColor, null))) || (gameID == 0) ) {
            throw new InvalidRequest("ERROR: bad request");
        }
        AuthData authData = getAuth(authToken);
        GameData gameData = accessGame(gameID);
        updateGame(gameData, authData, playerColor);
    }

    private static GameData accessGame(int gameID) throws DataAccessException {
        SQLGameDao gameDao = new SQLGameDao();
        return gameDao.getGame(gameID);
    }

    private static void updateGame(GameData gameData, AuthData authData, String playerColor) throws DataAccessException, MemoryAlreadyAllocated {
        SQLGameDao gameDao = new SQLGameDao();
        GameData newGame;

        if (Objects.equals(playerColor, "WHITE") && Objects.equals(gameData.whiteUsername(), null)) {
            newGame = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        } else if (Objects.equals(playerColor, "BLACK") && Objects.equals(gameData.blackUsername(), null)) {
            newGame = new GameData(gameData.gameID(), gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
        } else if (Objects.equals(playerColor, null)) {
            newGame = gameData;
        } else {
            throw new MemoryAlreadyAllocated("Error: already taken");
        }

        gameDao.updateGame(newGame);

    }
}
