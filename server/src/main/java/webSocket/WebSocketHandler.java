package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.*;


@WebSocket
public class WebSocketHandler {

    private final WebSocketSessions webSocketSessions = new WebSocketSessions();
//    private Gson gson;

    public WebSocketHandler() {}

//    @OnOpen
//    public void onOpen(Session session, Integer gameID, String authToken) {
//        System.out.println("Connected to WebSocket server");
//        webSocketSessions.addSessionToGame(gameID, authToken, session);
//    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {

        System.out.println("Message received from client: " + message);
        UserGameCommand msg = new Gson().fromJson(message, UserGameCommand.class);

        if(webSocketSessions.getSessionsForGame(msg.getGameID()) == null) {
            webSocketSessions.addSessionToGame(msg.getGameID(), msg.getAuthString(), session);
        }


        if (msg.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE){
            MakeMove commandObj = new Gson().fromJson(message, MakeMove.class);
            GameService.makeMove(commandObj.getAuthString(), commandObj.getGameID(), commandObj.getMove(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.JOIN_OBSERVER) {
            JoinObserver commandObj = new Gson().fromJson(message, JoinObserver.class);
            GameService.joinObserver(commandObj.getAuthString(), commandObj.getGameID(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.JOIN_PLAYER) {
            JoinPlayer commandObj = new Gson().fromJson(message, JoinPlayer.class);
            GameService.joinPlayer(commandObj.getAuthString(), commandObj.getGameID(), commandObj.getPlayerColor(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.LEAVE) {
            Leave commandObj = new Gson().fromJson(message, Leave.class);
            GameService.leaveGame(commandObj.getAuthString(), commandObj.getGameID(), webSocketSessions);

        } else if (msg.getCommandType() == UserGameCommand.CommandType.RESIGN) {
            Resign commandObj = new Gson().fromJson(message, Resign.class);
            GameService.resignGame(commandObj.getAuthString(), commandObj.getGameID(), webSocketSessions);

        }

    }

    public void onClose(Integer gameID, String authToken) {
        System.out.println("Connection closed");
        webSocketSessions.removeSessionFromGame(gameID, authToken);
    }

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
