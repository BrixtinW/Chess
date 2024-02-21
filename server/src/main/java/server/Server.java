package server;

import dataAccess.DataAccessException;
import handler.*;
import model.*;
import spark.*;

import java.util.Map;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

//        Spark.webSocket("/connect", webSocketHandler);


//        Spark.put("/game", JoinGameHandler::joinGame);
//        Spark.post("/game", CreateGameHandler::createGame);
//        Spark.get("/game", ListGamesHandler::listGames);
//        Spark.delete("/session", LogoutHandler::logout);
//        Spark.post("/session", LoginHandler::login);
//        Spark.post("/user", RegisterHandler::register);
        Spark.delete("/db", ClearHandler::clearApplication);

//        YOU ARE NOT HANDLING YOUR EXCEPTIONS!!! GET YOUR ACT TOGETHER!!!
//        Spark.exception(DataAccessException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
