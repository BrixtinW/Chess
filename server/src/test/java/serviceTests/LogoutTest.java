package serviceTests;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLAuthDao;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.Clear;
import service.JoinGame;
import service.Logout;
import service.Register;

import static org.junit.jupiter.api.Assertions.*;

class LogoutTest {

    @Test
    public void validLogoutTest() throws TestException {

        try {
            Clear.clear();

            SQLAuthDao authDao = new SQLAuthDao();

            authDao.createAuth(new AuthData("authToken", "username"));

            Logout.logout("authToken");
            try {
                authDao.getAuth("authToken");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

        } catch(Exception e){
            fail();
        }

    }

    @Test
    public void invalidLogoutTest() throws TestException {

        try {
            Clear.clear();

            try {
                Logout.logout("authToken");
            } catch (CustomException e) {
                assertEquals(401, e.statusCode);
            }

        } catch(Exception e){
            fail();
        }

    }

}