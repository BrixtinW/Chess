package serviceTests;

import dataAccess.Exceptions.CustomException;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @BeforeAll
    static void beforeAll() {
        DB.authDataMap = new HashMap<>();
        DB.gameDataMap = new HashMap<>();
        DB.userDataMap = new HashMap<>();
    }

    @Test
    public void validLoginCases() throws TestException {

        DB.userDataMap.put("username", new UserData("username", "password", "email"));

        try{
            service.Login.login("username", "password");
        } catch (CustomException e){
            fail();
        }

    }

    @Test
    public void invalidLoginCases() throws TestException {
        DB.userDataMap.put("username", new UserData("username", "password", "email"));

        try{
            service.Login.login("username", "invalid password");
        } catch (CustomException e){
            assertEquals(401, e.statusCode);
        }

        DB.userDataMap = null;

        try{
            service.Login.login("username", "password");
        } catch (CustomException e){
            assertEquals(500, e.statusCode);
        }
    }

}