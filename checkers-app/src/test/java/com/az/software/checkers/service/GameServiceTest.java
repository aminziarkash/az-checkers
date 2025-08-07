package com.az.software.checkers.service;

import com.az.software.checkers.domain.api.GameRepository;
import com.az.software.checkers.domain.api.GameState;
import com.az.software.checkers.domain.model.*;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTest {

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