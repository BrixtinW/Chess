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

    public static ArrayList<ChessMove> rookMoves(ChessBoard board, ChessPosition start, ChessGame.TeamColor pieceColor) {
        int row = start.getRow();
        int colm = start.getColumn();
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();

        while (row < 8) {
            row++;
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
        while (row > 1) {
            row--;
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
        while (colm > 1) {
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

        colm = start.getColumn();
        while (colm < 8) {
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

        return potentialMoves;

    }



    public static ArrayList<ChessMove> queenMoves(ChessBoard board, ChessPosition start, ChessGame.TeamColor pieceColor) {

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        potentialMoves.addAll(bishopMoves(board, start, pieceColor));
        potentialMoves.addAll(rookMoves(board, start, pieceColor));

        return potentialMoves;
    }


    public static ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition start, ChessGame.TeamColor pieceColor) {
        int row;
        int colm;
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        ChessPosition position;

        for (int i = -1 ; i <= 1; i++) {
            row = start.getRow();
            row += i;
            for (int j = -1; j <= 1; j++) {
                colm = start.getColumn();
                colm += j;

                if ((row == start.getRow() && colm == start.getColumn()) || (row < 1 || row > 8 || colm < 1 || colm > 8)) {
                    continue;
                } else {
                    position = new ChessPosition(row, colm);
                }

                if (board.getPiece(position) == null) {
                    potentialMoves.add(new ChessMove(start, position, null));
                } else {
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
        }



        return potentialMoves;
    }


    public static ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition start, ChessGame.TeamColor pieceColor) {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        int row = start.getRow();
        int colm = start.getColumn();
        int advance = 0;
        boolean superAdvance = false;
        ChessPosition position;

        ////////////////////////////////////
        if (pieceColor == ChessGame.TeamColor.WHITE){
//            White is at the bottom
            if (row == 2) { superAdvance = true; }
            advance = 1;
        } else if (pieceColor == ChessGame.TeamColor.BLACK) {
//            Black is at the top
            if (row == 7){ superAdvance = true; }
            advance = -1;
        } else {
            System.out.println("ERROR piece was neither BLACK nor WHITE");
        }

        ////////////////////////////////////

        row += advance;
        position = new ChessPosition(row, colm);
        if (board.getPiece(position) == null) {
            if (row == 1 || row == 8){
//                   if the pawn is being promoted
                potentialMoves.add(new ChessMove(start, position, ChessPiece.PieceType.QUEEN));
                potentialMoves.add(new ChessMove(start, position, ChessPiece.PieceType.KNIGHT));
                potentialMoves.add(new ChessMove(start, position, ChessPiece.PieceType.ROOK));
                potentialMoves.add(new ChessMove(start, position, ChessPiece.PieceType.BISHOP));
            } else { potentialMoves.add(new ChessMove(start, position, null)); }
            if(superAdvance){
                row += advance;
                ChessPosition superPosition = new ChessPosition(row, colm);
                if (board.getPiece(superPosition) == null) {
                    potentialMoves.add(new ChessMove(start, superPosition, null));
                }
            }
        }
        ////////////////////////////////////
        row = start.getRow();
        row += advance;
        colm += -1;
        ChessPosition leftAttack = new ChessPosition(row, colm);
        colm += 2;
        ChessPosition rightAttack = new ChessPosition(row, colm);
        if (!(board.getPiece(leftAttack) == null)) {
            if (!(board.getPiece(leftAttack).getTeamColor()).equals(pieceColor)){
                if (row == 1 || row == 8){
                    potentialMoves.add(new ChessMove(start, leftAttack, ChessPiece.PieceType.QUEEN));
                    potentialMoves.add(new ChessMove(start, leftAttack, ChessPiece.PieceType.KNIGHT));
                    potentialMoves.add(new ChessMove(start, leftAttack, ChessPiece.PieceType.BISHOP));
                    potentialMoves.add(new ChessMove(start, leftAttack, ChessPiece.PieceType.ROOK));
                } else {potentialMoves.add(new ChessMove(start, leftAttack, null)); }
            }
        }
        ////////////////////////////////////
        if (!(board.getPiece(rightAttack) == null)) {
            if (!(board.getPiece(rightAttack).getTeamColor()).equals(pieceColor)){
                if (row == 1 || row == 8){
                    potentialMoves.add(new ChessMove(start, rightAttack, ChessPiece.PieceType.QUEEN));
                    potentialMoves.add(new ChessMove(start, rightAttack, ChessPiece.PieceType.KNIGHT));
                    potentialMoves.add(new ChessMove(start, rightAttack, ChessPiece.PieceType.BISHOP));
                    potentialMoves.add(new ChessMove(start, rightAttack, ChessPiece.PieceType.ROOK));
                } else { potentialMoves.add(new ChessMove(start, rightAttack, null));}
            }
        }
        ////////////////////////////////////
        return potentialMoves;
    }



    public static ArrayList<ChessMove> knightMoves(ChessBoard board, ChessPosition start, ChessGame.TeamColor pieceColor) {
        int row = start.getRow();
        int colm = start.getColumn();
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        ArrayList<ChessPosition> potentialPositions = new ArrayList<>();

        row += 2;
        colm ++;
        ChessPosition upRight = new ChessPosition(row, colm);
        potentialPositions.add(upRight);

        colm -= 2;
        ChessPosition upLeft = new ChessPosition(row, colm);
        potentialPositions.add(upLeft);

        row -= 4;
        ChessPosition downLeft = new ChessPosition(row, colm);
        potentialPositions.add(downLeft);

        colm += 2;
        ChessPosition downRight = new ChessPosition(row, colm);
        potentialPositions.add(downRight);

        row++;
        colm++;
        ChessPosition rightDown = new ChessPosition(row, colm);
        potentialPositions.add(rightDown);

        row += 2;
        ChessPosition rightUp = new ChessPosition(row, colm);
        potentialPositions.add(rightUp);

        colm -= 4;
        ChessPosition leftUp = new ChessPosition(row, colm);
        potentialPositions.add(leftUp);

        row -= 2;
        ChessPosition leftDown = new ChessPosition(row, colm);
        potentialPositions.add(leftDown);

        for (ChessPosition position: potentialPositions) {

            if(position.getRow() >= 9 || position.getRow() <= 0 || position.getColumn() >= 9 || position.getColumn() <= 0){
                continue;
            }
            if (board.getPiece(position) == null || !(board.getPiece(position).getTeamColor().equals(pieceColor))) {
                potentialMoves.add(new ChessMove(start, position, null));
            }

        }
        return potentialMoves;
    }


}
