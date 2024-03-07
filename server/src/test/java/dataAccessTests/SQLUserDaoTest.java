package dataAccessTests;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLUserDao;
import model.UserData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.Clear;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDaoTest {

    @Test
    public void validGetUserTest() throws TestException {
        try {
            Clear.clear();

            SQLUserDao userDao = new SQLUserDao();

            userDao.createUser(new UserData("username", "password", "email"));

            UserData userData = userDao.getUser("username");

            assertNotEquals(null, userData);

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidGetUserTest() throws TestException {
        try {
            Clear.clear();

            SQLUserDao userDao = new SQLUserDao();

            userDao.createUser(new UserData("username", "password", "email"));

            try {
                UserData userData = userDao.getUser("invalidUsername");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void validCreateUserTest() throws TestException {
        try {
            Clear.clear();

            SQLUserDao userDao = new SQLUserDao();

            userDao.createUser(new UserData("username", "password", "email"));

            UserData userData = userDao.getUser("username");

            assertNotEquals(null, userData);

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidCreateUserTest() throws TestException {
        try {
            Clear.clear();

            SQLUserDao userDao = new SQLUserDao();

            userDao.createUser(new UserData("username", "password", "email"));

            try {
                userDao.createUser(new UserData(null, "password", "email"));
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
            SQLUserDao userDao = new SQLUserDao();


            userDao.createUser(new UserData("username", "password", "email"));

            Clear.clear();

            try {
                userDao.getUser("authToken");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }


        } catch (CustomException e) {
            fail();
        }

    }

}