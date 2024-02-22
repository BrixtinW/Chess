package handler;

import com.google.gson.Gson;
import dataAccess.CustomException;
import dataAccess.DataAccessException;
import dataAccess.InvalidRequest;
import dataAccess.UnauthorizedRequest;
import model.gameData;
import service.Clear;
import service.CreateGame;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public static Object createGame(Request request, Response response) {
        var serializer = new Gson();
        var game = serializer.fromJson(request.body(), gameData.class);
        String authToken = request.headers("Authorization");
        String responseBody = "";


        try {
            gameData createdGame = CreateGame.createGame(authToken, game.gameName());
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
