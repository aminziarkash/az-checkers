package com.az.software.checkers.domain.model;

public enum Player {
    BLACK,
    WHITE;

    public Player opponent() {
        return this == WHITE ? BLACK : WHITE;
    }
}
