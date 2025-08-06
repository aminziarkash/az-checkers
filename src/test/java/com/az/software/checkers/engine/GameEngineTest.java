package com.az.software.checkers.engine;

import com.az.software.checkers.exception.InvalidMoveException;
import com.az.software.checkers.exception.NoPieceFoundException;
import com.az.software.checkers.exception.NotYourTurnException;
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
    void testInvalidMoveToOccupiedSquare_throwsException() {
        engine.move(new Position(2, 1), new Position(3, 2)); // Black moves
        engine.move(new Position(5, 0), new Position(4, 1)); // White moves

        // Try to move into already occupied square
        assertThrows(InvalidMoveException.class, () -> {
            engine.move(new Position(2, 3), new Position(3, 2)); // Invalid move
        }, "Should throw InvalidMoveException due to occupied destination");
    }

    @Test
    public void testJumpOverOpponent_successfullyCaptures() {
        Board board = engine.getBoard();
        board.setPiece(2, 1, new Piece(PlayerColor.BLACK));
        board.setPiece(3, 2, new Piece(PlayerColor.WHITE));

        boolean result = engine.move(new Position(2, 1), new Position(4, 3));
        assertTrue(result);
        assertNull(board.getPiece(3, 2), "Captured piece should be removed");
        assertNotNull(board.getPiece(4, 3), "Black piece should move to target");
    }

    @Test
    void testPromotionToKing_afterReachingEnd() {
        Board board = engine.getBoard();
        clearBoard(board);
        Piece blackPiece = new Piece(PlayerColor.BLACK);
        board.setPiece(6, 1, blackPiece);

        engine.move(new Position(6, 1), new Position(7, 2)); // Should promote

        assertTrue(blackPiece.isKing(), "Piece should be promoted to King");
    }

    @Test
    void testInvalidBackwardMoveForMan_throwsInvalidMoveException() {
        engine.move(new Position(2, 1), new Position(3, 2)); // Black move
        engine.move(new Position(5, 0), new Position(4, 1)); // White move
        assertThrows(InvalidMoveException.class, () -> {
            engine.move(new Position(3, 2), new Position(2, 3)); // Black tries to go backward
        }, "Non-king should not be allowed to move backward");
    }

    @Test
    void testMoveFromEmptySquare_throwsNoPieceException() {
        assertThrows(NoPieceFoundException.class, () ->
                        engine.move(new Position(3, 3), new Position(4, 4)),
                "Should throw NoPieceException for empty square");
    }

    @Test
    void testMoveWhenNotYourTurn_throwsNotYourTurnException() {
        engine.move(new Position(2, 1), new Position(3, 2)); // Black

        // Try moving another black piece again
        assertThrows(NotYourTurnException.class, () ->
                engine.move(new Position(2, 3), new Position(3, 4)),
                "Should throw NotYourTurnException because it's WHITE's turn");
    }

    private static void clearBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.setPiece(row, col, null);
            }
        }
    }

}