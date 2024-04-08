package ui.WebSocket;

import com.google.gson.Gson;
import ui.ServerFacade;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebSocketFacade extends Endpoint {

    private final GameHandler gameHandler;
    private Session session;

    private ScheduledExecutorService scheduler;

    public WebSocketFacade(GameHandler gameHandler) {
        this.gameHandler = gameHandler;

        connect();
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println("Message received from server: " + message);
                ServerMessage msg = new Gson().fromJson(message, ServerMessage.class);

                if (msg.getMessageType() == ServerMessage.ServerMessageType.ERROR){
                    Error commandObj = new Gson().fromJson(message, Error.class);
                    gameHandler.printMessage(commandObj.getErrorMessage());

//                    if (Objects.equals(commandObj.getErrorMessage(), "Error: Game has already ended")) {
//                        gameHandler.gameEnded = true;
//                    }

                } else if (msg.getMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                    Notification commandObj = new Gson().fromJson(message, Notification.class);
                    gameHandler.printMessage(commandObj.getMessage());
                } else if (msg.getMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
                    LoadGame commandObj = new Gson().fromJson(message, LoadGame.class);
                    gameHandler.updateGame(commandObj.getGame());
                }

            }
        });


    }


//    public void startPingScheduler() {
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(this::sendPing, 0, 5, TimeUnit.SECONDS); // Send ping every 5 seconds
//    }
//
//    public void sendPing() {
//        try {
//            session.getBasicRemote().sendPing(ByteBuffer.wrap("Ping".getBytes())); // Sending a ping message to the server
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void connect() {
        try {
            String url = ServerFacade.SERVER_URL.replace("http", "ws");
            URI uri = new URI(url + "/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
//            startPingScheduler();
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

//    @OnWebSocketMessage
//    public void onMessage(String message) {
//        System.out.println("Message received from server: " + message);
//        ServerMessage msg = new Gson().fromJson(message, ServerMessage.class);
//        System.out.println(msg);
//        System.out.println(msg.getMessageType());
//
//        if (msg.getMessageType() == ServerMessage.ServerMessageType.ERROR){
//            Error commandObj = (Error) msg;
//            gameHandler.printMessage(commandObj.getErrorMessage());
//        } else if (msg.getMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
//            Notification commandObj = (Notification) msg;
//            gameHandler.printMessage(commandObj.getMessage());
//        } else if (msg.getMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
//            LoadGame commandObj = (LoadGame) msg;
//            gameHandler.updateGame(commandObj.getGame());
//        }
//
//    }


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


    @Override
    public void onOpen(javax.websocket.Session session, EndpointConfig config) {

    }
}
