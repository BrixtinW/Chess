package ui.REPLS;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import ui.EscapeSequences.*;
import ui.WebSocket.GameHandler;
import ui.WebSocket.WebSocketFacade;
import webSocketMessages.userCommands.*;

import static ui.EscapeSequences.*;

public class GameplayUI extends REPL implements GameHandler {

    WebSocketFacade wsf = new WebSocketFacade(this);
    ChessGame game;
    String authToken;
    Integer gameID;
    ChessGame.TeamColor playerColor;
    Gson gson = new Gson();
    BoardGenerator board;

    String[][] colors = {
            {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
            {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
            {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
            {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
            {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
            {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
            {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
            {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE}
    };

    String[][] pieces = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };


    public GameplayUI(String authToken, String gameID, String color) {

        this.authToken = authToken;
        this.gameID = Integer.valueOf(gameID);

        if (color != null) {

            if(color.equalsIgnoreCase("black")){
                this.playerColor = ChessGame.TeamColor.BLACK;
            } else if (color.equalsIgnoreCase("white")) {
                this.playerColor = ChessGame.TeamColor.WHITE;
            }

            JoinPlayer joinPlayer = new JoinPlayer(this.authToken, this.gameID, this.playerColor);
            String jsonString = this.gson.toJson(joinPlayer);
            this.wsf.sendMessage(jsonString);

        } else {
            this.playerColor = null;

            JoinObserver joinObserver = new JoinObserver(this.authToken, this.gameID);
            String jsonString = this.gson.toJson(joinObserver);
            this.wsf.sendMessage(jsonString);

        }


//        String[][] colors = {
//                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
//                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
//                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
//                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
//                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
//                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
//                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
//                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE}
//        };
//
//        String[][] pieces = {
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
//                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
//        };



        if (!this.playerColor.equals(ChessGame.TeamColor.BLACK)) {

            this.board = (boardColors, boardPieces) -> {

                String string = SET_TEXT_BOLD + SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  White Board:\n\t" + SET_TEXT_COLOR_BLACK +
                        SET_BG_COLOR_LIGHT_GREY + "    H  G  F  E  D  C  B  A    " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 1 " + boardColors[0][0] + boardPieces[0][0] + boardColors[0][1] + boardPieces[0][1] + boardColors[0][2] + boardPieces[0][2] + boardColors[0][3] + boardPieces[0][3] + boardColors[0][4] + boardPieces[0][4] + boardColors[0][5] + boardPieces[0][5] + boardColors[0][6] + boardPieces[0][6] + boardColors[0][7] + boardPieces[0][7] + SET_BG_COLOR_LIGHT_GREY + " 1 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 2 " + boardColors[1][0] + boardPieces[1][0] + boardColors[1][1] + boardPieces[1][1] + boardColors[1][2] + boardPieces[1][2] + boardColors[1][3] + boardPieces[1][3] + boardColors[1][4] + boardPieces[1][4] + boardColors[1][5] + boardPieces[1][5] + boardColors[1][6] + boardPieces[1][6] + boardColors[1][7] + boardPieces[1][7] + SET_BG_COLOR_LIGHT_GREY + " 2 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 3 " + boardColors[2][0] + boardPieces[2][0] + boardColors[2][1] + boardPieces[2][1] + boardColors[2][2] + boardPieces[2][2] + boardColors[2][3] + boardPieces[2][3] + boardColors[2][4] + boardPieces[2][4] + boardColors[2][5] + boardPieces[2][5] + boardColors[2][6] + boardPieces[2][6] + boardColors[2][7] + boardPieces[2][7] + SET_BG_COLOR_LIGHT_GREY + " 3 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 4 " + boardColors[3][0] + boardPieces[3][0] + boardColors[3][1] + boardPieces[3][1] + boardColors[3][2] + boardPieces[3][2] + boardColors[3][3] + boardPieces[3][3] + boardColors[3][4] + boardPieces[3][4] + boardColors[3][5] + boardPieces[3][5] + boardColors[3][6] + boardPieces[3][6] + boardColors[3][7] + boardPieces[3][7] + SET_BG_COLOR_LIGHT_GREY + " 4 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 5 " + boardColors[4][0] + boardPieces[4][0] + boardColors[4][1] + boardPieces[4][1] + boardColors[4][2] + boardPieces[4][2] + boardColors[4][3] + boardPieces[4][3] + boardColors[4][4] + boardPieces[4][4] + boardColors[4][5] + boardPieces[4][5] + boardColors[4][6] + boardPieces[4][6] + boardColors[4][7] + boardPieces[4][7] + SET_BG_COLOR_LIGHT_GREY + " 5 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 6 " + boardColors[5][0] + boardPieces[5][0] + boardColors[5][1] + boardPieces[5][1] + boardColors[5][2] + boardPieces[5][2] + boardColors[5][3] + boardPieces[5][3] + boardColors[5][4] + boardPieces[5][4] + boardColors[5][5] + boardPieces[5][5] + boardColors[5][6] + boardPieces[5][6] + boardColors[5][7] + boardPieces[5][7] + SET_BG_COLOR_LIGHT_GREY + " 6 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 7 " + boardColors[6][0] + boardPieces[6][0] + boardColors[6][1] + boardPieces[6][1] + boardColors[6][2] + boardPieces[6][2] + boardColors[6][3] + boardPieces[6][3] + boardColors[6][4] + boardPieces[6][4] + boardColors[6][5] + boardPieces[6][5] + boardColors[6][6] + boardPieces[6][6] + boardColors[6][7] + boardPieces[6][7] + SET_BG_COLOR_LIGHT_GREY + " 7 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 8 " + boardColors[7][0] + boardPieces[7][0] + boardColors[7][1] + boardPieces[7][1] + boardColors[7][2] + boardPieces[7][2] + boardColors[7][3] + boardPieces[7][3] + boardColors[7][4] + boardPieces[7][4] + boardColors[7][5] + boardPieces[7][5] + boardColors[7][6] + boardPieces[7][6] + boardColors[7][7] + boardPieces[7][7] + SET_BG_COLOR_LIGHT_GREY + " 8 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + "    H  G  F  E  D  C  B  A    " + RESET_BG_COLOR + "\n\t" + RESET_TEXT_BOLD_FAINT + RESET_TEXT_COLOR;

                System.out.println(string);

            };
        } else {
            this.board = (boardColors, boardPieces) -> {

                String string = SET_TEXT_BOLD + SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  White Board:\n\t" + SET_TEXT_COLOR_BLACK +
                        SET_BG_COLOR_LIGHT_GREY + "    A  B  C  D  E  F  G  H    " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 8 " + boardColors[0][0] + boardPieces[0][0] + boardColors[0][1] + boardPieces[0][1] + boardColors[0][2] + boardPieces[0][2] + boardColors[0][3] + boardPieces[0][3] + boardColors[0][4] + boardPieces[0][4] + boardColors[0][5] + boardPieces[0][5] + boardColors[0][6] + boardPieces[0][6] + boardColors[0][7] + boardPieces[0][7] + SET_BG_COLOR_LIGHT_GREY + " 8 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 7 " + boardColors[1][0] + boardPieces[1][0] + boardColors[1][1] + boardPieces[1][1] + boardColors[1][2] + boardPieces[1][2] + boardColors[1][3] + boardPieces[1][3] + boardColors[1][4] + boardPieces[1][4] + boardColors[1][5] + boardPieces[1][5] + boardColors[1][6] + boardPieces[1][6] + boardColors[1][7] + boardPieces[1][7] + SET_BG_COLOR_LIGHT_GREY + " 7 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 6 " + boardColors[2][0] + boardPieces[2][0] + boardColors[2][1] + boardPieces[2][1] + boardColors[2][2] + boardPieces[2][2] + boardColors[2][3] + boardPieces[2][3] + boardColors[2][4] + boardPieces[2][4] + boardColors[2][5] + boardPieces[2][5] + boardColors[2][6] + boardPieces[2][6] + boardColors[2][7] + boardPieces[2][7] + SET_BG_COLOR_LIGHT_GREY + " 6 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 5 " + boardColors[3][0] + boardPieces[3][0] + boardColors[3][1] + boardPieces[3][1] + boardColors[3][2] + boardPieces[3][2] + boardColors[3][3] + boardPieces[3][3] + boardColors[3][4] + boardPieces[3][4] + boardColors[3][5] + boardPieces[3][5] + boardColors[3][6] + boardPieces[3][6] + boardColors[3][7] + boardPieces[3][7] + SET_BG_COLOR_LIGHT_GREY + " 5 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 4 " + boardColors[4][0] + boardPieces[4][0] + boardColors[4][1] + boardPieces[4][1] + boardColors[4][2] + boardPieces[4][2] + boardColors[4][3] + boardPieces[4][3] + boardColors[4][4] + boardPieces[4][4] + boardColors[4][5] + boardPieces[4][5] + boardColors[4][6] + boardPieces[4][6] + boardColors[4][7] + boardPieces[4][7] + SET_BG_COLOR_LIGHT_GREY + " 4 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 3 " + boardColors[5][0] + boardPieces[5][0] + boardColors[5][1] + boardPieces[5][1] + boardColors[5][2] + boardPieces[5][2] + boardColors[5][3] + boardPieces[5][3] + boardColors[5][4] + boardPieces[5][4] + boardColors[5][5] + boardPieces[5][5] + boardColors[5][6] + boardPieces[5][6] + boardColors[5][7] + boardPieces[5][7] + SET_BG_COLOR_LIGHT_GREY + " 3 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 2 " + boardColors[6][0] + boardPieces[6][0] + boardColors[6][1] + boardPieces[6][1] + boardColors[6][2] + boardPieces[6][2] + boardColors[6][3] + boardPieces[6][3] + boardColors[6][4] + boardPieces[6][4] + boardColors[6][5] + boardPieces[6][5] + boardColors[6][6] + boardPieces[6][6] + boardColors[6][7] + boardPieces[6][7] + SET_BG_COLOR_LIGHT_GREY + " 2 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + " 1 " + boardColors[7][0] + boardPieces[7][0] + boardColors[7][1] + boardPieces[7][1] + boardColors[7][2] + boardPieces[7][2] + boardColors[7][3] + boardPieces[7][3] + boardColors[7][4] + boardPieces[7][4] + boardColors[7][5] + boardPieces[7][5] + boardColors[7][6] + boardPieces[7][6] + boardColors[7][7] + boardPieces[7][7] + SET_BG_COLOR_LIGHT_GREY + " 1 " + RESET_BG_COLOR + "\n\t" +
                        SET_BG_COLOR_LIGHT_GREY + "    A  B  C  D  E  F  G  H    " + RESET_BG_COLOR + "\n\t" + RESET_TEXT_BOLD_FAINT + RESET_TEXT_COLOR;

                System.out.println(string);

            };
        }


//        String blackBoard = SET_TEXT_BOLD + SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  Black Board:\n\t" + SET_TEXT_COLOR_BLACK +
//                SET_BG_COLOR_LIGHT_GREY + "    A  B  C  D  E  F  G  H    " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 8 " + SET_BG_COLOR_WHITE + WHITE_ROOK + SET_BG_COLOR_LIGHT_GREY + WHITE_KNIGHT + SET_BG_COLOR_WHITE + WHITE_BISHOP + SET_BG_COLOR_LIGHT_GREY + WHITE_QUEEN + SET_BG_COLOR_WHITE + WHITE_KING + SET_BG_COLOR_LIGHT_GREY + WHITE_BISHOP + SET_BG_COLOR_WHITE + WHITE_KNIGHT + SET_BG_COLOR_LIGHT_GREY + WHITE_ROOK + SET_BG_COLOR_LIGHT_GREY + " 8 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 7 " + SET_BG_COLOR_LIGHT_GREY + WHITE_PAWN + SET_BG_COLOR_WHITE + WHITE_PAWN + SET_BG_COLOR_LIGHT_GREY + WHITE_PAWN + SET_BG_COLOR_WHITE + WHITE_PAWN + SET_BG_COLOR_LIGHT_GREY + WHITE_PAWN + SET_BG_COLOR_WHITE + WHITE_PAWN + SET_BG_COLOR_LIGHT_GREY + WHITE_PAWN + SET_BG_COLOR_WHITE + WHITE_PAWN + SET_BG_COLOR_LIGHT_GREY + " 7 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 6 " + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_LIGHT_GREY + " 6 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 5 " + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + " 5 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 4 " + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_LIGHT_GREY + " 4 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 3 " + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + EMPTY + SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_LIGHT_GREY + " 3 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 2 " + SET_BG_COLOR_WHITE + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_WHITE + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_WHITE + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_WHITE + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + " 2 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + " 1 " + SET_BG_COLOR_LIGHT_GREY + BLACK_ROOK + SET_BG_COLOR_WHITE + BLACK_KNIGHT + SET_BG_COLOR_LIGHT_GREY + BLACK_BISHOP + SET_BG_COLOR_WHITE + BLACK_QUEEN + SET_BG_COLOR_LIGHT_GREY + BLACK_KING + SET_BG_COLOR_WHITE + BLACK_BISHOP + SET_BG_COLOR_LIGHT_GREY + BLACK_KNIGHT + SET_BG_COLOR_WHITE + BLACK_ROOK + SET_BG_COLOR_LIGHT_GREY + " 1 " + RESET_BG_COLOR + "\n\t" +
//                SET_BG_COLOR_LIGHT_GREY + "    A  B  C  D  E  F  G  H    " + RESET_BG_COLOR + "\n\t" + RESET_TEXT_BOLD_FAINT + RESET_TEXT_COLOR;


//        System.out.println(whiteBoard);
//        System.out.println();
//        System.out.println(blackBoard);

        this.board.printBoard(colors, pieces);
    }

    @Override
    protected Boolean evaluate(String[] parsedInput) {

        switch (parsedInput[0]) {
            case "help":
                System.out.println( SET_TEXT_COLOR_RED + "\thelp" + SET_TEXT_COLOR_LIGHT_GREY + " - List all valid commands" + SET_TEXT_COLOR_RED + "\n\tr" + SET_TEXT_COLOR_LIGHT_GREY + " - Redraws the chess board" + SET_TEXT_COLOR_RED + "\n\tleave" + SET_TEXT_COLOR_LIGHT_GREY + " - Temporarily leaves the game session" + SET_TEXT_COLOR_RED + "\n\tm <STARTING_COLUMN> <STARTING_ROW> - <TARGET_COLUMN> <TARGET_ROW>" + SET_TEXT_COLOR_LIGHT_GREY + " - Moves a piece from the starting location to the target location." +  SET_TEXT_COLOR_RED +"\n\th <SELECTED_PIECE'S_COLUMN> <SELECTED_PIECE'S_ROW>" + SET_TEXT_COLOR_LIGHT_GREY + " - Highlights all available moves for the selected piece"+  SET_TEXT_COLOR_RED +"\n\tresign" + SET_TEXT_COLOR_LIGHT_GREY + " - Forfeits the game");
                break;
            case "quit":
                return true;
            case "r":
                this.board.printBoard(colors, pieces);
                break;
            case "leave":
                Leave leave = new Leave(this.authToken, this.gameID);
                String leaveString = this.gson.toJson(leave);
                this.wsf.sendMessage(leaveString);
                break;
            case "mm":
//                MakeMove makeMove = new MakeMove(this.authToken, this.gameID, new ChessMove());
//                String makeMoveString = this.gson.toJson(makeMove);
//                this.wsf.sendMessage(makeMoveString);
                break;
            case "resign":
                Resign resign = new Resign(this.authToken, this.gameID);
                String resignString = this.gson.toJson(resign);
                this.wsf.sendMessage(resignString);
                break;
            case "h":
                highlightBoard();
                break;
            default:
                System.out.println("Ya trash cuz.\ntype help for God's sake");
        }
        return false;
    }

    private void highlightBoard(){
//        alter the colors array.
//        print the board
//        change the array back to normal.
    }

    @Override
    public void updateGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public void printMessage(String message) {
        System.out.println(ERASE_SCREEN);
        this.board.printBoard(colors, pieces);
        System.out.println(message);
    }

    @FunctionalInterface
    interface BoardGenerator {
        public void printBoard(String[][] boardColors, String[][] boardPieces);
    }


}
