package serviceTests;

import chess.ChessGame;
import dataAccess.Exceptions.CustomException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesTest {

    @BeforeAll
    static void beforeAll() {
        DB.authDataMap = new HashMap<>();
        DB.gameDataMap = new HashMap<>();
        DB.userDataMap = new HashMap<>();
    }

    @Test
    public void validListGamesCases() throws TestException {
        DB.gameDataMap.put(1000, new GameData(1000, "WHITE", "BLACK", "FUN", new ChessGame()));
        DB.gameDataMap.put(1001, new GameData(1001, "WHITE", "BLACK", "Game", new ChessGame()));
        DB.gameDataMap.put(1002, new GameData(1002, "WHITE", "BLACK", "To", new ChessGame()));
        DB.gameDataMap.put(1003, new GameData(1003, "WHITE", "BLACK", "Play", new ChessGame()));

        DB.authDataMap.put("TOKEN", new AuthData("TOKEN", "Username"));

        try{
            service.ListGames.listGames("TOKEN");
        } catch (CustomException e){
            fail();
        }
    }

    @Test
    public void invalidListGamesCases() throws TestException {
        DB.gameDataMap.put(1000, new GameData(1000, "WHITE", "BLACK", "FUN", new ChessGame()));
        DB.gameDataMap.put(1001, new GameData(1001, "WHITE", "BLACK", "Game", new ChessGame()));
        DB.gameDataMap.put(1002, new GameData(1002, "WHITE", "BLACK", "To", new ChessGame()));
        DB.gameDataMap.put(1003, new GameData(1003, "WHITE", "BLACK", "Play", new ChessGame()));

        DB.authDataMap.put("TOKEN", new AuthData("TOKEN", "Username"));

        try{
            service.ListGames.listGames("invalid Token");
        } catch (CustomException e){
            assertEquals(401, e.statusCode);
        }

        DB.gameDataMap = null;


        try{
            service.ListGames.listGames("TOKEN");
        } catch (CustomException e){
            assertEquals(500, e.statusCode);
        }


    }

}