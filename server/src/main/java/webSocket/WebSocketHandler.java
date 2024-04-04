package webSocket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.glassfish.tyrus.core.wsadl.model.Endpoint;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.util.Map;
import java.util.Objects;

public class WebSocketHandler extends Endpoint {

    private WebSocketSessions webSocketSessions;
//    private Gson gson;

    public WebSocketHandler(WebSocketSessions webSocketSessions) {
        this.webSocketSessions = new WebSocketSessions();
//        this.gson = new Gson();
    }

    @OnOpen
    public void onOpen(Session session, Integer gameID, String authToken) {
        System.out.println("Connected to WebSocket server");
        webSocketSessions.addSessionToGame(gameID, authToken, session);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message received from client: " + message);
        UserGameCommand msg = new Gson().fromJson(message, UserGameCommand.class);
        System.out.println(msg.getCommandType());

        if (msg.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE){
            MakeMove commandObj = (MakeMove) msg;
            GameService.makeMove(commandObj.getAuthString(), commandObj.getGameID(), commandObj.getMove(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.JOIN_OBSERVER) {
            JoinObserver commandObj = (JoinObserver) msg;
            GameService.joinObserver(commandObj.getAuthString(), commandObj.getGameID(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.JOIN_PLAYER) {
            JoinPlayer commandObj = (JoinPlayer) msg;
            GameService.joinPlayer(commandObj.getAuthString(), commandObj.getGameID(), commandObj.getPlayerColor(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.LEAVE) {
            Leave commandObj = (Leave) msg;
            GameService.leaveGame(commandObj.getAuthString(), commandObj.getGameID(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.RESIGN) {
            Resign commandObj = (Resign) msg;
            GameService.resignGame(commandObj.getAuthString(), commandObj.getGameID(), webSocketSessions);

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

//    private void sendMessage(Integer gameID, String authToken, String message) {
//        Session session = webSocketSessions.getSessionsForGame(gameID).get(authToken);
//        if (session != null) {
//            String jsonMessage = gson.toJson(message);
//            try {
//                session.getBasicRemote().sendText(jsonMessage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Session not found for gameID: " + gameID + " and authToken: " + authToken);
//        }
//    }
//
//    private void broadcastMessage(Integer gameID, String exceptThisAuthToken, String message) {
//        for (Map.Entry<String, Session> entry : webSocketSessions.getSessionsForGame(gameID).entrySet()) {
//            if (!entry.getKey().equals(exceptThisAuthToken)) {
//                String jsonMessage = gson.toJson(message);
//                try {
//                    entry.getValue().getBasicRemote().sendText(jsonMessage);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


}
