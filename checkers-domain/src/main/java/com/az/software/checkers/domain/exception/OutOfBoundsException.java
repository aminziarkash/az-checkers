package com.az.software.checkers.domain.exception;

import com.az.software.checkers.domain.model.Position;
import com.az.software.checkers.domain.util.PositionFormatter;

/**
 * Thrown when trying to move off the board
 */
public class OutOfBoundsException extends MoveException {
    public OutOfBoundsException(Position pos) {
        super("Position out of bounds: " + PositionFormatter.format(pos.row(), pos.col()));
    }
}