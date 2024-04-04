package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;

import javax.websocket.Session;
import java.util.Map;

public class GameService {

    public static void joinPlayer(String authToken, Integer gameID, ChessGame.TeamColor teamColor, WebSocketSessions webSocketSessions){


        sendMessage(webSocketSessions, gameID, authToken, "");


        broadcastMessage(webSocketSessions, gameID, authToken, "");


    }

    public static void joinObserver(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    public static void makeMove(String authToken, Integer gameID, ChessMove move, WebSocketSessions webSocketSessions){}

    public static void leaveGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    public static void resignGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    private static void sendMessage(WebSocketSessions webSocketSessions, Integer gameID, String authToken, String message) {
        Session session = webSocketSessions.getSessionsForGame(gameID).get(authToken);
        if (session != null) {
            Gson gson = new Gson();
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

    private static void broadcastMessage(WebSocketSessions webSocketSessions, Integer gameID, String exceptThisAuthToken, String message) {
        for (Map.Entry<String, Session> entry : webSocketSessions.getSessionsForGame(gameID).entrySet()) {
            if (!entry.getKey().equals(exceptThisAuthToken)) {
                Gson gson = new Gson();
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
