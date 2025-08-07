package com.az.software.checkers.domain.validator;

import com.az.software.checkers.domain.model.Position;
import com.az.software.checkers.domain.exception.*;
import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.Piece;
import com.az.software.checkers.domain.model.Player;

public class MoveValidator {

    /**
     * Validate if `move` is legal for `player` on the given `board` else throw appropriate exceptions.
     */
    public static void validate(Board board, Move move, Player player) {
        Position from = move.from();
        Position to = move.to();

        // 1) Bounds check
        checkBounds(from, to);

        // 2) Must move your own piece
        Piece p = muscMoveOwnPiece(board, player, from);

        // 3) Destination must be empty
        destinationMustBeEmpty(board, to);

        int dR = to.row() - from.row(), dC = to.col() - from.col();
        int absDR = Math.abs(dR), absDC = Math.abs(dC);

        // 4a) Simple step (one square diagonally)
        if (isMoveNotDiagonally(move, player, p, dR, absDR, absDC)) return;

        // 4b) Jump/capture (two squares diagonally with an opponent in between)
        if (canCaptureOpponent(board, move, player, from, to, absDR, absDC)) return;

        // All other moves are illegal
        throw new MoveException("Unsupported move: " + move) {};
    }

    private static boolean canCaptureOpponent(Board board, Move move, Player player, Position from, Position to, int absDR, int absDC) {
        if (absDR == 2 && absDC == 2) {
            Position mid = getCapturedPosition(from, to);
            Piece midP = board.getPiece(mid.row(), mid.col());
            if (midP == null || midP.getPlayer() == player)
                throw new IllegalJumpException(move);
            return true;
        }
        return false;
    }

    private static boolean isMoveNotDiagonally(Move move, Player player, Piece p, int dR, int absDR, int absDC) {
        if (absDR == 1 && absDC == 1) {
            if (!p.isKing()) {
                if (player == Player.WHITE && dR != -1 ||
                        player == Player.BLACK && dR != 1)
                    throw new IllegalStepException(move);
            }
            return true;
        }
        return false;
    }

    private static void destinationMustBeEmpty(Board board, Position to) {
        if (board.getPiece(to.row(), to.col()) != null) {
            throw new DestinationOccupiedException(to);
        }
    }

    private static Piece muscMoveOwnPiece(Board board, Player player, Position from) {
        Piece p = board.getPiece(from.row(), from.col());
        if (p == null) {
            throw new NoPieceFoundException(from);
        }
        if (p.getPlayer() != player) {
            throw new WrongPieceException(from);
        }
        return p;
    }

    private static void checkBounds(Position from, Position to) {
        if (from.row() < 0 || from.row() > 7 ||
                from.col() < 0 || from.col() > 7) {
            throw new OutOfBoundsException(from);
        }
        if (to.row() < 0 || to.row() > 7 ||
                to.col() < 0 || to.col() > 7) {
            throw new OutOfBoundsException(to);
        }
    }

    /**
     * Given a legal jump move from → to, returns the board position that is captured.
     */
    public static Position getCapturedPosition(Position from, Position to) {
        int row = (from.row() + to.row()) / 2;
        int col = (from.col() + to.col()) / 2;
        return new Position(row, col);
    }

}
