package webSocket;

import org.glassfish.grizzly.http.server.Session;
import java.util.Map;
import java.util.HashMap;

public class WebSocketSessions {

    private final Map<Integer, Map<String, Session>> sessionMap = new HashMap<>();

    public void addSessionToGame(Integer gameID, String authToken, Session session){
        sessionMap.get(gameID).put(authToken, session);
    }

    public void removeSessionFromGame(Integer gameID, String authToken){
        sessionMap.get(gameID).remove(authToken);
    }

    Map<String, Session> getSessionsForGame(Integer gameID) {
        return sessionMap.get(gameID);
    }

}
