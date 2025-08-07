package com.az.software.checkers.domain;

import com.az.software.checkers.domain.api.GameState;
import com.az.software.checkers.domain.usecase.GameUseCase;
import com.az.software.checkers.domain.model.*;
import com.az.software.checkers.domain.validator.MoveValidator;

/**
 * Core game orchestrator for Checkers.
 */
public class CheckersGame implements GameUseCase {

    private Board board;
    private Player currentPlayer;

    public CheckersGame() {
        reset();
    }

    public CheckersGame(Board board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Retrieves the current board state.
     */
    @Override
    public Board getBoard() {
        return board;
    }

    /**
     * Retrieves which player's turn it is.
     */
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Attempts to apply a move after validations
     * @param move the attempted move
     * @return a MoveResult indicating success and a message
     */
    @Override
    public MoveResult applyMove(Move move) {
        MoveValidator.validate(board, move, currentPlayer);

        // Apply the move to the board
        board.applyMove(move);

        currentPlayer = currentPlayer.opponent();

        return new MoveResult(true, "Moved successfully");
    }

    public GameState toState() {
        // copy the board so we don’t leak mutability
        return new GameState(this.board.copy(), this.currentPlayer);
    }

    /**
     * Resets the game to initial state.
     */
    @Override
    public void reset() {
        this.board = Board.initialSetup();
        currentPlayer = Player.BLACK; // BLACK starts first
    }

}
