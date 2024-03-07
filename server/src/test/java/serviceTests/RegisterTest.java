package serviceTests;

import com.mysql.cj.log.Log;
import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.Clear;
import service.Login;
import service.Logout;
import service.Register;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {

    @Test
    public void validRegisterTest() throws TestException {
        try {
            Clear.clear();

            Register.register("username", "password", "email");

            Login.login("username", "password");

            SQLUserDao userDao = new SQLUserDao();
            UserData userData = userDao.getUser("username");
            assertNotNull(userData);

        } catch(Exception e){
            fail();
        }

    }

    @Test
    public void invalidRegisterTest() throws TestException {

        try {
            Clear.clear();

            try {
                Register.register(null, "password", "email");
            } catch (CustomException e) {
                assertEquals(400, e.statusCode);
            }

        } catch(Exception e){
            fail();
        }

    }

}