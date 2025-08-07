package com.az.software.checkers.domain.exception;

import com.az.software.checkers.domain.model.Move;

/**
 * Thrown for invalid step (non‐diagonal, wrong direction, etc.)
 */
public class IllegalStepException extends MoveException {
    public IllegalStepException(Move move) {
        super("Illegal step: " + move);
    }
}