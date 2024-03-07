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
import service.Register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ClearTest {

    @Test
    public void clearTest() throws TestException {
        try {
            SQLAuthDao authDao = new SQLAuthDao();
            SQLUserDao userDao = new SQLUserDao();
            SQLGameDao gameDao = new SQLGameDao();

            authDao.createAuth(new AuthData("authToken", "username"));
            userDao.createUser(new UserData("username", "password", "email"));
            int gameID = gameDao.createGame(new GameData(0, "WHITE", "BLACK", "GAMENAME", null));

            Clear.clear();

            try {
                authDao.getAuth("authToken");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

            try {
                userDao.getUser("username");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

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