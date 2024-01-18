package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


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


        switch (pieceType){
            case PieceType.KING:
                break;
            case PieceType.QUEEN:
                break;
            case PieceType.BISHOP:
                return new HashSet<ChessMove>(MovementCalculator.bishopMoves(board, myPosition, pieceColor));
            case PieceType.KNIGHT:
                break;
            case PieceType.ROOK:
                return new HashSet<ChessMove>(MovementCalculator.rookMoves(board, myPosition, pieceColor));
            case PieceType.PAWN:
                break;
            default:
                System.out.println("Invalid Piece Type");
                System.out.println(pieceType);
        }
        return new HashSet<>();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
