package com.az.software.checkers.model;

public class Board {

    private final Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
        initialize();
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from.getRow(), from.getCol());
        setPiece(to.getRow(), to.getCol(), piece);
        setPiece(from.getRow(), from.getCol(), null);
    }

    public void removePiece(Position position) {
        setPiece(position.getRow(), position.getCol(), null);
    }

    public void initialize() {
        // Place the BLACK pieces on the top 3 rows (0-1-2)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    board[row][col] = new Piece(PlayerColor.BLACK);
                }
            }
        }

        // Place the WHITE pieces on the bottom 3 rows (5-6-7)
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    board[row][col] = new Piece(PlayerColor.WHITE);
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
                Piece piece = board[row][col];
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    A B C D E F G H\n");
        sb.append("   ----------------\n");

        for (int row = 0; row < 8; row++) {
            sb.append(8 - row).append(" | ");
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece == null) {
                    sb.append(". ");
                } else {
                    char c = piece.getColor() == PlayerColor.BLACK ? 'B' : 'W';
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
