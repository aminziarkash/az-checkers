package com.az.software.checkers.domain.exception;

import com.az.software.checkers.domain.model.Position;
import com.az.software.checkers.domain.util.PositionFormatter;

/**
 * Thrown when you try to move an opponent’s piece
 */
public class WrongPieceException extends MoveException {
    public WrongPieceException(Position from) {
        super("Cannot move opponent’s piece at: " + PositionFormatter.format(from.row(), from.col()));
    }
}