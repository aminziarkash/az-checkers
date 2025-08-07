package com.az.software.checkers.domain.exception;

import com.az.software.checkers.domain.model.Position;
import com.az.software.checkers.domain.util.PositionFormatter;

/**
 * Thrown when target square is occupied
 */
public class DestinationOccupiedException extends MoveException {
    public DestinationOccupiedException(Position to) {
        super("Destination occupied: " + PositionFormatter.format(to.row(), to.col()));
    }
}