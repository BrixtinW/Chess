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

        Spark.delete("/db", ClearHandler::clearApplication); // DONE!
        Spark.post("/game", CreateGameHandler::createGame); // DONE!
        Spark.put("/game", JoinGameHandler::joinGame);
        Spark.get("/game", ListGamesHandler::listGames);
        Spark.post("/session", LoginHandler::login);
        Spark.delete("/session", LogoutHandler::logout);
        Spark.post("/user", RegisterHandler::register);

//        YOU ARE NOT HANDLING YOUR EXCEPTIONS!!! GET YOUR ACT TOGETHER!!!
        Spark.exception(DataAccessException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public void exceptionHandler(Exception ex, Request request, Response response) {
        response.status(500);
        response.body("Internal Server Error: " + ex.getMessage());
    }

}
