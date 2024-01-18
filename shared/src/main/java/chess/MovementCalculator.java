package chess;

import java.util.ArrayList;

public class MovementCalculator {

    public static ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition start, ChessGame.TeamColor pieceColor) {
        int row = start.getRow();
        int colm = start.getColumn();
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();

        while (row < 8 && colm < 8) {
            row++;
            colm++;
            ChessPosition position = new ChessPosition(row, colm);

            if ( board.getPiece(position) == null ) {
                potentialMoves.add(new ChessMove(start, position, null));
            }
            else {
                if ((board.getPiece(position).getTeamColor()).equals(pieceColor)) {
//                    If they are of the same type, then you can move to that square or past that square.
                    break;
                } else {
//                    If there is an enemy piece at that location, you can move there, but you cant move past it.
                    potentialMoves.add(new ChessMove(start, position, null));
                    break;
                }
            }
        }

        row = start.getRow();
        colm = start.getColumn();
        while (row > 1 && colm < 8) {
            row--;
            colm++;
            ChessPosition position = new ChessPosition(row, colm);

            if ( board.getPiece(position) == null ) {
                potentialMoves.add(new ChessMove(start, position, null));
            }
            else {
                if ((board.getPiece(position).getTeamColor()).equals(pieceColor)) {
//                    If they are of the same type, then you can move to that square or past that square.
                    break;
                } else {
//                    If there is an enemy piece at that location, you can move there, but you cant move past it.
                    potentialMoves.add(new ChessMove(start, position, null));
                    break;
                }
            }
        }

        row = start.getRow();
        colm = start.getColumn();
        while (row > 1 && colm > 1) {
            row--;
            colm--;
            ChessPosition position = new ChessPosition(row, colm);

            if ( board.getPiece(position) == null ) {
                potentialMoves.add(new ChessMove(start, position, null));
            }
            else {
                if ((board.getPiece(position).getTeamColor()).equals(pieceColor)) {
//                    If they are of the same type, then you can move to that square or past that square.
                    break;
                } else {
//                    If there is an enemy piece at that location, you can move there, but you cant move past it.
                    potentialMoves.add(new ChessMove(start, position, null));
                    break;
                }
            }

        }

        row = start.getRow();
        colm = start.getColumn();
        while (row < 8 && colm > 1) {
            row++;
            colm--;
            ChessPosition position = new ChessPosition(row, colm);

            if ( board.getPiece(position) == null ) {
                potentialMoves.add(new ChessMove(start, position, null));
            }
            else {
                if ((board.getPiece(position).getTeamColor()).equals(pieceColor)) {
//                    If they are of the same type, then you can move to that square or past that square.
                    break;
                } else {
//                    If there is an enemy piece at that location, you can move there, but you cant move past it.
                    potentialMoves.add(new ChessMove(start, position, null));
                    break;
                }
            }
        }

        return potentialMoves;
    }

//    public static ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition start, ChessPiece.PieceType pieceType) {}
//    public static ArrayList<ChessMove> queenMoves(ChessBoard board, ChessPosition start, ChessPiece.PieceType pieceType) {}
//    public static ArrayList<ChessMove> rookMoves(ChessBoard board, ChessPosition start, ChessPiece.PieceType pieceType) {}
//    public static ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition start, ChessPiece.PieceType pieceType) {}
//    public static ArrayList<ChessMove> knightMoves(ChessBoard board, ChessPosition start, ChessPiece.PieceType pieceType) {}





}
