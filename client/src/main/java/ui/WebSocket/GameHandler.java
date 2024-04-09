package ui.WebSocket;

import chess.ChessGame;

public interface GameHandler {

//    public boolean gameEnded = false;

    public void updateGame(ChessGame game);

    public void printMessage(String message);
}
