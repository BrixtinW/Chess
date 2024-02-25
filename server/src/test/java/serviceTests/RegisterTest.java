package serviceTests;

import dataAccess.Exceptions.CustomException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import server.DB;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {

    @BeforeAll
    static void beforeAll() {
        DB.authDataMap = new HashMap<>();
        DB.gameDataMap = new HashMap<>();
        DB.userDataMap = new HashMap<>();
    }

    @Test
    public void validRegisterCases() throws TestException {

        DB.userDataMap = new HashMap<>();

        assertTrue(DB.userDataMap.isEmpty());

        try{
            service.Register.register("username", "password", "email");
        } catch (CustomException e){
            fail();
        }

        assertFalse(DB.userDataMap.isEmpty());

    }

    @Test
    public void invalidRegisterCases() throws TestException {

        assertTrue(DB.userDataMap.isEmpty());

        try{
            service.Register.register(null, "password", "email");
        } catch (CustomException e){
            assertEquals(400, e.statusCode);
        }

        try{
            service.Register.register("username", "password", "email");
        } catch (CustomException e){
            fail();
        }

        assertFalse(DB.userDataMap.isEmpty());

        try{
            service.Register.register("username", "password", "email");
        } catch (CustomException e){
            assertEquals(403, e.statusCode);
        }


        DB.userDataMap = null;

        try{
            service.Register.register("username", "password", "email");
        } catch (CustomException e){
            assertEquals(500, e.statusCode);
        }

    }

}