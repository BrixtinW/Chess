package serviceTests;

import dataAccess.Exceptions.CustomException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.InvalidRequest;
import dataAccess.Exceptions.UnauthorizedRequest;
import model.AuthData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameTest {

    @BeforeAll
    static void beforeAll() {
        DB.authDataMap = new HashMap<>();
        DB.gameDataMap = new HashMap<>();
        DB.userDataMap = new HashMap<>();
    }

    @Test
    public void validCreateGameCases() throws TestException, InvalidRequest, DataAccessException, UnauthorizedRequest {

        DB.gameDataMap = new HashMap<>();

        try {
            assertTrue(DB.gameDataMap.isEmpty());
            DB.authDataMap.put("TOKEN", new AuthData("TOKEN", "Username"));

            service.CreateGame.createGame("TOKEN", "FUNGAME");

            assertFalse(DB.gameDataMap.isEmpty());
        } catch (CustomException e){
            fail();
        }

    }

    @Test
    public void invalidCreateGameCases() throws TestException {
        DB.authDataMap.put("TOKEN", new AuthData("TOKEN", "Username"));


        try{
            service.CreateGame.createGame("TOKEN", null);
        } catch (CustomException e){
            assertEquals(400, e.statusCode);
        }

        try {
            service.CreateGame.createGame("INVALIDTOKEN", "GAMEFUN");
        } catch (CustomException e){
            assertEquals(401, e.statusCode);
        }


        DB.gameDataMap = null;
        try{
            service.CreateGame.createGame("TOKEN", "FUNGAME");
        } catch (CustomException e) {
            assertEquals(500, e.statusCode);
        }

    }

}