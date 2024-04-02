//import chess.*;
import ui.REPLS.PreloginUI;
import server.Server;
import ui.ServerFacade;

public class Main {
    public static void main(String[] args) {
//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//        System.out.println("♕ 240 Chess Client: " + piece);

        Server server = new Server();
        var port = server.run(0);
        ServerFacade.SERVER_URL = ServerFacade.DEFAULT_URL + port;

        PreloginUI repl = new PreloginUI();
        repl.start();

        server.stop();
    }
}