package DataAccessTest;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLAuthDao;
import dataAccess.SQLDataAccess.SQLGameDao;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.Clear;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesTest {

    @Test
    public void validListGamesTest() throws TestException {
        try {
        SQLGameDao gameDao = new SQLGameDao();

        gameDao.createGame(new GameData(0, "WHITE", "BLACK", "GAMENAME1", null));
        gameDao.createGame(new GameData(0, "WHITE", "BLACK", "GAMENAME2", null));
        gameDao.createGame(new GameData(0, "WHITE", "BLACK", "GAMENAME3", null));


        Collection<GameData> gamesList = gameDao.listGames();

        assertFalse(gamesList.isEmpty());


    } catch (CustomException e) {
        fail();
        }

    }

    @Test
    public void invalidListGamesTest() throws TestException {

        try {
            Clear.clear();

        SQLGameDao gameDao = new SQLGameDao();

        Collection<GameData> gamesList = gameDao.listGames();

        assertTrue(gamesList.isEmpty());

        } catch (CustomException e) {
            fail();
        }
    }

}