package handler;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.CustomException;
import model.gameData;
import service.ListGames;
import service.Register;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListGamesHandler {
    public static Object listGames(Request request, Response response) {
        String authToken = request.headers("Authorization");
        String responseBody = "";


        try {
            Collection<gameData> games = ListGames.listGames(authToken);
            response.status(200);





            responseBody = "{\"games\": \"" + games + "\"}";
        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            response.type("application/json");
            return responseBody;
        }
    }
}
