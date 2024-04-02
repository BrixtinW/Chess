package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMove extends UserGameCommand{

    private final ChessMove move;

    public MakeMove(String authToken, Integer gameID, ChessMove move) {
        super(authToken, gameID);
        this.move = move;
    }
}
