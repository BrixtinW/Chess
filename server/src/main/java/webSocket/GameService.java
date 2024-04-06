package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.SQLDataAccess.SQLAuthDao;
import dataAccess.SQLDataAccess.SQLGameDao;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Map;

public class GameService {

    private static final Gson gson = new Gson();

    public static void joinPlayer(String authToken, Integer gameID, ChessGame.TeamColor teamColor, WebSocketSessions webSocketSessions){
        try {
            SQLGameDao gameDao = new SQLGameDao();
            SQLAuthDao authDao = new SQLAuthDao();
            GameData gameData = gameDao.getGame(gameID);
            AuthData authData = authDao.getAuth(authToken);

            LoadGame loadGame = new LoadGame(gameData.game());
//            String loadGameString = gson.toJson(loadGame);
            sendMessage(webSocketSessions, gameID, authToken, loadGame);

            Notification notification = new Notification(authData.username() + "joined game as" + teamColor.name());
//            String notificationString = gson.toJson(notification);
            broadcastMessage(webSocketSessions, gameID, authToken, notification);

        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
//            String errorString = gson.toJson(error);
            sendMessage(webSocketSessions, gameID, authToken, error);
        }
    }

    public static void joinObserver(String authToken, Integer gameID, WebSocketSessions webSocketSessions){
        try {
            SQLGameDao gameDao = new SQLGameDao();
            SQLAuthDao authDao = new SQLAuthDao();
            GameData gameData = gameDao.getGame(gameID);
            AuthData authData = authDao.getAuth(authToken);

            LoadGame loadGame = new LoadGame(gameData.game());
            sendMessage(webSocketSessions, gameID, authToken, loadGame);

            Notification notification = new Notification(authData.username() + "joined game as an observer");
            broadcastMessage(webSocketSessions, gameID, authToken, notification);

        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
            sendMessage(webSocketSessions, gameID, authToken, error);
        }
    }

    public static void makeMove(String authToken, Integer gameID, ChessMove move, WebSocketSessions webSocketSessions){}

    public static void leaveGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    public static void resignGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    private static void sendMessage(WebSocketSessions webSocketSessions, Integer gameID, String authToken, ServerMessage message) {
        Session session = webSocketSessions.getSessionsForGame(gameID).get(authToken);
        if (session != null) {
            String jsonMessage = gson.toJson(message);
            try {
                session.getRemote().sendString(jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Session not found for gameID: " + gameID + " and authToken: " + authToken);
        }
    }

    private static void broadcastMessage(WebSocketSessions webSocketSessions, Integer gameID, String exceptThisAuthToken, ServerMessage message) {
        for (Map.Entry<String, Session> entry : webSocketSessions.getSessionsForGame(gameID).entrySet()) {
            if (!entry.getKey().equals(exceptThisAuthToken)) {
                String jsonMessage = gson.toJson(message);
                try {
                    entry.getValue().getRemote().sendString(jsonMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
