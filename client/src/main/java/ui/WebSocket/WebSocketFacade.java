package ui.WebSocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.glassfish.tyrus.core.cluster.RemoteSession;
import org.glassfish.tyrus.core.wsadl.model.Endpoint;
import ui.ServerFacade;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.MakeMove;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.net.URI;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {

    private final GameHandler gameHandler;
    private Session session;

    public WebSocketFacade(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        connect();
    }

    public void connect() {
        try {
            URI uri = new URI("ws://" + ServerFacade.DEFAULT_URL + "/websocket");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
            System.out.println(this.session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            this.session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message received from server: " + message);
        ServerMessage msg = new Gson().fromJson(message, ServerMessage.class);
        System.out.println(msg.getMessageType());

        if (msg.getMessageType() == ServerMessage.ServerMessageType.ERROR){
            Error commandObj = (Error) msg;
            gameHandler.printMessage(commandObj.getErrorMessage());
        } else if (msg.getMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
            Notification commandObj = (Notification) msg;
            gameHandler.printMessage(commandObj.getMessage());
        } else if (msg.getMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
            LoadGame commandObj = (LoadGame) msg;
            gameHandler.updateGame(commandObj.getGame());
        }

    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to WebSocket server");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
        disconnect();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on WebSocket: " + throwable.getMessage());
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
