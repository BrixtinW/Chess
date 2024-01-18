package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;

    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {

        return this.promotionPiece;
    }


    @Override
    public int hashCode() {
//        return super.hashCode();
        if (promotionPiece == null){
            return (startPosition.getRow() + startPosition.getColumn()) * (endPosition.getRow() + endPosition.getColumn());

        }
        else {
        return (startPosition.getRow() + startPosition.getColumn()) * (endPosition.getRow() + endPosition.getColumn()) + promotionPiece.hashCode() + 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        if (endPosition.getRow() == ((ChessMove) obj).endPosition.getRow() && endPosition.getColumn() == ((ChessMove) obj).endPosition.getColumn()){
            if (startPosition.getRow() == ((ChessMove) obj).startPosition.getRow() && startPosition.getColumn() == ((ChessMove) obj).startPosition.getColumn()) {
                if (promotionPiece == ((ChessMove) obj).promotionPiece) {
                    return true;
                }
            }
        }

//        System.out.println(String.format("These should not be equal: {%d,%d} != {%d,%d}", endPosition.getRow(), endPosition.getColumn(), ((ChessMove) obj).endPosition.getRow(), ((ChessMove) obj).endPosition.getColumn()));
        return false;




//        return super.equals(obj);
    }

    public String toString() {
        return String.format("{%d,%d}", endPosition.getRow(), endPosition.getColumn());
    }

}

