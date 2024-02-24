package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataAccess.CustomException;
import service.JoinGame;
import service.Login;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    public static Object joinGame(Request request, Response response) {
        JsonObject json = JsonParser.parseString(request.body()).getAsJsonObject();
        String playerColor = null;
        if (json.has("playerColor") && !json.get("playerColor").isJsonNull()) {
            playerColor = json.get("playerColor").getAsString();
        }

        int gameID = 0;
        if (json.has("gameID") && !json.get("gameID").isJsonNull()) {
            gameID = json.get("gameID").getAsInt();
        }

        String authToken = request.headers("Authorization");
        String responseBody = "";

        try {
            JoinGame.joinGame(playerColor, gameID, authToken);
            response.status(200);
            responseBody = "{\"message\": \"User Joined Game Successfully\"}";
        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            response.type("application/json");
            return responseBody;
        }
    }
}
