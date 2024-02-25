package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.CustomException;
import model.AuthData;
import model.UserData;
import service.Login;
import spark.Request;
import spark.Response;

public class LoginHandler {
    public static Object login(Request request, Response response) {
//        something to note is that you can log in an infinite number of times and it will always
//        generate new auth objects that just sit in memory. you need a way to get rid of all the
//        old auth objects associated with the old username so there can only be one auth at a time.
        var serializer = new Gson();
        var user = serializer.fromJson(request.body(), UserData.class);
        String responseBody = "";


        try {
            AuthData authObj = Login.login(user.username(), user.password());
            response.status(200);
            responseBody = "{\"username\": \"" + authObj.username() + "\", \"authToken\": \"" + authObj.authToken() + "\"}";
        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            response.type("application/json");
            return responseBody;
        }
    }
}
