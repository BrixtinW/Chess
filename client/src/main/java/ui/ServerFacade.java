package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ServerFacade {

    private static final String SERVER_URL = "http://localhost:8080";

    public static String[] login(String[] parsedInput) {
        System.out.println(parsedInput[0] + " " + parsedInput[1] + " " +  parsedInput[2]);

        // Create JSON payload for the request
        String jsonPayload = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", parsedInput[1], parsedInput[2]);

        JsonObject jsonObject = connect("POST", "/session", jsonPayload, null);

        if (jsonObject == null) { return new String[0]; }

        String name = jsonObject.get("username").getAsString();
        String authToken = jsonObject.get("authToken").getAsString();
        return new String[] { name, authToken };

    }


    public static String[] register(String[] parsedInput){
        System.out.println(parsedInput[0] + " " + parsedInput[1] + " " +  parsedInput[2] + " " +  parsedInput[3]);

        // Create JSON payload for the request
        String jsonPayload = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}", parsedInput[1], parsedInput[2],  parsedInput[3]);

            JsonObject jsonObject = connect("POST", "/user", jsonPayload, null);

            if (jsonObject == null) { return new String[0]; }

            String name = jsonObject.get("username").getAsString();
            String authToken = jsonObject.get("authToken").getAsString();
            return new String[] { name, authToken };

    }

    public static void logout(String authToken) {

        System.out.println(authToken);

        JsonObject jsonObject = connect("DELETE", "/session", null, authToken);

        System.out.println(jsonObject.toString());

        return;

        }

    public static void listGames(String authToken) {

        System.out.println(authToken);

        JsonObject jsonObject = connect("GET", "/game", null, authToken);

        System.out.println(jsonObject.toString());

        return;

    }


    private static JsonObject connect(String endpointType, String api, String jsonPayload, String authToken) {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(SERVER_URL + api);

            // Open a connection to the server
            connection = (HttpURLConnection) url.openConnection();

            // Set request method to POST
            connection.setRequestMethod(endpointType);
//            if (endpointType != "GET") {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
//            }
                connection.setRequestProperty("Authorization", authToken);


            // Write JSON payload to the connection's output stream
            try (OutputStream outputStream = connection.getOutputStream()) {
                if (jsonPayload != null) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }

            // Get the response code from the server
            int responseCode = connection.getResponseCode();

            // Read the response from the server
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return JsonParser.parseString(response.toString()).getAsJsonObject();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Close the connection in the finally block to ensure it's always closed
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }
//
//    }
//
//    public static void createGame(String[] parsedInput){
//
//    }
//
//    public static String listGames(String[] parsedInput) throws IOException {

}

