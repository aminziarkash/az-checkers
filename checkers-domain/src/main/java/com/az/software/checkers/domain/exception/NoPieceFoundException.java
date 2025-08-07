package com.az.software.checkers.domain.exception;

import com.az.software.checkers.domain.model.Position;
import com.az.software.checkers.domain.util.PositionFormatter;

/**
 * Thrown when there is no piece at the source
 */
public class NoPieceFoundException extends MoveException {
    public NoPieceFoundException(Position from) {
        super("No piece at: " + PositionFormatter.format(from.row(), from.col()));
    }
}