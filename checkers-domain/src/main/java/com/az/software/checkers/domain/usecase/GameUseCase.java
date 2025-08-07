package com.az.software.checkers.domain.usecase;

import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.MoveResult;
import com.az.software.checkers.domain.model.Player;

public interface GameUseCase {

    Board getBoard();
    Player getCurrentPlayer();
    MoveResult applyMove(Move move);
    void reset();

}
