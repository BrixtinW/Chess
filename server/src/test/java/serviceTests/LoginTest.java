package serviceTests;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLAuthDao;
import dataAccess.SQLDataAccess.SQLGameDao;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.Clear;
import service.Login;
import service.Register;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    public void validLoginTest() throws TestException {
        try {
            Clear.clear();

            SQLUserDao userDao = new SQLUserDao();

            Register.register("username", "password", "email");

            Login.login("username", "password");

            UserData userData = userDao.getUser("username");

            assertNotNull(userData);

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidLoginTest() throws TestException {

        try {


            Clear.clear();

            try {
                Login.login("username", "password");
            } catch (CustomException e) {
                assertEquals(401, e.statusCode);
            }

            try {
                Register.register(null, "password", "email");
            } catch (CustomException e) {
                assertEquals(400, e.statusCode);
            }

            try {
                Login.login("username", "password");
            } catch (CustomException e) {
                assertEquals(401, e.statusCode);
            }

        } catch (CustomException e) {
            fail();
        }

    }

}