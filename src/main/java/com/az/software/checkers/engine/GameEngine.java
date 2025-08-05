package com.az.software.checkers.engine;

import com.az.software.checkers.exception.InvalidMoveException;
import com.az.software.checkers.exception.NoPieceFoundException;
import com.az.software.checkers.exception.NotYourTurnException;
import com.az.software.checkers.model.Board;
import com.az.software.checkers.model.Piece;
import com.az.software.checkers.model.PlayerColor;
import com.az.software.checkers.model.Position;

public class GameEngine {

    private final MoveValidator validator = new MoveValidator();

    private final Board board;
    private PlayerColor currentPlayer;

    public GameEngine() {
        this.board = new Board();
        this.currentPlayer = PlayerColor.BLACK; // Since BLACK starts first
    }

    public void printBoard() {
        board.printBoard();
    }

    public Board getBoard() {
        return board;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean move(Position from, Position to) {
        Piece piece = board.getPiece(from.getRow(), from.getCol());

        if (piece == null) {
            throw new NoPieceFoundException("No piece found at position: " + from);
        }

        if (piece.getColor() != currentPlayer) {
            throw new NotYourTurnException("It's " + currentPlayer + "'s turn, not " + piece.getColor());
        }

        if (!validator.isValidMove(board, from, to, currentPlayer)) {
            throw new InvalidMoveException("Move from " + from + " to " + to + " is not valid.");
        }

        // Check for jump and remove captured piece
        if (validator.isJumpMove(from, to)) {
            Position captured = validator.getCapturedPosition(from, to);
            board.removePiece(captured);
            System.out.println("Captured piece at " + captured);
        }

        board.movePiece(from, to);

        // Promote to King
        if ((piece.getColor() == PlayerColor.BLACK && to.getRow() == 7)
                || (piece.getColor() == PlayerColor.WHITE && to.getRow() == 0)) {
            piece.promoteToKing();
        }

        // Switch turn
        currentPlayer = (currentPlayer == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;
        return true;
    }

}
