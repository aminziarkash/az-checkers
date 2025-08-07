package com.az.software.checkers.service;

import com.az.software.checkers.exception.InvalidMoveException;
import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    void testInitialBoardSetup() {
        Board board = gameService.getBoard();
        assertNotNull(board);
    }

    @Test
    void testValidMove() {
        gameService.resetGame();
        Position from = new Position(1, 2);
        Position to = new Position(0, 3);
        Move move = new Move(from, to);
//        Move move = new Move.fromNotation("B6", "A5");
        gameService.makeMove(move);

        // assert expected piece moved, etc.
    }

    @Test
    void testInvalidMove() {
        Position from = new Position(1, 2);
        Position to = new Position(1, 3);
        Move move = new Move(from, to);
        gameService.resetGame();

        assertThrows(InvalidMoveException.class, () -> {
            gameService.makeMove(move);
        });
    }

}