package dataAccessTests;

import dataAccess.Exceptions.CustomException;
import dataAccess.SQLDataAccess.SQLAuthDao;
import model.AuthData;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.Clear;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDaoTest {

    @Test
    public void validGetAuthTest() throws TestException {
        try {
        Clear.clear();

        SQLAuthDao authDao = new SQLAuthDao();

        authDao.createAuth(new AuthData("authToken", "username"));

        AuthData authData = authDao.getAuth("authToken");

        assertNotEquals(null, authData);

    } catch (CustomException e) {
        fail();
    }
    }

    @Test
    public void invalidGetAuthTest() throws TestException {
        try {
            Clear.clear();

            SQLAuthDao authDao = new SQLAuthDao();

            authDao.createAuth(new AuthData("authToken", "username"));

            try {
                AuthData authData = authDao.getAuth("invalidToken");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void validCreateAuthTest() throws TestException {
        try {
            Clear.clear();

            SQLAuthDao authDao = new SQLAuthDao();

            authDao.createAuth(new AuthData("authToken", "username"));

            AuthData authData = authDao.getAuth("authToken");

            assertNotEquals(null, authData);

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidCreateAuthTest() throws TestException {
        try {
            Clear.clear();

            SQLAuthDao authDao = new SQLAuthDao();

            authDao.createAuth(new AuthData("authToken", "username"));

            try {
                authDao.createAuth(new AuthData("invalidAuthToken", "username"));
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }

        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void validDeleteTest() throws TestException {
        try {
            Clear.clear();
            SQLAuthDao authDao = new SQLAuthDao();

            authDao.createAuth(new AuthData("authToken", "username"));

            authDao.deleteAuth("authToken");


            try {
                AuthData authData = authDao.getAuth("authToken");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }


        } catch (CustomException e) {
            fail();
        }

    }

    @Test
    public void invalidDeleteTest() throws TestException {
        try {

            SQLAuthDao authDao = new SQLAuthDao();

            Clear.clear();

            try {
                authDao.deleteAuth("authToken");
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
            SQLAuthDao authDao = new SQLAuthDao();


            authDao.createAuth(new AuthData("authToken", "username"));

            Clear.clear();

            try {
                authDao.getAuth("authToken");
            } catch (CustomException e) {
                assertEquals(500, e.statusCode);
            }


        } catch (CustomException e) {
            fail();
        }

    }





}