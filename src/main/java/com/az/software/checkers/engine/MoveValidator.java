package com.az.software.checkers.engine;

import com.az.software.checkers.model.Board;
import com.az.software.checkers.model.Piece;
import com.az.software.checkers.model.PlayerColor;
import com.az.software.checkers.model.Position;

public class MoveValidator {

    public boolean isValidMove(Board board, Position from, Position to, PlayerColor currentPlayer) {
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        if (piece == null || piece.getColor() != currentPlayer) {
            return false;
        }

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        // Not a diagonal move
        if (colDiff != Math.abs(rowDiff)) {
            return false;
        }

        // The destination must be empty
        if (board.getPiece(to.getRow(), to.getCol()) != null) {
            return false;
        }

        boolean isKing = piece.isKing();

        // Simple move (1 square diagonal)
        if (Math.abs(rowDiff) == 1) {
            if (isKing) {
                return true;
            }
            // For men: forward only
            return (piece.getColor() == PlayerColor.BLACK && rowDiff == 1)
                    || (piece.getColor() == PlayerColor.WHITE && rowDiff == -1);
        }

        // Jump move (2 squares diagonal)
        if (Math.abs(rowDiff) == 2) {
            int midRow = (from.getRow() + to.getRow()) / 2;
            int midCol = (from.getCol() + to.getCol()) / 2;
            Piece middlePiece = board.getPiece(midRow, midCol);

            if (middlePiece == null || middlePiece.getColor() == currentPlayer) {
                return false;
            }

            if (isKing) {
                return true;
            }

            // For men: forward only
            return (piece.getColor() == PlayerColor.BLACK && rowDiff == 2)
                    || (piece.getColor() == PlayerColor.WHITE && rowDiff == -2);
        }

        return false;
    }

    public boolean isJumpMove(Position from, Position to) {
        return Math.abs(from.getRow() - to.getRow()) == 2;
    }

    public Position getCapturedPosition(Position from, Position to) {
        int row = (from.getRow() + to.getRow()) / 2;
        int col = (from.getCol() + to.getCol()) / 2;
        return new Position(row, col);
    }

}
