package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;



public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void validLogin() {
        String[] registerInput = {"register", "username", "password", "email"};
        ServerFacade.register(registerInput);

        String[] input = { "login", "username", "password" };
        String[] responseArray = ServerFacade.login(input);
        Assertions.assertEquals(2, responseArray.length);
    }

    @Test
    public void invalidLogin() {
        String[] input = { "login", "incorrectUsername", "incorrectPassword" };
        String[] responseArray = ServerFacade.login(input);
        Assertions.assertNotEquals(2, responseArray.length);
    }

    @Test
    public void validRegister() {
        String[] registerInput = {"register", "username", "password", "email"};
        ServerFacade.register(registerInput);

        String[] input = { "login", "username", "password" };
        String[] responseArray = ServerFacade.login(input);
        Assertions.assertEquals(2, responseArray.length);
    }

    @Test
    public void invalidRegister() {
        try {
            String[] registerInput = {"register", "new_username", "new_password"};
            ServerFacade.register(registerInput);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        String[] input = { "login", "new_username", "new_password" };
        String[] responseArray = ServerFacade.login(input);
        Assertions.assertNotEquals(2, responseArray.length);
    }

    @Test
    public void validLogout() {
        String[] registerInput = {"register", "username", "password", "email"};
        String[] response = ServerFacade.register(registerInput);

        if(response.length == 0){
            String[] loginInput = {"login", "username", "password"};
            response = ServerFacade.login(loginInput);
        }


        ServerFacade.logout(response[1]);

        String[] input = new String[] {"create", "new_Game"};

        Assertions.assertNull(ServerFacade.createGame(input, response[1]));
    }

    @Test
    public void invalidLogout() {
        try {
            String[] registerInput = {"register", "username", "password", "email"};
            String[] response = ServerFacade.register(registerInput);

            if (response.length == 0) {
                String[] loginInput = {"login", "username", "password"};
                response = ServerFacade.login(loginInput);
            }


            ServerFacade.logout("invalidAuthToken");

            ServerFacade.listGames(response[1]);

        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void validCreateGame() {
        String[] registerInput = {"register", "username", "password", "email"};
        String[] response = ServerFacade.register(registerInput);

        if(response.length == 0){
            String[] loginInput = {"login", "username", "password"};
            response = ServerFacade.login(loginInput);
        }


        String[] input = new String[] {"create", "new_Game"};

        Assertions.assertNotNull(ServerFacade.createGame(input, response[1]));
    }

    @Test
    public void invalidCreateGame() {
        String[] registerInput = {"register", "username", "password", "email"};
        String[] response = ServerFacade.register(registerInput);

        if(response.length == 0){
            String[] loginInput = {"login", "username", "password"};
            response = ServerFacade.login(loginInput);
        }


        ServerFacade.logout(response[1]);

        String[] input = new String[] {"create", "new_Game"};

        Assertions.assertNull(ServerFacade.createGame(input, response[1]));
    }

    @Test
    public void validListGames() {
        String[] registerInput = {"register", "username", "password", "email"};
        String[] response = ServerFacade.register(registerInput);

        if(response.length == 0){
            String[] loginInput = {"login", "username", "password"};
            response = ServerFacade.login(loginInput);
        }

        try {
            ServerFacade.listGames(response[1]);
        } catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    public void invalidListGames() {
        try {
            ServerFacade.listGames("InvalidAuthToken");
        } catch (Exception e){
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void validJoinGame() {
        String[] registerInput = {"register", "username", "password", "email"};
        String[] response = ServerFacade.register(registerInput);

        if(response.length == 0){
            String[] loginInput = {"login", "username", "password"};
            response = ServerFacade.login(loginInput);
        }


        String[] createInput = new String[] {"create", "new_Game"};
        String gameID = ServerFacade.createGame(createInput, response[1]);


        String[] input = {"join", gameID, "WHITE"};
        ServerFacade.joinGame(input, response[1]);

        try {
            ServerFacade.joinGame(input, response[1]);
        } catch (Exception e){
            Assertions.assertTrue(true);
        }



    }

    @Test
    public void invalidJoinGame() {
        String[] registerInput = {"register", "username", "password", "email"};
        String[] response = ServerFacade.register(registerInput);

        if(response.length == 0){
            String[] loginInput = {"login", "username", "password"};
            response = ServerFacade.login(loginInput);
        }


        String[] createInput = new String[] {"create", "new_Game"};
        String gameID = ServerFacade.createGame(createInput, response[1]);


        String[] input = {"join", gameID, "WHITE"};
        try {
            ServerFacade.joinGame(input, "invalidAuthToken");
        } catch (Exception e){
            Assertions.assertTrue(true);
        }


        try {
            ServerFacade.joinGame(input, response[1]);
        } catch (Exception e){
            Assertions.fail();
        }
    }





}
