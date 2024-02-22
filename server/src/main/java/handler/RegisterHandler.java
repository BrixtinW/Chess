package handler;

import com.google.gson.Gson;
import dataAccess.CustomException;
import service.Register;
import spark.Request;
import spark.Response;

public class RegisterHandler {

    public static Object register(Request request, Response response) {
        var serializer = new Gson();
        var user = serializer.fromJson(request.body(), model.userData.class);
        String responseBody = "";


        try {
            model.authData authObj = Register.register(user.username(), user.password(), user.email());
            response.status(200);
            responseBody = "{\"username\": \"" + user.username() + "\", \"authToken\": \"" + authObj.authToken() + "\"}";
        } catch (CustomException e) {
            response.status(e.statusCode);
            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            response.type("application/json");
            return responseBody;
        }


    }
}
