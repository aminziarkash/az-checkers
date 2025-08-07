package com.az.software.checkers.service;

import com.az.software.checkers.exception.InvalidMoveException;
import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.MoveResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final CheckersGame checkersGame;

    public GameService(CheckersGame checkersGame) {
        this.checkersGame = checkersGame;
    }

    public Board getBoard() {
        LOGGER.debug("Fetching current board state");
        return checkersGame.getBoard();
    }

    public String getCurrentPlayer() {
        LOGGER.debug("Fetching current player");
        return checkersGame.getCurrentPlayer().toString();
    }


    public MoveResult makeMove(Move move) throws InvalidMoveException {
        LOGGER.debug("Attempting move: {}", move);
        return checkersGame.applyMove(move);
    }

    public void resetGame() {
        LOGGER.debug("Resetting game");
        checkersGame.reset();
    }
}
