package ui.REPLS;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import ui.EscapeSequences.*;
import ui.WebSocket.GameHandler;
import ui.WebSocket.WebSocketFacade;
import webSocketMessages.userCommands.*;

import java.util.Collection;

import static ui.EscapeSequences.*;

public class GameplayUI extends REPL implements GameHandler {

    WebSocketFacade wsf = new WebSocketFacade(this);
    String authToken;
    Integer gameID;

    ChessGame game;
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



        if (this.playerColor == null || !this.playerColor.equals(ChessGame.TeamColor.BLACK)) {

            this.board = (boardColors, boardPieces) -> {

                String string = SET_TEXT_BOLD  + SET_BG_COLOR_BLACK + "\n\t" + SET_TEXT_COLOR_BLACK +
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
                System.out.print(SET_TEXT_COLOR_LIGHT_GREY + "\n>> ");

            };
        } else {
            this.board = (boardColors, boardPieces) -> {

                String string = SET_TEXT_BOLD + SET_BG_COLOR_BLACK + "\n\t" + SET_TEXT_COLOR_BLACK +
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
                System.out.print(SET_TEXT_COLOR_LIGHT_GREY + "\n>> ");

            };
        }

    }

    @Override
    protected Boolean evaluate(String[] parsedInput) {

        switch (parsedInput[0]) {
            case "help":
                System.out.println( SET_TEXT_COLOR_RED + "\thelp" + SET_TEXT_COLOR_LIGHT_GREY + " - List all valid commands" + SET_TEXT_COLOR_RED + "\n\tr" + SET_TEXT_COLOR_LIGHT_GREY + " - Redraws the chess board" + SET_TEXT_COLOR_RED + "\n\tleave" + SET_TEXT_COLOR_LIGHT_GREY + " - Temporarily leaves the game session" + SET_TEXT_COLOR_RED + "\n\tm <STARTING_COLUMN><STARTING_ROW>  <TARGET_COLUMN><TARGET_ROW>" + SET_TEXT_COLOR_LIGHT_GREY + " - Moves a piece from the starting location to the target location." +  SET_TEXT_COLOR_RED +"\n\th <SELECTED_PIECE'S_COLUMN> <SELECTED_PIECE'S_ROW>" + SET_TEXT_COLOR_LIGHT_GREY + " - Highlights all available moves for the selected piece"+  SET_TEXT_COLOR_RED +"\n\tresign" + SET_TEXT_COLOR_LIGHT_GREY + " - Forfeits the game");
                break;
            case "quit":
//                YOU SHOULD DELETE THIS EVENTUALLY!!!!!!!!!!!!!!!!!!!!!! THERE IS NO QUIT COMMAND IN THE HELP
                return true;
            case "r":
                this.board.printBoard(colors, pieces);
                break;
            case "leave":
                Leave leave = new Leave(this.authToken, this.gameID);
                String leaveString = this.gson.toJson(leave);
                this.wsf.sendMessage(leaveString);
                this.wsf.disconnect();
                return true;
            case "m":
                if (playerColor != ChessGame.TeamColor.WHITE && playerColor != ChessGame.TeamColor.BLACK){
                    System.out.println("Observers cannot move pieces on the board.");
                    break;
                }

                ChessPosition startPosition = getPosition(parsedInput[1]);
                ChessPosition endPosition = getPosition(parsedInput[2]);

//                YOU STILL HAVE TO MAKE SURE YOU ASK FOR WHICH PIECE THEY WOULD LIKE TO PROMOTE TO!!!

                MakeMove makeMove = new MakeMove(this.authToken, this.gameID, new ChessMove(startPosition, endPosition, null));
                String makeMoveString = this.gson.toJson(makeMove);
                this.wsf.sendMessage(makeMoveString);
                break;
            case "resign":
                Resign resign = new Resign(this.authToken, this.gameID);
                String resignString = this.gson.toJson(resign);
                this.wsf.sendMessage(resignString);
                this.wsf.disconnect();
                return true;
            case "h":
                ChessPosition highlightPosition = getPosition(parsedInput[1]);
                highlightBoard(highlightPosition);
                break;
            default:
                System.out.println("Ya trash cuz.\ntype help for God's sake");
        }
        return false;
    }

    private void highlightBoard(ChessPosition startPosition){

        Collection<ChessMove> potentialMoves = game.validMoves(startPosition);
        System.out.println(potentialMoves);

//        alter the colors array.

        for (ChessMove move : potentialMoves){



        }

        this.board.printBoard(colors, pieces);

        this.colors = new String[][]{
                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE},
                {SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY},
                {SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_WHITE}
        };
    }

    private ChessPosition getPosition(String coordinates) {
//        THIS WORKS FOR WHITE ONLY
        int row = extractNumber(coordinates);;
        int col = 0;


        switch (coordinates.toLowerCase().charAt(0)){
            case 'a':
                    col = 8;
                break;
            case 'b':
                col = 7;
                break;
            case 'c':
                col = 6;
                break;
            case 'd':
                col = 5;
                break;
            case 'e':
                col = 4;
                break;
            case 'f':
                col = 3;
                break;
            case 'g':
                col = 2;
                break;
            case 'h':
                col = 1;
                break;
            default:
        }

        row = 9 - row;
        if (playerColor == ChessGame.TeamColor.BLACK){
            col = 9-col;
        }

        System.out.println(row);
        System.out.println(col);




        return new ChessPosition(row, col);
    }

    public static int extractNumber(String input) {
        // Regular expression to match the number
        String regex = "\\d+";

        // Using Pattern and Matcher to find the number
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        // If a number is found, parse it and return
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        // If no number found, return a default value (you can handle this differently based on your requirements)
        return -1; // or throw an exception, or return a default value, etc.
    }

    @Override
    public void updateGame(ChessGame game) {

        this.game = game;

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {

                String piece = EMPTY;
                int modifier = i;

                if (playerColor == null || playerColor == ChessGame.TeamColor.WHITE) {
                    modifier = 9 - i;
                }

                    if (game.getBoard().boardArray[modifier][j] != null) {

                        boolean pieceIsWhite = false;

                        if (game.getBoard().boardArray[modifier][j].getTeamColor() == ChessGame.TeamColor.WHITE) {
                            pieceIsWhite = true;
                        }

                        switch (game.getBoard().boardArray[modifier][j].getPieceType()) {
                            case ChessPiece.PieceType.PAWN:
                                if (pieceIsWhite) {
                                    piece = WHITE_PAWN;
                                } else {
                                    piece = BLACK_PAWN;
                                }
                                break;
                            case ChessPiece.PieceType.KNIGHT:
                                if (pieceIsWhite) {
                                    piece = WHITE_KNIGHT;
                                } else {
                                    piece = BLACK_KNIGHT;
                                }
                                break;
                            case ChessPiece.PieceType.ROOK:
                                if (pieceIsWhite) {
                                    piece = WHITE_ROOK;
                                } else {
                                    piece = BLACK_ROOK;
                                }
                                break;
                            case ChessPiece.PieceType.BISHOP:
                                if (pieceIsWhite) {
                                    piece = WHITE_BISHOP;
                                } else {
                                    piece = BLACK_BISHOP;
                                }
                                break;
                            case ChessPiece.PieceType.QUEEN:
                                if (pieceIsWhite) {
                                    piece = WHITE_QUEEN;
                                } else {
                                    piece = BLACK_QUEEN;
                                }
                                break;
                            case ChessPiece.PieceType.KING:
                                if (pieceIsWhite) {
                                    piece = WHITE_KING;
                                } else {
                                    piece = BLACK_KING;
                                }
                                break;
                            default:
                                piece = EMPTY;

                        }

                    }


                this.pieces[i-1][j-1] = piece;

            }
        }

        System.out.println(ERASE_SCREEN);
        this.board.printBoard(colors, pieces);
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
