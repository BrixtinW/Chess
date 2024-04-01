package ui.WebSocket;

import org.glassfish.tyrus.core.cluster.RemoteSession;
import org.glassfish.tyrus.core.wsadl.model.Endpoint;

import javax.websocket.*;
import java.net.URI;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {

    private final GameHandler gameHandler;
    private Session session;

    public WebSocketFacade(GameHandler gameHandler) {
        this.gameHandler = gameHandler;

        try {
            // Connect to WebSocket server
            URI uri = new URI("ws://localhost:8080/websocket"); // Adjust URL accordingly
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
