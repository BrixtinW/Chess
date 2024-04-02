package webSocket;

//import org.glassfish.grizzly.http.server.Session;
import javax.websocket.Session;

import java.util.Map;
import java.util.HashMap;

public class WebSocketSessions {

    private final Map<Integer, Map<String, Session>> sessionMap = new HashMap<>();

    public void addSessionToGame(Integer gameID, String authToken, Session session){
        // Get the map corresponding to the gameID
        Map<String, Session> gameSessions = sessionMap.computeIfAbsent(gameID, k -> new HashMap<>());

        // Add the session to the game's session map
        gameSessions.put(authToken, session);
    }

    public void removeSessionFromGame(Integer gameID, String authToken){
        // Get the map corresponding to the gameID
        Map<String, Session> gameSessions = sessionMap.get(gameID);

        // Remove the session associated with the authToken from the game's session map
        if (gameSessions != null) {
            gameSessions.remove(authToken);
        }
    }

    Map<String, Session> getSessionsForGame(Integer gameID) {
        return sessionMap.get(gameID);
    }

}
