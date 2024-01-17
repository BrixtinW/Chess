package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        if (pieceColor == ChessGame.TeamColor.BLACK || pieceColor == ChessGame.TeamColor.WHITE) {
            this.pieceColor = pieceColor;
        }
        for (PieceType pieceType : PieceType.values()) {
//            System.out.println(pieceType);
            if (pieceType.equals(type)) {
                this.pieceType = type;
//                System.out.println("This was successful!");
            }
        }
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        switch (pieceType){
            case PieceType.KING:

            case PieceType.QUEEN:

            case PieceType.BISHOP:
                potentialMoves = bishopMoves(board, myPosition);
            case PieceType.KNIGHT:

            case PieceType.ROOK:

            case PieceType.PAWN:

            default:
                System.out.println("Invalid Piece Type");
        }
//        you have the bishops potential moves. Now you need to check and see if there is anyone in the way.
        for (ChessMove move: potentialMoves){
            if ( board.getPiece(move.getEndPosition()) != null ) {
                if ( (board.getPiece(move.getEndPosition()).pieceColor).equals(pieceColor) ) {
//                    if the pieces are of the same type, remove the potential move from the array list
                    potentialMoves.remove(move);
                }



//              if it is the other players piece, check to see if there are any opposing pieces in front of it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



            }
        }


        return new ArrayList<>();
    }


    private ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition start) {
        int row = start.getRow();
        int colm = start.getColumn();
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();

        while (row < 8 && colm < 8) {
            row++;
            colm++;
            potentialMoves.add(new ChessMove(start, new ChessPosition(row, colm), this.pieceType));
        }

        row = start.getRow();
        colm = start.getColumn();
        while (row > 1 && colm < 8) {potentialMoves.add(new ChessMove(start, new ChessPosition(row, colm), this.pieceType));
            row--;
            colm++;
            potentialMoves.add(new ChessMove(start, new ChessPosition(row, colm), this.pieceType));
        }

        row = start.getRow();
        colm = start.getColumn();
        while (row > 1 && colm > 1) {
            row--;
            colm--;
            potentialMoves.add(new ChessMove(start, new ChessPosition(row, colm), this.pieceType));
        }

        row = start.getRow();
        colm = start.getColumn();
        while (row < 8 && colm > 0) {
            row++;
            colm--;
            potentialMoves.add(new ChessMove(start, new ChessPosition(row, colm), this.pieceType));
        }

        return potentialMoves;
    }
}
