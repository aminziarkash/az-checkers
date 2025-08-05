package com.az.software.checkers.model;

public class Board {

    private final Piece[][] grid;

    public Board() {
        this.grid = new Piece[8][8];
        initialize();
    }

    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        grid[row][col] = piece;
    }

    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from.getRow(), from.getCol());
        setPiece(to.getRow(), to.getCol(), piece);
        setPiece(from.getRow(), from.getCol(), null);
    }

    public void removePiece(Position position) {
        setPiece(position.getRow(), position.getCol(), null);
    }

    private void initialize() {
        // Place the BLACK pieces on the top 3 rows (0-1-2)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    grid[row][col] = new Piece(PlayerColor.BLACK);
                }
            }
        }

        // Place the WHITE pieces on the bottom 3 rows (5-6-7)
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    grid[row][col] = new Piece(PlayerColor.WHITE);
                }
            }
        }
    }

    public void printBoard() {
        System.out.println("    A B C D E F G H");
        System.out.println("   ----------------");
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " | ");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece == null) {
                    System.out.print(". ");
                } else {
                    char c = piece.getColor() == PlayerColor.BLACK ? 'B' : 'W';
                    if (piece.isKing()) {
                        c = Character.toLowerCase(c);
                    }
                    System.out.print(c + " ");
                }
            }
            System.out.println("| " + (8 - row));
        }
        System.out.println("   ----------------");
        System.out.println("    A B C D E F G H");
    }
}
