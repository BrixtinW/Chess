package webSocket;

import chess.ChessGame;
import chess.ChessMove;

public class GameService {

    public static void joinPlayer(String authToken, Integer gameID, ChessGame.TeamColor teamColor){}

    public static void joinObserver(String authToken, Integer gameID){}

    public static void makeMove(String authToken, Integer gameID, ChessMove move){}

    public static void leaveGame(String authToken, Integer gameID){}

    public static void resignGame(String authToken, Integer gameID){}

}
