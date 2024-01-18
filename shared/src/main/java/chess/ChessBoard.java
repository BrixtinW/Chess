package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    public ChessPiece[][] boardArray;
    public ChessBoard() {
        boardArray = new ChessPiece[9][9];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int column = position.getColumn();
        boardArray[row][column] = piece;

    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (position != null) {
            return boardArray[position.getRow()][position.getColumn()];
        }
        else {
            return null;
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

//      Black and White Pawn Loop
        for (int i = 1; i < 9; i++)
        {
            boardArray[7][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            boardArray[2][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        ChessGame.TeamColor[] typeArray = {ChessGame.TeamColor.BLACK, ChessGame.TeamColor.WHITE};

        int starterRow = 8;
        for (ChessGame.TeamColor type: typeArray){

            boardArray[starterRow][1] = new ChessPiece(type, ChessPiece.PieceType.ROOK);
            boardArray[starterRow][2] = new ChessPiece(type, ChessPiece.PieceType.KNIGHT);
            boardArray[starterRow][3] = new ChessPiece(type, ChessPiece.PieceType.BISHOP);
            boardArray[starterRow][4] = new ChessPiece(type, ChessPiece.PieceType.QUEEN);
            boardArray[starterRow][5] = new ChessPiece(type, ChessPiece.PieceType.KING);
            boardArray[starterRow][6] = new ChessPiece(type, ChessPiece.PieceType.BISHOP);
            boardArray[starterRow][7] = new ChessPiece(type, ChessPiece.PieceType.KNIGHT);
            boardArray[starterRow][8] = new ChessPiece(type, ChessPiece.PieceType.ROOK);

            starterRow = 1;
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.boardArray[i][j] == null && that.boardArray[i][j] == null) {
                    continue;
                }
                assert this.boardArray[i][j] != null;
                try {
                    if (!this.boardArray[i][j].equals(that.boardArray[i][j])) {
                        return false;
                    }
                } catch (AssertionError e) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public int hashCode() {
        return Arrays.hashCode(boardArray);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "boardArray=" + Arrays.toString(boardArray) +
                '}';
    }
}
