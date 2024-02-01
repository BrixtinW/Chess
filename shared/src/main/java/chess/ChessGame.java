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

    private Collection<ChessMove> whiteKingMoves = null;

    private Collection<ChessMove> blackKingMoves = null;
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
        if (boardObject.boardArray[row][col] == null){
            return null;
        } else {
            ChessGame.TeamColor teamColor = boardObject.boardArray[startPosition.getRow()][startPosition.getColumn()].getTeamColor();
            if (boardObject.boardArray[startPosition.getRow()][startPosition.getColumn()].getPieceType() == ChessPiece.PieceType.KING){
                if (!isInCheckmate(teamColor)){
                    if (teamColor == TeamColor.WHITE) {
                        return whiteKingMoves;
                    } else if (teamColor == TeamColor.BLACK) {
                        return blackKingMoves;
                    } else { System.out.println("Error in validMoves. Invalid team color"); }
                }
//          Must also include when the king is in check and you must sacrifice a piece by putting it in the way
                if (isInCheck(teamColor)){
//                !!!!!!!!!!!not yet implemented!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



                }



            }
            return boardObject.boardArray[row][col].pieceMoves(boardObject, startPosition);
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
        if (!potentialMoves.isEmpty() && potentialMoves.contains(move)){

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
                        if (move.getEndPosition() == kingPosition){
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
        Collection<ChessMove> kingSafePlaces = null;

        ChessPosition kingPosition = null;
        if (teamColor == TeamColor.WHITE){
            kingPosition = whiteKingPosition;
        } else if (teamColor == TeamColor.BLACK) {
            kingPosition = blackKingPosition;
        } else {
            System.out.println("kingPosition not initialized in isInCheckmate!!");
        }

        kingSafePlaces = boardObject.boardArray[kingPosition.getRow()][kingPosition.getColumn()].pieceMoves(boardObject, kingPosition);
        kingSafePlaces.add(new ChessMove(kingPosition, kingPosition, null));

        for (ChessMove move: kingSafePlaces) {
            if (teamColor == TeamColor.WHITE) {
                whiteKingPosition = move.getEndPosition();
            } else if (teamColor == TeamColor.BLACK) {
                blackKingPosition = move.getEndPosition();
            }

            if (isInCheck(teamColor)){
                kingSafePlaces.remove(move);
            }

        }


        if (teamColor == TeamColor.WHITE) {
            whiteKingPosition = kingPosition;
            if (!kingSafePlaces.isEmpty()){
                whiteKingMoves = kingSafePlaces;
                return false;
            }
        } else if (teamColor == TeamColor.BLACK) {
            blackKingPosition = kingPosition;
            if (!kingSafePlaces.isEmpty()){
                blackKingMoves = kingSafePlaces;
                return false;
            }
        }

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

        if (teamColor == currentTurnColor){

            // if moves is equal to zero and it is that teams turn, then return true;
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    if (boardObject.boardArray[row][col] != null && boardObject.boardArray[row][col].getTeamColor() == teamColor) {
                        Collection<ChessMove> potentialMoves = (validMoves(new ChessPosition(row, col)));
                        if (!potentialMoves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
            return false;
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
