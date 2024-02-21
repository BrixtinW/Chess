package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;
import service.Clear;

public class ClearHandler {

    public static Object clearApplication(Request request, Response response) throws DataAccessException {

//        CONVERT JSON TO REQUEST OBJECT USING GSON??????
//        from json
//        Gson gson = new Gson();
//        LoginRequest requestObject = gson.fromJson(request.body(), LoginRequest.class);


        var serializer = new Gson();


// deserialize back to ChessGame
//        game = serializer.fromJson(json, ChessGame.class);


//        LoginResponse result =
//        service.Clear.clear();
////        gson.toJson(result);
//        response.status(200); // MAYBE CHANGE THE STATUS CODE? I'M NOT SURE WHAT GOES HERE
//        System.out.println(response);
//
//        System.out.println(serializer.toJson(response));
//
//        response.type("application/json");
//        return serializer.toJson(response);
//        // return result body here!!!!!!




        // Call the clear method to clear the application data
        Clear.clear();

        // Set the status code to 200 (OK)
        response.status(200);

        // Create a response object containing a success message
        String responseBody = "{\"message\": \"Application data cleared successfully\"}";

        // Set the content type header to indicate JSON response
        response.type("application/json");

        // Return the JSON response body
        return responseBody;
    }
}
