package serviceTests;

import dataAccess.CustomException;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    public void validLoginCases() throws TestException {

        DB.userDataMap.put("username", new model.userData("username", "password", "email"));

        try{
            service.Login.login("username", "password");
        } catch (CustomException e){
            fail();
        }

    }

    @Test
    public void invalidLoginCases() throws TestException {
        DB.userDataMap.put("username", new model.userData("username", "password", "email"));

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