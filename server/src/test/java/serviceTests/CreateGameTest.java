package serviceTests;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLAuthDao;
import dataAccess.SQLDataAccess.SQLGameDao;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;
import service.Clear;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameTest {

    @Test
    public void validCreateGameTest() throws TestException {
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
    public void invalidCreateGameTest() throws TestException {
        try {
            SQLGameDao gameDao = new SQLGameDao();

            try {
                gameDao.createGame(new GameData(0, "WHITE", "BLACK", null, null));
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }


        } catch (CustomException e){
            fail();
        }

    }

}