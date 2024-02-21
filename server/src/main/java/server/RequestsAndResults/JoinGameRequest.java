package server.RequestsAndResults;

import chess.ChessGame;

public record JoinGameRequest(ChessGame.TeamColor chessColor, int gameID) {
}
