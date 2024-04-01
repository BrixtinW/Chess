package ui.WebSocket;

import chess.ChessGame;

public interface GameHandler {

    public String updateGame(ChessGame game);

    public void printMessage(String message);
}
