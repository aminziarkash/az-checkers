package com.az.software.checkers.domain.model;

public class Piece {

    private final Player player;
    private PieceType type;

    public Piece(Player player, boolean isKing) {
        this.player = player;
        this.type = PieceType.MAN;
    }

    public Player getPlayer() {
        return player;
    }

    public void promoteToKing() {
        this.type = PieceType.KING;
    }

    public boolean isKing() {
        return this.type == PieceType.KING;
    }

    public String getSymbol() {
        if (isKing()) {
            return player == Player.WHITE ? "♔" : "♚";
        } else {
            return player == Player.WHITE ? "W" : "B";
        }
    }

    @Override
    public String toString() {
        // “W” = red, “B” = black; lowercase = king
        String symbol = (player == Player.WHITE ? "W" : "B");
        return isKing() ? symbol.toLowerCase() : symbol;
    }

}
