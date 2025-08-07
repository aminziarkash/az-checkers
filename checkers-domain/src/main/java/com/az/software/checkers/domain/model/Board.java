package com.az.software.checkers.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final Piece[][] grid;

    private Board(Piece[][] grid) {
        this.grid = grid;
    }

    /**
     * Creates a new board with the standard initial setup:
     * - Rows 0–2 filled with BLACK pieces on alternating squares
     * - Rows 5–7 filled with WHITE pieces on alternating squares
     */
    public static Board initialSetup() {
        Piece[][] grid = new Piece[8][8];
        for (int r = 0; r < 3; r++) {
            for (int c = (r + 1) % 2; c < 8; c += 2) {
                grid[r][c] = new Piece(Player.BLACK, false);
            }
        }
        for (int r = 5; r < 8; r++) {
            for (int c = (r + 1) % 2; c < 8; c += 2) {
                grid[r][c] = new Piece(Player.WHITE, false);
            }
        }
        return new Board(grid);
    }

    /**
     * Returns the piece at the given row/column, or null if empty.
     */
    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    /**
     * Applies the given move, removing any captured piece
     * and promotion to king if a piece reaches the last row.
     */
    public void applyMove(Move move) {
        int fromR = move.from().row(), fromC = move.from().col();
        int toR = move.to().row(), toC = move.to().col();
        Piece p = grid[fromR][fromC];
        grid[fromR][fromC] = null;

        // Remove captured piece if this was a jump
        if (Math.abs(toR - fromR) == 2) {
            int midR = (fromR + toR) / 2, midC = (fromC + toC) / 2;
            grid[midR][midC] = null;
        }

        // King the piece if it reaches the last row
        if ((p.getPlayer() == Player.WHITE && toR == 0) ||
                (p.getPlayer() == Player.BLACK && toR == 7)) {
            p = new Piece(p.getPlayer(), true);
        }

        grid[toR][toC] = p;
    }

    /**
     * Returns true if the given move is legal for the specified player,
     * based on simple steps or single captures.
     */
    public boolean isValidMove(Move move, Player player) {
        int fromR = move.from().row(), fromC = move.from().col();
        int toR = move.to().row(), toC = move.to().col();

        if (inBounds(fromR, fromC) || inBounds(toR, toC)) return false;
        Piece p = grid[fromR][fromC];
        if (p == null || p.getPlayer() != player) return false;
        if (grid[toR][toC] != null) return false;

        int dR = toR - fromR, dC = toC - fromC;
        int absDR = Math.abs(dR), absDC = Math.abs(dC);

        // Simple diagonal move
        if (absDR == 1 && absDC == 1) {
            if (!p.isKing()) {
                // WHITE moves up (dR == -1), BLACK moves down (dR == +1)
                if (player == Player.WHITE && dR != -1) return false;
                if (player == Player.BLACK && dR != 1) return false;
            }
            return true;
        }
        // Capture move (jump)
        else if (absDR == 2 && absDC == 2) {
            int midR = (fromR + toR) / 2, midC = (fromC + toC) / 2;
            Piece mid = grid[midR][midC];
            if (mid == null || mid.getPlayer() == player) return false;
            return true;
        }

        return false;
    }

    /**
     * Checks if a row/col are within 0–7.
     */
    private boolean inBounds(int r, int c) {
        return r < 0 || r >= 8 || c < 0 || c >= 8;
    }


    /**
     * Returns all legal moves for the given player (single steps or captures).
     */
    public List<Move> getValidMoves(Player player) {
        List<Move> moves = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getPlayer() == player) {
                    for (int dr : new int[]{-2, -1, 1, 2}) {
                        for (int dc : new int[]{-2, -1, 1, 2}) {
                            int nr = r + dr, nc = c + dc;
                            Move m = new Move(new Position(r, c), new Position(nr, nc));
                            if (isValidMove(m, player)) {
                                moves.add(m);
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    /**
     * Returns a deep copy of this board.
     */
    public Board copy() {
        Piece[][] newGrid = new Piece[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                newGrid[r][c] = (p == null ? null : new Piece(p.getPlayer(), p.isKing()));
            }
        }
        return new Board(newGrid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board other)) return false;
        return Arrays.deepEquals(this.grid, other.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    A B C D E F G H\n");
        sb.append("   ----------------\n");

        for (int row = 0; row < 8; row++) {
            sb.append(8 - row).append(" | ");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece == null) {
                    sb.append(". ");
                } else {
                    char c = piece.getPlayer() == Player.BLACK ? 'B' : 'W';
                    if (piece.isKing()) {
                        c = Character.toLowerCase(c); // b or w for king
                    }
                    sb.append(c).append(" ");
                }
            }
            sb.append("| ").append(8 - row).append("\n");
        }

        sb.append("   ----------------\n");
        sb.append("    A B C D E F G H\n");

        return sb.toString();
    }
}
