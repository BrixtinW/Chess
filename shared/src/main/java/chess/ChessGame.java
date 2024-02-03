package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor currentTurnColor = TeamColor.WHITE;
    private ChessBoard boardObject = new ChessBoard();
    private ChessPosition whiteKingPosition = null;
    private ChessPosition blackKingPosition = null;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.currentTurnColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.currentTurnColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        int row  = startPosition.getRow();
        int col = startPosition.getColumn();
        ChessGame.TeamColor teamColor = boardObject.boardArray[startPosition.getRow()][startPosition.getColumn()].getTeamColor();

        if (boardObject.boardArray[row][col] == null){
            return null;
        } else if (teamColor != currentTurnColor) {
            return new ArrayList<>();
        } else {
//            if (boardObject.boardArray[startPosition.getRow()][startPosition.getColumn()].getPieceType() == ChessPiece.PieceType.KING) {
                    ChessPiece piece = boardObject.boardArray[row][col];
                    ChessPiece takenPiece;
                    Collection<ChessMove> potentialMoves = boardObject.boardArray[row][col].pieceMoves(boardObject, startPosition);
                    ArrayList<ChessMove> finalMoves = new ArrayList<>();
                    boolean isKing = false;

            if (boardObject.boardArray[startPosition.getRow()][startPosition.getColumn()].getPieceType() == ChessPiece.PieceType.KING) { isKing = true;}


                    for (ChessMove move: potentialMoves){

                        if (isKing) {
                            if (teamColor == TeamColor.WHITE) {
                                whiteKingPosition = move.getEndPosition();
                            } else if (teamColor == TeamColor.BLACK) {
                                blackKingPosition = move.getEndPosition();
                            }
                        }

                        boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
                        takenPiece = boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()];
                        boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = piece;

                        if (!isInCheck(teamColor)) {
                            finalMoves.add(move);
                        }

                        boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = takenPiece;
//                        takenPiece = boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()];
                        boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = piece;

                        if (isKing) {
                            if (teamColor == TeamColor.WHITE) {
                                whiteKingPosition = move.getStartPosition();
                            } else if (teamColor == TeamColor.BLACK) {
                                blackKingPosition = move.getStartPosition();
                            }
                        }
                    }
                    return finalMoves;
//            }
//                if (isInCheck(teamColor)){
//                    ChessPiece piece = boardObject.boardArray[row][col];
//                    ChessPiece takenPiece;
//                    Collection<ChessMove> potentialMoves = boardObject.boardArray[row][col].pieceMoves(boardObject, startPosition);
//                    ArrayList<ChessMove> finalMoves = new ArrayList<>();
//
//                    for (ChessMove move: potentialMoves){
//
//
//                        boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
//                        takenPiece = boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()];
//                        boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = piece;
//
//
//                        if (!isInCheck(teamColor)) {
//                            finalMoves.add(move);
//                        }
//
//                        boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = takenPiece;
//                        boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = piece;
//
//                    }
//                    return finalMoves;
//                }
//
//            return boardObject.boardArray[row][col].pieceMoves(boardObject, startPosition);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> potentialMoves = validMoves(move.getStartPosition());
        if (potentialMoves != null && !potentialMoves.isEmpty() && potentialMoves.contains(move)){

            if(move.getPromotionPiece() == null) {
                boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()];
                boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
            } else {
                ChessGame.TeamColor color = boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()].getTeamColor();
                boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = new ChessPiece(color, move.getPromotionPiece());
                boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
            }

        } else {
            throw new InvalidMoveException();
        }




        if (currentTurnColor == TeamColor.WHITE){
            currentTurnColor = TeamColor.BLACK;
        }
        else if (currentTurnColor == TeamColor.BLACK) {
            currentTurnColor = TeamColor.WHITE;
        }



    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPosition = null;
        if (teamColor == TeamColor.WHITE){
            kingPosition = whiteKingPosition;
        } else if (teamColor == TeamColor.BLACK) {
            kingPosition = blackKingPosition;
        }else{
            System.out.println("kingPosition not initialized in isInCheck!!");
        }


        for (int row = 1; row <= 8; row++){
            for (int col = 1; col <= 8; col++){
                if (boardObject.boardArray[row][col] == null || boardObject.boardArray[row][col].getTeamColor() == teamColor){
                    continue;
                }
                else {
                    Collection<ChessMove> potentialMoves = boardObject.boardArray[row][col].pieceMoves(boardObject, new ChessPosition(row, col));

                    for (ChessMove move: potentialMoves){
                        if (move.getEndPosition().equals(kingPosition)){
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        for (int row = 1; row <= 8; row++){
            for (int col = 1; col <= 8; col++){
                if (boardObject.boardArray[row][col] == null || boardObject.boardArray[row][col].getTeamColor() != teamColor){
                    continue;
                }
                else {
                    if (currentTurnColor != teamColor){
                        return false;
                    }
                    Collection<ChessMove> potentialMoves = validMoves(new ChessPosition(row, col));
                    if (potentialMoves != null && !potentialMoves.isEmpty()){
                    return false;
                    }
                }
            }
        }


//        if (currentTurnColor == TeamColor.WHITE){
//            currentTurnColor = TeamColor.BLACK;
//        }
//        else if (currentTurnColor == TeamColor.BLACK) {
//            currentTurnColor = TeamColor.WHITE;
//        }
        return true;

    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        if (teamColor == currentTurnColor){ return false;}

            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    if (boardObject.boardArray[row][col] != null && boardObject.boardArray[row][col].getTeamColor() == teamColor) {
                        if (!validMoves(new ChessPosition(row, col)).isEmpty()) {
                            return false;
                        }
                    }
                }
            }
            return true;
    }


    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.boardObject = board;


        for (int row = 1; row <= 8; row++){
            for (int col = 1; col <= 8; col++) {
                if (boardObject.boardArray[row][col] != null && boardObject.boardArray[row][col].getPieceType() == ChessPiece.PieceType.KING){
                    if (boardObject.boardArray[row][col].getTeamColor() == TeamColor.WHITE){
                        this.whiteKingPosition = new ChessPosition(row, col);
                    } else if (boardObject.boardArray[row][col].getTeamColor() == TeamColor.BLACK) {
                        this.blackKingPosition = new ChessPosition(row, col);
                    }
                    else {
                        System.out.println("There was an error! it found a king that was neither black nor white!");
                    }
                }
            }
        }
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.boardObject;
    }
}
