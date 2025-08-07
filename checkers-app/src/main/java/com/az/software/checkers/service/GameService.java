package com.az.software.checkers.service;

import com.az.software.checkers.domain.CheckersGame;
import com.az.software.checkers.domain.api.GameRepository;
import com.az.software.checkers.domain.api.GameState;
import com.az.software.checkers.domain.api.MoveStrategy;
import com.az.software.checkers.domain.model.Player;
import com.az.software.checkers.exception.InvalidMoveException;
import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.MoveResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepository repository;

    private final MoveStrategy ai;

    public GameService(GameRepository repository, MoveStrategy ai) {
        this.repository = repository;
        this.ai = ai;
    }

    /**
     * Returns the current board for the given gameId.
     */
    public Board getBoard(UUID gameId) {
        LOGGER.debug(String.format("Fetching current board state using gameId:%s", gameId));
        GameState state = repository.load(gameId);
        return state.board();
    }

    /**
     * Returns the current player (as a String) for the given gameId.
     */
    public String getCurrentPlayer(UUID gameId) {
        LOGGER.debug(String.format("Fetching current player for the gameId %s", gameId));
        GameState state = repository.load(gameId);
        return state.currentPlayer().toString();
    }

    /**
     * Attempts the given move for the specified gameId.
     * Loads state, the domain game and apply the move,
     * persists the new state, and returns the MoveResult.
     */
    public MoveResult makeMove(Move move, UUID gameId) throws InvalidMoveException {
        LOGGER.debug(String.format("Attempting move: %s for gameId: %s", move, gameId));

        // 1) Load the existing game state
        GameState currentState = repository.load(gameId);

        // 2) Reload the pure-domain game
        CheckersGame game = new CheckersGame(
                currentState.board(),
                currentState.currentPlayer()
        );

        // 3) Apply the human move (may throw domain exceptions)
        MoveResult humanResult = game.applyMove(move);

        // 4) Persist the updated state after human move
        repository.save(gameId, game.toState());

        // 5) If the game is not over, let the AI take its turn
        Player nextPlayer = game.getCurrentPlayer();
        ai.chooseMove(game.getBoard(), nextPlayer)
                .ifPresent(aiMove -> {
                    LOGGER.debug("AI chooses move: {} for gameId {}", aiMove, gameId);
                    MoveResult aiResult = game.applyMove(aiMove);
                    LOGGER.debug("AI moveResult: {}", aiResult);
                    // persist after AI move
                    repository.save(gameId, game.toState());
                });

        // 6) Return only the human MoveResult
        return humanResult;
    }

    /**
     * Resets the game for the given gameId to a fresh initial state.
     */
    public void resetGame(UUID gameId) {
        LOGGER.debug(String.format("Resetting game for gameId: %s", gameId));
        CheckersGame fresh = new CheckersGame();
        repository.save(gameId, fresh.toState());
    }
}
