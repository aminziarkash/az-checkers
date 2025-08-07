package com.az.software.checkers.domain.api;

import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.Player;

import java.util.Optional;

/**
 * A pluggable strategy for choosing a Move for a given player on a board.
 */
public interface MoveStrategy {
    /**
     * Return an Optional move for the given board & player.
     * Empty if no moves available (game over).
     */
    Optional<Move> chooseMove(Board board, Player player);
}