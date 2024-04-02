package webSocketMessages.serverMessages;

import chess.ChessGame;

public class Notification extends ServerMessage {
    public String getMessage() {
        return message;
    }

    private final String message;

    public Notification(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }
}
