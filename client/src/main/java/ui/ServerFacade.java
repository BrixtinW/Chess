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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


public class ServerFacade {

    public static String SERVER_URL = null;
    public static String DEFAULT_URL = "http://localhost:";

    public static String[] login(String[] parsedInput) {
//        System.out.println(parsedInput[0] + " " + parsedInput[1] + " " +  parsedInput[2]);

        String jsonPayload = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", parsedInput[1], parsedInput[2]);

        JsonObject jsonObject = connect("POST", "/session", jsonPayload, null);

        if (jsonObject == null) { return new String[0]; }

        String name = jsonObject.get("username").getAsString();
        String authToken = jsonObject.get("authToken").getAsString();
        return new String[] { name, authToken };

    }


    public static String[] register(String[] parsedInput){
//        System.out.println(parsedInput[0] + " " + parsedInput[1] + " " +  parsedInput[2] + " " +  parsedInput[3]);

        String jsonPayload = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}", parsedInput[1], parsedInput[2],  parsedInput[3]);

            JsonObject jsonObject = connect("POST", "/user", jsonPayload, null);

            if (jsonObject == null) { return new String[0]; }

            String name = jsonObject.get("username").getAsString();
            String authToken = jsonObject.get("authToken").getAsString();
            return new String[] { name, authToken };

    }

    public static void logout(String authToken) {

        JsonObject jsonObject = connect("DELETE", "/session", null, authToken);

        if (jsonObject == null) { return; }

        System.out.println("User Logged Out Successfully!");

        return;

        }

    public static String createGame(String[] parsedInput, String authToken) {
//        System.out.println(parsedInput[0] + " " + parsedInput[1]);


        String jsonPayload = String.format("{\"gameName\": \"%s\"}", parsedInput[1]);

        JsonObject jsonObject = connect("POST", "/game", jsonPayload, authToken);

        if (jsonObject == null) { return null; }

        String id = jsonObject.get("gameID").getAsString();

        return id;

    }

    public static void listGames(String authToken) {

        JsonObject jsonObject = connect("GET", "/game", null, authToken);

        JsonArray jsonArray = jsonObject.getAsJsonArray("games");

        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                JsonObject gameObject = element.getAsJsonObject();
                int gameId = gameObject.get("gameID").getAsInt();
                String gameName = gameObject.get("gameName").getAsString();
                String whitePlayer = !gameObject.get("whiteUsername").isJsonNull()
                        ? gameObject.get("whiteUsername").getAsString()
                        : "________";

                String blackPlayer = !gameObject.get("blackUsername").isJsonNull()
                        ? gameObject.get("blackUsername").getAsString()
                        : "________";


                System.out.println("\tGame ID: " + gameId);
                System.out.println("\tGame Name: " + gameName);
                System.out.println("\tWhite Player: " + whitePlayer);
                System.out.println("\tBlack Player: " + blackPlayer);
                System.out.println();
            }
        }

        return;

    }

    public static String joinGame(String[] parsedInput, String authToken){

//        System.out.println(parsedInput[0] + " " + parsedInput[1] + " " +  parsedInput[2]);

        String jsonPayload = null;

        if (parsedInput.length == 2) {
            jsonPayload = String.format("{\"gameID\": \"%s\", \"playerColor\": null}", parsedInput[1]);
        } else if (parsedInput.length == 3){
            jsonPayload = String.format("{\"gameID\": \"%s\", \"playerColor\": \"%s\"}", parsedInput[1], parsedInput[2].toUpperCase());
        }

        JsonObject jsonObject = connect("PUT", "/game", jsonPayload, authToken);

        if (jsonObject == null){ return null; }

        return jsonObject.get("message").getAsString();

    }


    private static JsonObject connect(String endpointType, String api, String jsonPayload, String authToken) {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(SERVER_URL + api);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(endpointType);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", authToken);


            if (jsonPayload != null) {
                try (OutputStream outputStream = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }

            int responseCode = connection.getResponseCode();

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
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }


}

