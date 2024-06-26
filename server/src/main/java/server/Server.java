package server;

import handler.*;
import spark.*;
import webSocket.WebSocketHandler;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/connect", WebSocketHandler.class);

        Spark.delete("/db", ClearHandler::clearApplication); // DONE!
        Spark.post("/game", CreateGameHandler::createGame); // DONE!
        Spark.put("/game", JoinGameHandler::joinGame); // DONE!
        Spark.get("/game", ListGamesHandler::listGames); // DONE!
        Spark.post("/session", LoginHandler::login); // DONE!
        Spark.delete("/session", LogoutHandler::logout); // DONE!
        Spark.post("/user", RegisterHandler::register); // DONE!


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
