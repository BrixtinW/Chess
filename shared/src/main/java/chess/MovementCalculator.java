package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.IntPredicate;

public class MovementCalculator {


    public static ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

            IntPredicate lessThanEight = value -> value < 8;
            IntPredicate greaterThanOne = value -> value > 1;


            checkStraightMoves(1, 1, myPosition, board, potentialMoves, teamColor, lessThanEight, lessThanEight);

            checkStraightMoves(1, -1, myPosition, board, potentialMoves, teamColor, lessThanEight, greaterThanOne);

            checkStraightMoves(-1, -1, myPosition, board, potentialMoves, teamColor, greaterThanOne, greaterThanOne);

            checkStraightMoves(-1, 1, myPosition, board, potentialMoves, teamColor, greaterThanOne, lessThanEight);

            return potentialMoves;
        }

    private static void checkStraightMoves(int rowIncrement, int colIncrement, ChessPosition myPosition, ChessBoard board, ArrayList<ChessMove> potentialMoves, ChessGame.TeamColor teamColor, IntPredicate row_check, IntPredicate col_Check) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        while (row_check.test(row) && col_Check.test(col)) {
            row += rowIncrement;
            col += colIncrement;
            ChessPosition position = new ChessPosition(row, col);

            if (board.getPiece(position) == null) {
                potentialMoves.add(new ChessMove(myPosition, position, null));
            } else {
                if (board.getPiece(position).getTeamColor() == teamColor) {
                    break;
                } else {
                    potentialMoves.add(new ChessMove(myPosition, position, null));
                    break;
                }
            }
        }
    }

    public static ArrayList<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        IntPredicate lessThanEight = value -> value < 8;
        IntPredicate greaterThanOne = value -> value > 1;
        IntPredicate alwaysTrue = value -> true;


        checkStraightMoves(1, 0, myPosition, board, potentialMoves, teamColor, lessThanEight, alwaysTrue);

        checkStraightMoves(-1, 0, myPosition, board, potentialMoves, teamColor, greaterThanOne, alwaysTrue);

        checkStraightMoves(0, -1, myPosition, board, potentialMoves, teamColor, alwaysTrue, greaterThanOne);

        checkStraightMoves(0, 1, myPosition, board, potentialMoves, teamColor, alwaysTrue, lessThanEight);

        return potentialMoves;

    }

    public static ArrayList<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        potentialMoves.addAll(bishopMoves(board, myPosition, teamColor));
        potentialMoves.addAll(rookMoves(board, myPosition, teamColor));

        return potentialMoves;
    }

    public static ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        int row;
        int col;
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        for (int i = -1; i < 2; i++){
            row = myPosition.getRow() + i;
            for (int j = -1; j < 2; j++){
                col = myPosition.getColumn() +j;

                if (row < 9 && row > 0 && col < 9 && col > 0) {
                    ChessPosition position = new ChessPosition(row, col);
                    if (board.getPiece(position) == null || board.getPiece(position).getTeamColor() != teamColor) {
                        potentialMoves.add(new ChessMove(myPosition, position, null));
                    }
                }

            }
        }

        return potentialMoves;
    }

    public static ArrayList<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        int[][] coordinates = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        for (int[] move: coordinates){
            if (row + move[0] < 9 && row + move[0] > 0 && col + move[1] < 9 && col + move[1] > 0){
                ChessPosition position = new ChessPosition(row + move[0], col + move[1]);
                if (board.getPiece(position) == null || board.getPiece(position).getTeamColor() != teamColor){
                    potentialMoves.add(new ChessMove(myPosition, position, null));
                }
            }
        }
        return potentialMoves;
    }

    public static ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        int advance = 0;
        boolean superAdvance = false;

        if(teamColor == ChessGame.TeamColor.WHITE){
            if (row == 2){
                superAdvance = true;
            }
            advance = 1;
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            if (row == 7){
                superAdvance = true;
            }
            advance = -1;
        }

        row += advance;
        ChessPosition position = new ChessPosition(row, col);

        if(board.getPiece(position) == null) {
            if (row == 1 || row == 8) {
                potentialMoves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.BISHOP));
                potentialMoves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.ROOK));
                potentialMoves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.QUEEN));
                potentialMoves.add(new ChessMove(myPosition, position, ChessPiece.PieceType.KNIGHT));
            } else {
                potentialMoves.add(new ChessMove(myPosition, position, null));
            }

            if (superAdvance) {
                row += advance;
                ChessPosition superPosition = new ChessPosition(row, col);
                if (board.getPiece(superPosition) == null) {
                    potentialMoves.add(new ChessMove(myPosition, superPosition, null));
                }
            }

        }



        ChessPosition leftAttack = null;
        ChessPosition rightAttack = null;

        row = myPosition.getRow() + advance;
        col -= 1;
        if (col > 0) {
            leftAttack = new ChessPosition(row, col);
        }
        col += 2;
        if (col < 9) {
            rightAttack = new ChessPosition(row, col);
        }


        if (board.getPiece(leftAttack) != null && board.getPiece(leftAttack).getTeamColor() != teamColor){
            if (row == 1 || row == 8) {
                potentialMoves.add(new ChessMove(myPosition, leftAttack, ChessPiece.PieceType.BISHOP));
                potentialMoves.add(new ChessMove(myPosition, leftAttack, ChessPiece.PieceType.ROOK));
                potentialMoves.add(new ChessMove(myPosition, leftAttack, ChessPiece.PieceType.QUEEN));
                potentialMoves.add(new ChessMove(myPosition, leftAttack, ChessPiece.PieceType.KNIGHT));
            } else {
                potentialMoves.add(new ChessMove(myPosition, leftAttack, null));
            }

        }

        if (board.getPiece(rightAttack) != null && board.getPiece(rightAttack).getTeamColor() != teamColor){
            if (row == 1 || row == 8) {
                potentialMoves.add(new ChessMove(myPosition, rightAttack, ChessPiece.PieceType.BISHOP));
                potentialMoves.add(new ChessMove(myPosition, rightAttack , ChessPiece.PieceType.ROOK));
                potentialMoves.add(new ChessMove(myPosition, rightAttack, ChessPiece.PieceType.QUEEN));
                potentialMoves.add(new ChessMove(myPosition, rightAttack, ChessPiece.PieceType.KNIGHT));
            } else {
                potentialMoves.add(new ChessMove(myPosition, rightAttack, null));
            }

        }





        return potentialMoves;
    }








}
