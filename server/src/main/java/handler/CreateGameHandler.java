package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.CustomException;
import model.GameData;
import service.CreateGame;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public static Object createGame(Request request, Response response) {
        var serializer = new Gson();
        var game = serializer.fromJson(request.body(), GameData.class);
        String authToken = request.headers("Authorization");
        String responseBody = "";


        try {
            GameData createdGame = CreateGame.createGame(authToken, game.gameName());
            response.status(200);
            responseBody = "{\"gameID\": \"" + createdGame.gameID() + "\"}";
        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            response.type("application/json");
            return responseBody;
        }
    }
}
