package com.az.software.checkers.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardTest {

    @Test
    void initialSetup_hasCorrectPieceCounts() {
        Board board = Board.initialSetup();
        long whiteCount = 0, blackCount = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null) {
                    if (p.getPlayer() == Player.WHITE) whiteCount++;
                    else if (p.getPlayer() == Player.BLACK) blackCount++;
                }
            }
        }

        // each side starts with 12 pieces
        assertThat(whiteCount).isEqualTo(12);
        assertThat(blackCount).isEqualTo(12);
    }

    @Test
    void copy_deepCopiesGrid() {
        Board board = Board.initialSetup();
        Board copy  = board.copy();

        // they should be equal, but not the same instance
        assertThat(copy.toString()).isEqualTo(board.toString());
        assertThat(copy.toString()).isNotSameAs(board.toString());

        // modifying original does not affect the copy
        board.applyMove(new Move(new Position(5,0), new Position(4,1)));
        assertThat(board).isNotEqualTo(copy);
    }

    @Test
    void equalsAndHashCode_consistent() {
        Board a = Board.initialSetup();
        Board b = Board.initialSetup();
        assertThat(a.toString()).isEqualTo(b.toString());
        assertThat(a.toString().hashCode()).isEqualTo(b.toString().hashCode());

        // different board
        a.applyMove(new Move(new Position(5,0), new Position(4,1)));
        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void toString_containsHeadersAndCorrectCells() {
        Board board = Board.initialSetup();
        String s = board.toString();

        // check headers
        assertThat(s).contains("A B C D E F G H")
                .contains("8 | . B . B . B . B | 8")
                .contains("1 | W . W . W . W . | 1")
                .contains("   ----------------");

        // total lines = 1 header + 1 sep + 8 rows + 1 sep + 1 header = 12
        assertThat(s.split("\\R")).hasSize(12);
    }

    @Test
    void applyMove_simpleStep_movesPiece() {
        Board board = Board.initialSetup();
        // Pick a White piece at (5,0)
        Position from = new Position(5, 0);
        Position to   = new Position(4, 1);
        Piece before = board.getPiece(from.row(), from.col());
        assertThat(before).isNotNull()
                .extracting(Piece::getPlayer)
                .isEqualTo(Player.WHITE);

        board.applyMove(new Move(from, to));

        assertThat(board.getPiece(to.row(), to.col()))
                .as("piece should land at target")
                .isNotNull()
                .extracting(Piece::getPlayer)
                .isEqualTo(before.getPlayer());

        assertThat(board.getPiece(from.row(), from.col()))
                .as("origin square should be empty")
                .isNull();
    }
}