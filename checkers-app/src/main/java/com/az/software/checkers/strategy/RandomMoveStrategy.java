package com.az.software.checkers.strategy;


import com.az.software.checkers.domain.api.MoveStrategy;
import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class RandomMoveStrategy implements MoveStrategy {

    private final Random rnd = new Random();

    @Override
    public Optional<Move> chooseMove(Board board, Player player) {
        List<Move> moves = board.getValidMoves(player);
        if (moves.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(moves.get(rnd.nextInt(moves.size())));
    }
}