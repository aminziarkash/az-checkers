package com.az.software.checkers.domain.model;

/**
 * A board coordinate. 0 ≤ row,col ≤ 7.
 *
 * Supports binding from:
 *  • { "row": 5, "col": 0 }
 *  • "B6"
 */
public record Position(int row, int col) {

    /**
     * Create from algebraic notation, e.g. "A1" .. "H8".
     * Columns A..H map to 0..7, rows 1..8 map to 7..0.
     */
    public static Position fromAlgebraic(String alg) {
        if (alg == null || alg.length() != 2) {
            throw new IllegalArgumentException("Invalid position: " + alg);
        }
        char file = Character.toUpperCase(alg.charAt(0));
        char rank = alg.charAt(1);
        int col = file - 'A';
        int row = 8 - Character.getNumericValue(rank);
        if (col < 0 || col > 7 || row < 0 || row > 7) {
            throw new IllegalArgumentException("Invalid position: " + alg);
        }
        return new Position(row, col);
    }
}