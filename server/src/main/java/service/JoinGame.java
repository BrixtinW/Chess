package service;

import model.authData;
import dataAccess.*;
import model.gameData;

import java.util.Objects;

public class JoinGame extends ServiceProgenitor {

    public static void joinGame(String playerColor, int gameID, String authToken) throws DataAccessException, UnauthorizedRequest, InvalidRequest, MemoryAlreadyAllocated {
        if (((!Objects.equals(playerColor, "WHITE")) && (!Objects.equals(playerColor, "BLACK")) && (!Objects.equals(playerColor, null))) || (gameID == 0) ) {
            throw new InvalidRequest("ERROR: bad request");
        }
        model.authData authData = getAuth(authToken);
        model.gameData gameData = accessGame(gameID);
        updateGame(gameData, authData, playerColor);
    }

    private static model.gameData accessGame(int gameID) throws DataAccessException {
        MemoryGameDao gameDao = new MemoryGameDao();
        return gameDao.getGame(gameID);
    }

    private static void updateGame(model.gameData gameData, model.authData authData, String playerColor) throws DataAccessException, MemoryAlreadyAllocated {
        MemoryGameDao gameDao = new MemoryGameDao();
        model.gameData newGame;

        if (Objects.equals(playerColor, "WHITE") && Objects.equals(gameData.whiteUsername(), null)) {
            newGame = new gameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        } else if (Objects.equals(playerColor, "BLACK") && Objects.equals(gameData.blackUsername(), null)) {
            newGame = new gameData(gameData.gameID(), gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
        } else if (Objects.equals(playerColor, null)) {
            newGame = gameData;
        } else {
            throw new MemoryAlreadyAllocated("Error: already taken");
        }

        gameDao.updateGame(newGame);

    }
}
