package DataAccessTest;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.GameData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    public void validLoginTest() throws TestException {

        try {

            SQLGameDao gameDao = new SQLGameDao();

            int gameID = gameDao.createGame(new GameData(0, "WHITE", "BLACK", "GAMENAME", null));

            GameData gameData = gameDao.getGame(gameID);

            assertNotEquals(gameData, null);


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidLoginTest() throws TestException {

    }

}