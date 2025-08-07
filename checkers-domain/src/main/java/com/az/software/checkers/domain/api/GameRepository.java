package com.az.software.checkers.domain.api;

import java.util.UUID;

/**
 * Port: load or save the state of a game.
 */
public interface GameRepository {

    /**
     * Load an existing game, or return null if not found.
     */
    GameState load(UUID gameId);

    /**
     * Persist the given state under this gameId.
     */
    void save(UUID gameId, GameState state);
}