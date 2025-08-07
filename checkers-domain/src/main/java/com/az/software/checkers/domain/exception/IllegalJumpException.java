package com.az.software.checkers.domain.exception;

import com.az.software.checkers.domain.model.Move;

/**
 * Thrown for invalid jump (no piece to capture)
 */
public class IllegalJumpException extends MoveException {
    public IllegalJumpException(Move move) {
        super("Illegal jump: " + move);
    }
}