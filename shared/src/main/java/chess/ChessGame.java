package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<ChessPiece> movedPawns = new ArrayList<>();
//    private boolean enPassant = false;
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

    private void setKingPosition(TeamColor teamColor, ChessPosition position){

        if (teamColor == TeamColor.WHITE) {
            whiteKingPosition = position;
        } else  {
            blackKingPosition = position;
        }

    }

    private Collection<ChessMove> specialMoves(ChessPiece piece, int row, int col) {

        if(piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            if(piece.getTeamColor() == TeamColor.WHITE && row == 5) {

                col++;
                if(col < 9 && boardObject.boardArray[row][col] != null && boardObject.boardArray[row][col].getPieceType() == ChessPiece.PieceType.PAWN && boardObject.boardArray[row][col].getTeamColor() != piece.getTeamColor()){
                    if (boardObject.boardArray[row][col].hasMovedOnlyOnce == null) {
                        return Arrays.asList(new ChessMove(new ChessPosition(row, (col - 1)), new ChessPosition((row + 1), col), null));
                    }
                }
                col-=2;
                if(col > 0 && boardObject.boardArray[row][col] != null && boardObject.boardArray[row][col].getPieceType() == ChessPiece.PieceType.PAWN && boardObject.boardArray[row][col].getTeamColor() != piece.getTeamColor()){
                    if (boardObject.boardArray[row][col].hasMovedOnlyOnce == null) {
                        return Arrays.asList(new ChessMove(new ChessPosition(row, col+1), new ChessPosition(row+1, col), null));
                    }
                }

            } else if (piece.getTeamColor() == TeamColor.BLACK && row == 4) {

                col++;
                if(col < 9 && boardObject.boardArray[row][col] != null && boardObject.boardArray[row][col].getPieceType() == ChessPiece.PieceType.PAWN && boardObject.boardArray[row][col].getTeamColor() != piece.getTeamColor()){
                    if (boardObject.boardArray[row][col].hasMovedOnlyOnce == null) {
                        return Arrays.asList(new ChessMove(new ChessPosition(row, col-1), new ChessPosition(row-1, col), null));
                    }
                }
                col-=2;
                if(col > 0 && boardObject.boardArray[row][col] != null && boardObject.boardArray[row][col].getPieceType() == ChessPiece.PieceType.PAWN && boardObject.boardArray[row][col].getTeamColor() != piece.getTeamColor()){
                    if (boardObject.boardArray[row][col].hasMovedOnlyOnce == null) {
                        return Arrays.asList(new ChessMove(new ChessPosition(row, col+1), new ChessPosition(row-1, col), null));
                    }
                }

            }
        } else if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.hasMovedOnlyOnce == null){
            if (!isInCheck(piece.getTeamColor())) {
                if (col + 3 < 9 && boardObject.boardArray[row][col + 3] != null && boardObject.boardArray[row][col + 3].getPieceType() == ChessPiece.PieceType.ROOK && boardObject.boardArray[row][col + 3].hasMovedOnlyOnce == null) {
                    ArrayList<ChessMove> result = new ArrayList<>();
                    if (boardObject.boardArray[row][col + 1] == null && boardObject.boardArray[row][col + 2] == null) {
                        boardObject.boardArray[row][col] = null;
                        boardObject.boardArray[row][col + 1] = piece;
                        setKingPosition(piece.getTeamColor(), new ChessPosition(row, (col+1)));
                        if (!isInCheck(piece.getTeamColor())) {
                            boardObject.boardArray[row][col + 1] = null;
                            boardObject.boardArray[row][col + 2] = piece;
                            setKingPosition(piece.getTeamColor(), new ChessPosition(row, (col+2)));
                            if(!isInCheck(piece.getTeamColor())){
                                setKingPosition(piece.getTeamColor(), new ChessPosition(row, col));
                                boardObject.boardArray[row][col] = piece;
                                boardObject.boardArray[row][col+2] = null;
                                result.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(row, col+2), null));
                            } else {
                                setKingPosition(piece.getTeamColor(), new ChessPosition(row, col));
                                boardObject.boardArray[row][col] = piece;
                                boardObject.boardArray[row][col+2] = null;
                            }
                        } else {
                            boardObject.boardArray[row][col] = piece;
                            boardObject.boardArray[row][col + 1] = null;
                            setKingPosition(piece.getTeamColor(), new ChessPosition(row, col));
                        }

                    } if (col - 4 > 0 && boardObject.boardArray[row][col - 4] != null && boardObject.boardArray[row][col - 4 ].getPieceType() == ChessPiece.PieceType.ROOK && boardObject.boardArray[row][col - 4].hasMovedOnlyOnce == null){
                        if (boardObject.boardArray[row][col - 1] == null && boardObject.boardArray[row][col - 2] == null && boardObject.boardArray[row][col - 3] == null) {
                            boardObject.boardArray[row][col] = null;
                            boardObject.boardArray[row][col - 1] = piece;
                            setKingPosition(piece.getTeamColor(), new ChessPosition(row, (col-1)));
                            if (!isInCheck(piece.getTeamColor())) {
                                boardObject.boardArray[row][col - 1] = null;
                                boardObject.boardArray[row][col - 2] = piece;
                                setKingPosition(piece.getTeamColor(), new ChessPosition(row, (col-2)));
                                if(!isInCheck(piece.getTeamColor())) {
                                    setKingPosition(piece.getTeamColor(), new ChessPosition(row, col));
                                    boardObject.boardArray[row][col] = piece;
                                    boardObject.boardArray[row][col-2] = null;
                                    result.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(row, col-2), null));
                                } else{
                                    setKingPosition(piece.getTeamColor(), new ChessPosition(row, col));
                                    boardObject.boardArray[row][col] = piece;
                                    boardObject.boardArray[row][col-2] = null;
                                }
                            } else{
                                boardObject.boardArray[row][col] = piece;
                                boardObject.boardArray[row][col - 1] = null;
                                setKingPosition(piece.getTeamColor(), new ChessPosition(row, col));
                            }
                        }
                    }
                    return result;
                }
            }
        }



        return new ArrayList<>();
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
        } else {
                    ChessPiece piece = boardObject.boardArray[row][col];
                    ChessPiece takenPiece;
                    Collection<ChessMove> potentialMoves = boardObject.boardArray[row][col].pieceMoves(boardObject, startPosition);
                    Collection<ChessMove> otherMove = specialMoves(piece, row, col);
                    boolean enPassant = false;
                    if (!otherMove.isEmpty()){
                        potentialMoves.addAll(otherMove);
                    }
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


                        takenPiece = boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()];
//                        boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = piece;
                        if(boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()].getPieceType() == ChessPiece.PieceType.PAWN && move.getEndPosition().getColumn() != move.getStartPosition().getColumn() && boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] == null){
                            takenPiece = boardObject.boardArray[move.getStartPosition().getRow()][move.getEndPosition().getColumn()];
                            boardObject.boardArray[move.getStartPosition().getRow()][move.getEndPosition().getColumn()] = null;
                            enPassant = true;
                        }
                        boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
                        boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = piece;

                        if (!isInCheck(teamColor)) {
                            finalMoves.add(move);
                        }

                        if(enPassant){
                            boardObject.boardArray[move.getStartPosition().getRow()][move.getEndPosition().getColumn()] = takenPiece;
                            boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = null;
                        } else {
                        boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = takenPiece;
                        }
                        boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = piece;

                        if (isKing) {
//                            getKingPosition(teamColor) = move.getStartPosition();
                            if (teamColor == TeamColor.WHITE) {
                                whiteKingPosition = move.getStartPosition();
                            } else if (teamColor == TeamColor.BLACK) {
                                blackKingPosition = move.getStartPosition();
                            }
                        }
                    }
                    return finalMoves;
        }
    }

//    private ChessPosition getKingPosition(TeamColor teamColor){
//        if (teamColor == TeamColor.WHITE) {
//            return whiteKingPosition;
//        } else {
//            return blackKingPosition;
//        }
//    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> potentialMoves;
        ChessGame.TeamColor teamColor = boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()].getTeamColor();
        if (teamColor != currentTurnColor) {
            potentialMoves = new ArrayList<>();
        } else {
            potentialMoves = validMoves(move.getStartPosition());
        }
            if (potentialMoves != null && !potentialMoves.isEmpty() && potentialMoves.contains(move)){

                if((boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()].getPieceType() == ChessPiece.PieceType.PAWN) && (move.getEndPosition().getColumn() != move.getStartPosition().getColumn()) && (boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] == null)){

                    boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()];
                    boardObject.boardArray[move.getStartPosition().getRow()][move.getEndPosition().getColumn()] = null;
                    boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;

                } else if ((boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()].getPieceType() == ChessPiece.PieceType.KING) && (Math.abs(move.getStartPosition().getColumn() - move.getEndPosition().getColumn()) > 1)) {

                    boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()];
                    boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
                    if (move.getEndPosition().getColumn() - move.getStartPosition().getColumn() > 0){

                        boardObject.boardArray[move.getEndPosition().getRow()][move.getStartPosition().getColumn() + 1] = boardObject.boardArray[move.getEndPosition().getRow()][8];
                        boardObject.boardArray[move.getEndPosition().getRow()][8] = null;

                    } else if (move.getEndPosition().getColumn() - move.getStartPosition().getColumn() < 0) {

                        boardObject.boardArray[move.getEndPosition().getRow()][move.getStartPosition().getColumn() - 1] = boardObject.boardArray[move.getEndPosition().getRow()][1];
                        boardObject.boardArray[move.getEndPosition().getRow()][1] = null;

                    }

                } else if(move.getPromotionPiece() == null) {
                    boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()];
                    boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
                } else {
                    ChessGame.TeamColor color = boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()].getTeamColor();
                    boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = new ChessPiece(color, move.getPromotionPiece());
                    boardObject.boardArray[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
                }

                updateTurn(boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()]);
                if(boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()].getPieceType() == ChessPiece.PieceType.PAWN){
                    movedPawns.add(boardObject.boardArray[move.getEndPosition().getRow()][move.getEndPosition().getColumn()]);
                }


            } else {
                throw new InvalidMoveException();
            }
    }

    private void updateTurn(ChessPiece piece) {

//        !!!!!!!!!!!!! This is inefficient because then all pawns that have already be checked will always be checked to see if they've moved more than once !!!!!!!!!!!!!!
        if(piece.getPieceType() != ChessPiece.PieceType.PAWN){
            if (piece.hasMovedOnlyOnce == null) {
                piece.hasMovedOnlyOnce = true;
            } else if (piece.hasMovedOnlyOnce) {
                piece.hasMovedOnlyOnce = false;
            }
        }

        for (ChessPiece pawn: movedPawns) {
            if (pawn.hasMovedOnlyOnce == null) {
                pawn.hasMovedOnlyOnce = true;
            } else if (pawn.hasMovedOnlyOnce) {
                pawn.hasMovedOnlyOnce = false;
            }
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

        if (teamColor != currentTurnColor){ return false;}

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
