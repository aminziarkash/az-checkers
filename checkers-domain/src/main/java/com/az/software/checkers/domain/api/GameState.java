package com.az.software.checkers.domain.api;

import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Player;

/**
 * Snapshot of a game: the board plus whose turn it is.
 */
public record GameState(Board board, Player currentPlayer) {
}
