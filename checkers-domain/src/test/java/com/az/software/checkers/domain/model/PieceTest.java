package com.az.software.checkers.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PieceTest {

    @Test
    void constructor_initializesPlayerAndNotKing() {
        Piece whiteMan = new Piece(Player.WHITE, false);
        Piece blackMan = new Piece(Player.BLACK, false);

        assertThat(whiteMan.getPlayer()).isEqualTo(Player.WHITE);
        assertThat(whiteMan.isKing()).isFalse();

        assertThat(blackMan.getPlayer()).isEqualTo(Player.BLACK);
        assertThat(blackMan.isKing()).isFalse();
    }

    @Test
    void promoteToKing_setsKingFlag() {
        Piece p = new Piece(Player.WHITE, false);
        assertThat(p.isKing()).isFalse();

        p.setType(PieceType.KING);

        assertThat(p.isKing()).isTrue();
    }

    @Test
    void getSymbol_returnsLetterForMen() {
        Piece whiteMan = new Piece(Player.WHITE, false);
        Piece blackMan = new Piece(Player.BLACK, false);

        assertThat(whiteMan.getSymbol()).isEqualTo("W");
        assertThat(blackMan.getSymbol()).isEqualTo("B");
    }

    @Test
    void getSymbol_returnsUnicodeForKings() {
        Piece white = new Piece(Player.WHITE, true);
        Piece black = new Piece(Player.BLACK, true);

        assertThat(white.getSymbol()).isEqualTo("♔");
        assertThat(black.getSymbol()).isEqualTo("♚");
    }

    @Test
    void toString_usesAsciiUppercaseForMen() {
        Piece whiteMan = new Piece(Player.WHITE, false);
        Piece blackMan = new Piece(Player.BLACK, false);

        assertThat(whiteMan.toString()).isEqualTo("W");
        assertThat(blackMan.toString()).isEqualTo("B");
    }

    @Test
    void toString_usesAsciiLowercaseForKings() {
        Piece white = new Piece(Player.WHITE, true);
        Piece black = new Piece(Player.BLACK, true);

        assertThat(white.toString()).isEqualTo("w");
        assertThat(black.toString()).isEqualTo("b");
    }

}