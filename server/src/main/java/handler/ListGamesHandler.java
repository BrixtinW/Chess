package handler;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.CustomException;
import model.gameData;
import service.ListGames;
import service.Register;
import spark.Request;
import spark.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListGamesHandler {
    public static Object listGames(Request request, Response response) {
        String authToken = request.headers("Authorization");
        StringBuilder responseBody = new StringBuilder();


        try {
            Collection<gameData> games = ListGames.listGames(authToken);
            response.status(200);

            responseBody = new StringBuilder("{ \"games\": [");
            boolean firstObject = true;

            for (model.gameData game : games) {
                if (!firstObject) {
                    responseBody.append(",");
                } else {
                    firstObject = false;
                }

                responseBody.append("{\"gameID\": ").append(game.gameID()).append(", \"whiteUsername\": ");

                if (game.whiteUsername() != null) {
                    responseBody.append("\"").append(game.whiteUsername()).append("\"");
                } else {
                    responseBody.append("null");
                }

                responseBody.append(", \"blackUsername\": ");

                if (game.blackUsername() != null) {
                    responseBody.append("\"").append(game.blackUsername()).append("\"");
                } else {
                    responseBody.append("null");
                }

                responseBody.append(", \"gameName\": \"").append(game.gameName()).append("\"}");
            }
            responseBody.append("]}");

        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = new StringBuilder("{\"message\": \"" + e.getMessage() + "\"}");
        } finally {
            response.type("application/json");
            return responseBody.toString();
        }
    }
}
