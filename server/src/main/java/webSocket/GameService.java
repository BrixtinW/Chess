package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
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
import java.util.Objects;

public class GameService {

    private static final Gson gson = new Gson();

    public static void joinPlayer(String authToken, Integer gameID, ChessGame.TeamColor teamColor, WebSocketSessions webSocketSessions){
        try {
            SQLGameDao gameDao = new SQLGameDao();
            GameData gameData = gameDao.getGame(gameID);
            SQLAuthDao authDao = new SQLAuthDao();
            AuthData authData = authDao.getAuth(authToken);

            if (gameData == null || authData == null){
                Error error = new Error("Error: Invalid game ID or authToken");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }
            if ((teamColor == ChessGame.TeamColor.WHITE && !Objects.equals(gameData.whiteUsername(), authData.username())) || (teamColor == ChessGame.TeamColor.BLACK && !Objects.equals(gameData.blackUsername(), authData.username()))){
                Error error = new Error("Error: Spot already taken");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }
            if (gameData.game() == null){
                Error error = new Error("Error: Game has already ended");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }

            LoadGame loadGame = new LoadGame(gameData.game());
            sendMessage(webSocketSessions, gameID, authToken, loadGame);

            Notification notification = new Notification(authData.username() + " joined game as " + teamColor.name());
            broadcastMessage(webSocketSessions, gameID, authToken, notification);

        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
            sendMessage(webSocketSessions, gameID, authToken, error);
        }
    }

    public static void joinObserver(String authToken, Integer gameID, WebSocketSessions webSocketSessions){
        try {
            SQLGameDao gameDao = new SQLGameDao();
            SQLAuthDao authDao = new SQLAuthDao();
            GameData gameData = gameDao.getGame(gameID);
            AuthData authData = authDao.getAuth(authToken);

            if (gameData == null || authData == null){
                Error error = new Error("Error: Invalid game ID or authToken");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }
            if (gameData.game() == null){
                Error error = new Error("Error: Game has already ended");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }

            LoadGame loadGame = new LoadGame(gameData.game());
            sendMessage(webSocketSessions, gameID, authToken, loadGame);

            Notification notification = new Notification(authData.username() + " joined game as an observer");
            broadcastMessage(webSocketSessions, gameID, authToken, notification);

        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
            sendMessage(webSocketSessions, gameID, authToken, error);
        }
    }

    public static void makeMove(String authToken, Integer gameID, ChessMove move, WebSocketSessions webSocketSessions){
        try {
            SQLGameDao gameDao = new SQLGameDao();
            SQLAuthDao authDao = new SQLAuthDao();
            GameData gameData = gameDao.getGame(gameID);
            AuthData authData = authDao.getAuth(authToken);

            if (gameData.game() == null){
                Error error = new Error("Error: Game has already ended");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }
            if ((!Objects.equals(authData.username(), gameData.whiteUsername()) && gameData.game().getCurrentTurnColor() == ChessGame.TeamColor.WHITE) || (!Objects.equals(authData.username(), gameData.blackUsername()) && gameData.game().getCurrentTurnColor() == ChessGame.TeamColor.BLACK)){
                Error error = new Error("Error: It is not your turn to move.");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }

            if (gameData.game().isInCheckmate(ChessGame.TeamColor.WHITE) || gameData.game().isInCheckmate(ChessGame.TeamColor.BLACK)){
                Error error = new Error("Error: Game is in checkmate and no other moves are allowed.");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }

            if(gameData.game().validMoves(move.getStartPosition()).contains(move)) {
                gameData.game().makeMove(move);
            } else {
                Error error = new Error("Error: Invalid move. Make sure you are not in check and your syntax is correct by typing help.");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }

            gameDao.updateGame(new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), gameData.game()));

            LoadGame loadGame = new LoadGame(gameData.game());
            sendMessage(webSocketSessions, gameID, authToken, loadGame);
            broadcastMessage(webSocketSessions, gameID, authToken, loadGame);

            Notification notification = new Notification(authData.username() + " moved a piece!");
            broadcastMessage(webSocketSessions, gameID, authToken, notification);


            if (gameData.game().isInCheck(ChessGame.TeamColor.WHITE) ){
                Notification gameEvent;
                if (gameData.game().isInCheckmate(ChessGame.TeamColor.WHITE) ){
                    gameEvent = new Notification("White is currently in checkmate.\nGAME OVER");
                } else {
                    gameEvent = new Notification("White is currently in check!");
                }

                sendMessage(webSocketSessions, gameID, authToken, gameEvent);
                broadcastMessage(webSocketSessions, gameID, authToken, gameEvent);

            } else if (gameData.game().isInCheck(ChessGame.TeamColor.BLACK)){
                Notification gameEvent;
                if (gameData.game().isInCheckmate(ChessGame.TeamColor.BLACK)){
                    gameEvent = new Notification("Black is currently in checkmate.\nGAME OVER");
                } else {
                    gameEvent = new Notification("Black is currently in check!");
                }

                sendMessage(webSocketSessions, gameID, authToken, gameEvent);
                broadcastMessage(webSocketSessions, gameID, authToken, gameEvent);
            }


        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
            sendMessage(webSocketSessions, gameID, authToken, error);
        } catch (InvalidMoveException e){
            Error error = new Error("Error: Invalid Move. Make sure it is your turn, and your letter columns and number rows are formatted correctly.");
            sendMessage(webSocketSessions, gameID, authToken, error);
        }
    }

    public static void leaveGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){
        try {
            SQLGameDao gameDao = new SQLGameDao();
            SQLAuthDao authDao = new SQLAuthDao();
            GameData gameData = gameDao.getGame(gameID);
            AuthData authData = authDao.getAuth(authToken);

            String whiteUsername = gameData.whiteUsername();
            String blackUsername = gameData.blackUsername();
            if (Objects.equals(authData.username(), gameData.blackUsername())){
                blackUsername = null;
            }
            if (Objects.equals(authData.username(), gameData.whiteUsername())) {
                whiteUsername = null;
            }

            gameDao.updateGame(new GameData(gameData.gameID(), whiteUsername, blackUsername, gameData.gameName(), gameData.game()));


            Notification notification = new Notification(authData.username() + " has left the game.");
            broadcastMessage(webSocketSessions, gameID, authToken, notification);

            webSocketSessions.removeSessionFromGame(gameID, authToken);

        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
            sendMessage(webSocketSessions, gameID, authToken, error);
        }
    }

    public static void resignGame(String authToken, Integer gameID, WebSocketSessions webSocketSessions){
        try {
            SQLGameDao gameDao = new SQLGameDao();
            SQLAuthDao authDao = new SQLAuthDao();
            GameData gameData = gameDao.getGame(gameID);
            AuthData authData = authDao.getAuth(authToken);

            if (gameData.game() == null){
                Error error = new Error("Error: Game has already ended");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }
            if (!Objects.equals(authData.username(), gameData.blackUsername()) && !Objects.equals(authData.username(), gameData.whiteUsername())){
                Error error = new Error("Error: Observers cannot resign. type leave to leave.");
                sendMessage(webSocketSessions, gameID, authToken, error);
                return;
            }

            gameDao.updateGame(new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), "GAME ENDED (" + gameData.gameName() + ")", null));

            Notification notification = new Notification(authData.username() + " resigned from the game");
            sendMessage(webSocketSessions, gameID, authToken, notification);
            broadcastMessage(webSocketSessions, gameID, authToken, notification);

            Map<String, Session> sessions = webSocketSessions.getSessionsForGame(gameID);
            for (String authorization : sessions.keySet()){
                webSocketSessions.removeSessionFromGame(gameID, authorization);
            }


        } catch (DataAccessException e) {
            Error error = new Error("Error: Invalid Game ID or Game Does Not Exist");
            sendMessage(webSocketSessions, gameID, authToken, error);
        }
    }

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
