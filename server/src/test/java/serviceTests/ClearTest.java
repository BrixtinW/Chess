package serviceTests;

import chess.ChessGame;
import dataAccess.Exceptions.CustomException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;
import service.Clear;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ClearTest {

    @BeforeAll
    static void beforeAll() {
        DB.authDataMap = new HashMap<>();
        DB.gameDataMap = new HashMap<>();
        DB.userDataMap = new HashMap<>();
    }

    @Test
    public void validClearCases() throws TestException {
        try {

            assertTrue(DB.gameDataMap.isEmpty());
            assertTrue(DB.userDataMap.isEmpty());
            assertTrue(DB.authDataMap.isEmpty());

            Clear.clear();
            Clear.clear();

            assertTrue(DB.gameDataMap.isEmpty());
            assertTrue(DB.userDataMap.isEmpty());
            assertTrue(DB.authDataMap.isEmpty());


            DB.userDataMap.put("BILLY", new UserData("Billy", "Bob", "Joe"));
            DB.authDataMap.put("BILLY", new AuthData("a;lsdkjf;alksdjf;lajsdf", "Billy"));
            DB.gameDataMap.put(1000, new GameData(1000, "WHITE", "BLACK", "FUNGAME", new ChessGame()));

            assertFalse(DB.gameDataMap.isEmpty());
            assertFalse(DB.userDataMap.isEmpty());
            assertFalse(DB.authDataMap.isEmpty());

            Clear.clear();

            assertTrue(DB.gameDataMap.isEmpty());
            assertTrue(DB.userDataMap.isEmpty());
            assertTrue(DB.authDataMap.isEmpty());

        } catch (CustomException e) {
            System.out.println("ERROR!!! CLEAR DIDN'T WORK CORRECTLY");
            fail();
        }
    }

    @Test
    public void invalidClearCases() throws TestException {

        DB.authDataMap = null;
        DB.userDataMap = null;
        DB.gameDataMap = null;

        try {
            Clear.clear();
        } catch (CustomException e) {
            assertEquals(500, e.statusCode);
        }
    }

}