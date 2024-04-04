package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.MemoryDataAccess.MemoryGameDao;
import dataAccess.MemoryDataAccess.MemoryUserDao;
import model.GameData;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.userCommands.JoinPlayer;

import javax.websocket.Session;
import java.util.Map;

public class GameService {

    private static final Gson gson = new Gson();

    public static void joinPlayer(String authToken, Integer gameID, ChessGame.TeamColor teamColor, WebSocketSessions webSocketSessions){
        try {
            MemoryGameDao gameDao = new MemoryGameDao();
            GameData gameData = gameDao.getGame(gameID);

            LoadGame loadGame = new LoadGame(gameData.game());
            String loadGameString = gson.toJson(loadGame);
            sendMessage(webSocketSessions, gameID, authToken, loadGameString);

            String name = "";
            String color = "";
            if (teamColor == ChessGame.TeamColor.BLACK){
                name = gameData.blackUsername();
                color = "black";
            } else if (teamColor == ChessGame.TeamColor.WHITE) {
                name = gameData.whiteUsername();
                color = "white";
            }

            Notification notification = new Notification(name + "joined game as" + color);
            String notificationString = gson.toJson(notification);
            broadcastMessage(webSocketSessions, gameID, authToken, notificationString);

        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
            String errorString = gson.toJson(error);
            sendMessage(webSocketSessions, gameID, authToken, errorString);
        }
    }

    public static void joinObserver(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    public static void makeMove(String authToken, Integer gameID, ChessMove move, WebSocketSessions webSocketSessions){}

    public static void leaveGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    public static void resignGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){}

    private static void sendMessage(WebSocketSessions webSocketSessions, Integer gameID, String authToken, String message) {
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

    private static void broadcastMessage(WebSocketSessions webSocketSessions, Integer gameID, String exceptThisAuthToken, String message) {
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
