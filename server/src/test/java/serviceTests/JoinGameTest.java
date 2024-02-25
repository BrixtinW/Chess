package serviceTests;

import chess.ChessGame;
import dataAccess.CustomException;
import model.authData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JoinGameTest {

    @BeforeAll
    static void beforeAll() {
        DB.authDataMap = new HashMap<>();
        DB.gameDataMap = new HashMap<>();
        DB.userDataMap = new HashMap<>();
    }

    @Test
    public void validJoinGameCases() throws TestException {
        DB.gameDataMap = new HashMap<>();
        DB.authDataMap.put("TOKEN", new authData("TOKEN", "Username"));
        DB.gameDataMap.put(1000, new model.gameData(1000, "WHITE", null, "FUNGAME", new ChessGame()));


        try {
            service.JoinGame.joinGame("BLACK", 1000, "TOKEN");
        } catch (CustomException e){
            fail();
        }

    }

    @Test
    public void invalidJoinGameCases() throws TestException {
        DB.authDataMap.put("TOKEN", new authData("TOKEN", "Username"));
        DB.gameDataMap.put(1000, new model.gameData(1000, "WHITE", null, "FUNGAME", new ChessGame()));


        try{
            service.JoinGame.joinGame("invalid color", 1000, "TOKEN");
        } catch (CustomException e){
            assertEquals(400, e.statusCode);
        }

        try{
            service.JoinGame.joinGame("BLACK", 1000, "invalidToken");
        } catch (CustomException e){
            assertEquals(401, e.statusCode);
        }

        try{
            service.JoinGame.joinGame("WHITE", 1000, "TOKEN");
        } catch (CustomException e){
            assertEquals(403, e.statusCode);
        }

        DB.gameDataMap = null;


        try{
            service.JoinGame.joinGame("BLACK", 1000, "TOKEN");
        } catch (CustomException e){
            assertEquals(500, e.statusCode);
        }

    }

}