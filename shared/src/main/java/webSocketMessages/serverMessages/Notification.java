package webSocketMessages.serverMessages;

import chess.ChessGame;

public class Notification extends ServerMessage {
    public String getMessage() {
        return message;
    }

    private final String message;

    public Notification(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }
}
