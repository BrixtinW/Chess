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
import java.net.URI;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {

    private final GameHandler gameHandler;
    private Session session;

    public WebSocketFacade(GameHandler gameHandler) {
        this.gameHandler = gameHandler;


        try {
            // Connect to WebSocket server
            URI uri = new URI("ws://" + ServerFacade.DEFAULT_URL + "/websocket"); // Adjust URL accordingly
            this.session = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);

            // Keep the client running until interrupted
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to WebSocket server");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message received from server: " + message);
        ServerMessage msg = new Gson().fromJson(message, ServerMessage.class);
        System.out.println(msg.getMessageType());

        if (msg.getMessageType() == ServerMessage.ServerMessageType.ERROR){
            Error commandObj = (Error) msg;
            gameHandler.printMessage(commandObj.getErrorMessage());
//            NOT DONE
        } else if (msg.getMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
            Notification commandObj = (Notification) msg;
            gameHandler.printMessage(commandObj.getMessage());
//            NOT DONE
        } else if (msg.getMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
            LoadGame commandObj = (LoadGame) msg;
            gameHandler.updateGame(commandObj.getGame());
//            NOT DONE
        }

    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on WebSocket: " + throwable.getMessage());
    }


}
