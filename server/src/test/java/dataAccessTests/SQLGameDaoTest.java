package dataAccessTests;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.GameData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.Clear;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SQLGameDaoTest {

    @Test
    public void validGetGameTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            int gameID = gameDao.createGame(new GameData(0, "WHITE", "BLACK", "GAME_NAME", null));


            GameData gamedata = gameDao.getGame(gameID);
            if (gamedata == null){
                fail();
            }


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidGetGameTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            try {
                gameDao.getGame(1);
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void validCreateGameTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            int gameID = gameDao.createGame(new GameData(0, "WHITE", "BLACK", "GAME_NAME", null));


            GameData gamedata = gameDao.getGame(gameID);
            if (gamedata == null){
                fail();
            }


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidCreateGameTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            try {
                gameDao.createGame(new GameData(0, "WHITE", "BLACK", null, null));
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void validListTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            gameDao.createGame(new GameData(0, "WHITE", "BLACK", "game1", null));
            gameDao.createGame(new GameData(0, "WHITE", "BLACK", "game2", null));
            gameDao.createGame(new GameData(0, "WHITE", "BLACK", "game3", null));


            Collection<GameData> gamesList = gameDao.listGames();
            assertFalse(gamesList.isEmpty());


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidListTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            Collection<GameData> gamesList = gameDao.listGames();
            assertTrue(gamesList.isEmpty());


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void validUpdateTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            int gameID = gameDao.createGame(new GameData(0, null, null, "game1", null));

            gameDao.updateGame(new GameData(gameID, "WHITE", "black", null, null));

            GameData gameData = gameDao.getGame(gameID);

            assertNotNull(gameData);
            assertEquals("WHITE", gameData.whiteUsername());
            assertEquals("black", gameData.blackUsername());


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidUpdateTest() throws TestException {
        try {
            Clear.clear();

            SQLGameDao gameDao = new SQLGameDao();

            int gameID = gameDao.createGame(new GameData(0, null, null, "game1", null));

            try {
                gameDao.updateGame(new GameData(2, "WHITE", "black", null, null));
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void clearTest() throws TestException {
        try {
            Clear.clear();
            SQLGameDao gameDao = new SQLGameDao();

            int gameID = gameDao.createGame(new GameData(0, null, null, "game1", null));

            Clear.clear();

            try {
                gameDao.getGame(gameID);
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }


        } catch (CustomException e) {
            fail();
        }

    }

}