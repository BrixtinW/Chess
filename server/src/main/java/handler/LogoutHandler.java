package handler;

import dataAccess.CustomException;
import model.gameData;
import service.ListGames;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class LogoutHandler {
    public static Object logout(Request request, Response response) {
        String authToken = request.headers("Authorization");
        String responseBody = "";


        try {
            service.Logout.logout(authToken);
            response.status(200);
            responseBody = "{\"message\": \"User Logout Successful\"}";
        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            response.type("application/json");
            return responseBody;
        }
    }
}
