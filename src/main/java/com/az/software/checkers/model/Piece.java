package com.az.software.checkers.model;

public class Piece {

    private final PlayerColor color;
    private PieceType type;

    public Piece(PlayerColor color) {
        this.color = color;
        this.type = PieceType.MAN;
    }

    public PlayerColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public void promoteToKing() {
        this.type = PieceType.KING;
    }

    public boolean isKing() {
        return this.type == PieceType.KING;
    }

}
