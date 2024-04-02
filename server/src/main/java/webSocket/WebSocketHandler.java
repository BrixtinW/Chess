package webSocket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.glassfish.tyrus.core.wsadl.model.Endpoint;

import javax.websocket.*;
import java.util.Map;
import java.util.Objects;

public class WebSocketHandler extends Endpoint {

    private WebSocketSessions webSocketSessions;
    private Gson gson;

    public WebSocketHandler(WebSocketSessions webSocketSessions) {
        this.webSocketSessions = new WebSocketSessions();
        this.gson = new Gson();
    }

    @OnOpen
    public void onOpen(Session session, Integer gameID, String authToken) {
        System.out.println("Connected to WebSocket server");
        webSocketSessions.addSessionToGame(gameID, authToken, session);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message received from server: " + message);
        Gson gson = new Gson();
        JsonObject msg = gson.fromJson(message, JsonObject.class);



//        ALL OF THE BELOW IS JUST A PLACE HOLDER UNTIL YOU FIND OUT HOW TO DO ENUM MESSAGE SHENANIGANS!
        // Get a JsonElement for a specific key
        String type = msg.get("type").getAsString();

        // Check the type of the JsonElement
        if (type != null) {
            if (Objects.equals(type, "a")) {
                System.out.println("Type of 'name' is primitive");
            } else if (Objects.equals(type, "b")) {
                System.out.println("Type of 'name' is object");
            } else if (Objects.equals(type, "c")) {
                System.out.println("Type of 'name' is array");
            } else if (Objects.equals(type, "d")) {
                System.out.println("Type of 'name' is null");
            }
        } else {
            System.out.println("'name' not found in JSON object");
        }


    }

    @OnClose
    public void onClose(Integer gameID, String authToken) {
        System.out.println("Connection closed");
        webSocketSessions.removeSessionFromGame(gameID, authToken);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on WebSocket: " + throwable.getMessage());
    }

    private void sendMessage(Integer gameID, String authToken, String message) {
        Session session = webSocketSessions.getSessionsForGame(gameID).get(authToken);
        if (session != null) {
            String jsonMessage = gson.toJson(message);
            try {
                session.getBasicRemote().sendText(jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Session not found for gameID: " + gameID + " and authToken: " + authToken);
        }
    }

    private void broadcastMessage(Integer gameID, String exceptThisAuthToken, String message) {
        for (Map.Entry<String, Session> entry : webSocketSessions.getSessionsForGame(gameID).entrySet()) {
            if (!entry.getKey().equals(exceptThisAuthToken)) {
                String jsonMessage = gson.toJson(message);
                try {
                    entry.getValue().getBasicRemote().sendText(jsonMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
