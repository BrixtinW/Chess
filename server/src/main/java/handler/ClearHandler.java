package handler;

import com.google.gson.Gson;
import dataAccess.CustomException;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;
import service.Clear;

public class ClearHandler {

    public static Object clearApplication(Request request, Response response) {
        String responseBody = "";
        try {
            Clear.clear();
            response.status(200);
            responseBody = "{\"message\": \"Application data cleared successfully\"}";
        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            response.type("application/json");
            return responseBody;
        }
    }
}
