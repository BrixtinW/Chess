package serviceTests;

import chess.ChessGame;
import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;
import service.Clear;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JoinGameTest {

    @Test
    public void validJoinGameCases() throws TestException {
        try {
            Clear.clear();
            SQLGameDao gameDao = new SQLGameDao();
            gameDao.createGame(new GameData(0, null, null, "gameName", null));


            try{
                service.JoinGame.joinGame("BLACK", 1000, "TOKEN");
            } catch (CustomException e){
                assertEquals(401, e.statusCode);
            }
        } catch (CustomException e){
            fail();
        }

    }

    @Test
    public void invalidJoinGameCases() throws TestException {
        DB.authDataMap.put("TOKEN", new AuthData("TOKEN", "Username"));
        DB.gameDataMap.put(1000, new GameData(1000, "WHITE", null, "FUNGAME", new ChessGame()));


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
            assertEquals(401, e.statusCode);
        }

        DB.gameDataMap = null;


        try{
            service.JoinGame.joinGame("BLACK", 1000, "TOKEN");
        } catch (CustomException e){
            assertEquals(401, e.statusCode);
        }

    }

}