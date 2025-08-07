package com.az.software.checkers.service;

import com.az.software.checkers.domain.api.GameRepository;
import com.az.software.checkers.domain.api.GameState;
import com.az.software.checkers.domain.exception.MoveException;
import com.az.software.checkers.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    void testInitialBoardSetup() {
        Board board = gameService.getBoard(UUID.randomUUID());
        assertNotNull(board);
    }

    @Test
    void testValidMove() {
        // Arrange
        UUID gameId = UUID.randomUUID();
        gameService.resetGame(gameId);

        Position from = new Position(2, 1); // B6
        Position to   = new Position(3, 0); // A5
        Move move = new Move(from, to);

        // Act
        MoveResult result = gameService.makeMove(move, gameId);

        // Assert: result indicates success
        assertThat(result.successful())
                .as("the move should be reported successful")
                .isTrue();
        assertThat(result.message())
                .as("the success message should be as expected")
                .isEqualTo("Moved successfully");

        // Assert: the piece actually moved on the board
        Board boardAfter = gameService.getBoard(gameId);
        Piece moved = boardAfter.getPiece(to.row(), to.col());
        assertThat(moved)
                .as("there should be a piece at the destination square")
                .isNotNull();
        assertThat(moved.getPlayer())
                .as("the moved piece should belong to the moving player (BLACK)")
                .isEqualTo(Player.BLACK);

        // Assert: origin square is now empty
        assertThat(boardAfter.getPiece(from.row(), from.col()))
                .as("the origin square should now be empty")
                .isNull();

        // Assert: turn has switched to the other player
        String nextPlayer = gameService.getCurrentPlayer(gameId);
        assertThat(nextPlayer)
                .as("after BLACK moves, it should be WHITE's turn")
                .isEqualTo("WHITE");
    }

    @Test
    void testInvalidMove() {
        Position from = new Position(1, 2);
        Position to = new Position(1, 3);
        UUID gameId = UUID.randomUUID();
        Move move = new Move(from, to);
        gameService.resetGame(gameId);

        assertThrows(MoveException.class, () -> {
            gameService.makeMove(move, gameId);
        });
    }

    @Test
    void makeMovePersistsNewState() {
        GameRepository repo = mock(GameRepository.class);
        UUID id = UUID.randomUUID();
        // initial state: fresh game
        GameState initial = new GameState(Board.initialSetup(), Player.WHITE);
        when(repo.load(id)).thenReturn(initial);

        GameService svc = new GameService(repo);
        Move m = new Move(new Position(5,0), new Position(4,1));
        MoveResult res = svc.makeMove(m, id);

        assertThat(res.successful()).isTrue();

        ArgumentCaptor<GameState> cap = ArgumentCaptor.forClass(GameState.class);
        verify(repo).save(eq(id), cap.capture());
        GameState saved = cap.getValue();
        assertThat(saved.currentPlayer()).isEqualTo(Player.BLACK);
        assertThat(saved.board().getPiece(4,1).getPlayer()).isEqualTo(Player.WHITE);
    }

}