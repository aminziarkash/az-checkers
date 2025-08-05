package com.az.software.checkers.engine;

import com.az.software.checkers.model.Board;
import com.az.software.checkers.model.Piece;
import com.az.software.checkers.model.PlayerColor;
import com.az.software.checkers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    private GameEngine engine;

    @BeforeEach
    void setup() {
        engine = new GameEngine();
    }

    /*
            A B C D E F G H
           ----------------
        8 | . B . B . B . B | 8
        7 | B . B . B . B . | 7
        6 | . . . B . B . B | 6
        5 | . . B . . . . . | 5
        4 | . . . . . . . . | 4
        3 | W . W . W . W . | 3
        2 | . W . W . W . W | 2
        1 | W . W . W . W . | 1
           ----------------
            A B C D E F G H
     */
    @Test
    void testValidForwardMoveForBlack() {
        Position from = new Position(2, 1); // B6
        Position to = new Position(3, 2); // C5

        // Act
        boolean result = engine.move(from, to);

        // Assert
        assertTrue(result, "This move is valid");
        assertNull(engine.getBoard().getPiece(2, 1));
        assertNotNull(engine.getBoard().getPiece(3, 2));
    }

    /*
            A B C D E F G H
           ----------------
        8 | . B . B . B . B | 8
        7 | B . B . B . B . | 7
        6 | . . . B . B . B | 6
        5 | . . B . . . . . | 5
        4 | . W . . . . . . | 4
        3 | . . W . W . W . | 3
        2 | . W . W . W . W | 2
        1 | W . W . W . W . | 1
           ----------------
            A B C D E F G H
     */
    @Test
    void testInvalidMoveToOccupiedSquare() {
        engine.move(new Position(2, 1), new Position(3, 2)); // Black moves
        engine.move(new Position(5, 0), new Position(4, 1)); // White moves

        // Act
        boolean result = engine.move(new Position(2, 3), new Position(3, 2)); // Black moves

        // Assert
        assertFalse(result, "Move should be invalid because destination is occupied");
        assertNotNull(engine.getBoard().getPiece(3, 2));
    }

    @Test
    public void testJumpOverOpponent() {
        Board board = engine.getBoard();
        board.setPiece(2, 1, new Piece(PlayerColor.BLACK));
        board.setPiece(3, 2, new Piece(PlayerColor.WHITE));

        // Act
        boolean result = engine.move(new Position(2, 1), new Position(4, 3));

        // Assert
        assertTrue(result, "Jump should be valid");
        assertNull(board.getPiece(3, 2)); // Captured piece should be removed
        assertNotNull(board.getPiece(4, 3)); // Black piece moved
    }

    @Test
    void testPromotionToKing() {
        Board board = engine.getBoard();

        // TODO: Check, I think clearBoard is needed here
        // Clear board
        clearBoard(board);

        Piece blackPiece = new Piece(PlayerColor.BLACK);
        board.setPiece(6, 1, blackPiece);

        // Move to last row
        engine.move(new Position(6, 1), new Position(7, 2));

        assertTrue(blackPiece.isKing(), "Piece should be promoted to King");
    }

    @Test
    void testInvalidBackwardMoveForMan() {
        engine.move(new Position(2, 1), new Position(3, 2)); // Black move
        engine.move(new Position(5, 0), new Position(4, 1)); // White move
        boolean result = engine.move(new Position(3, 2), new Position(2, 3)); // Black moves backward
        assertFalse(result, "Non-king cannot move backward");
    }

    private static void clearBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.setPiece(row, col, null);
            }
        }
    }

}