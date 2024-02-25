package serviceTests;

import dataAccess.Exceptions.CustomException;
import model.AuthData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LogoutTest {

    @BeforeAll
    static void beforeAll() {
        DB.authDataMap = new HashMap<>();
        DB.gameDataMap = new HashMap<>();
        DB.userDataMap = new HashMap<>();
    }

    @Test
    public void validLogoutCases() throws TestException {

        DB.authDataMap.put("TOKEN", new AuthData("TOKEN", "Username"));

        try{
            service.Logout.logout("TOKEN");
        } catch (CustomException e){
            fail();
        }

        assertTrue(DB.authDataMap.isEmpty());

    }

    @Test
    public void invalidLogoutCases() throws TestException {

        DB.authDataMap.put("TOKEN", new AuthData("TOKEN", "Username"));

        try{
            service.Logout.logout("invalid token");
        } catch (CustomException e){
            assertEquals(401, e.statusCode);
        }

        DB.authDataMap = null;

        try{
            service.Logout.logout("TOKEN");
        } catch (CustomException e){
            assertEquals(500, e.statusCode);
        }


    }

}