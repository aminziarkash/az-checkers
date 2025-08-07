package com.az.software.checkers.domain.exception;

/**
 * Base class for move errors
 */
public abstract class MoveException extends RuntimeException {
    public MoveException(String message) {
        super(message);
    }

}