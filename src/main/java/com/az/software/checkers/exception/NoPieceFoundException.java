package com.az.software.checkers.exception;

public class NoPieceFoundException extends RuntimeException {
    public NoPieceFoundException(String message) {
        super(message);
    }
}
