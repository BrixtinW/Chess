package webSocket;

import org.glassfish.tyrus.core.wsadl.model.Endpoint;

import javax.websocket.*;

public class WebSocketHandler extends Endpoint {

    private final WebSocketSessions webSocketSessions = new WebSocketSessions();

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
