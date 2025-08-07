package com.az.software.checkers.repository;

import com.az.software.checkers.domain.api.GameRepository;
import com.az.software.checkers.domain.api.GameState;
import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Player;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adapter: keeps games in a simple ConcurrentHashMap.
 */
@Repository
public class InMemoryGameRepository implements GameRepository {
    private final ConcurrentHashMap<UUID, GameState> store = new ConcurrentHashMap<>();

    @Override
    public GameState load(UUID gameId) {
        // if missing, start a fresh game
        return store.computeIfAbsent(gameId, id ->
                new GameState(Board.initialSetup(), Player.WHITE)
        );
    }

    @Override
    public void save(UUID gameId, GameState state) {
        store.put(gameId, state);
    }
}