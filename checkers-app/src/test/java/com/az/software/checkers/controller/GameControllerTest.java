package com.az.software.checkers.controller;

import com.az.software.checkers.domain.model.*;
import com.az.software.checkers.dto.MoveRequest;
import com.az.software.checkers.mapper.MoveMapper;
import com.az.software.checkers.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    GameService gameService;

    @Test
    void getBoard_ReturnsBoardResponse() throws Exception {
        UUID gameId = UUID.randomUUID();
        Board board = Board.initialSetup();
        when(gameService.getBoard(gameId)).thenReturn(board);

        mockMvc.perform(get("/api/{gameId}/board", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.squares").isArray())
                .andExpect(jsonPath("$.squares.length()").value(8))
                .andExpect(jsonPath("$.squares[0].length()").value(8));

        verify(gameService).getBoard(gameId);
    }

    @Test
    void getCurrentPlayer_ReturnsSimpleResponse() throws Exception {
        UUID gameId = UUID.randomUUID();
        when(gameService.getCurrentPlayer(gameId)).thenReturn(Player.WHITE.toString());

        mockMvc.perform(get("/api/{gameId}/player", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("WHITE"));

        verify(gameService).getCurrentPlayer(gameId);
    }

    @Test
    void makeMove_ReturnsMoveResponse() throws Exception {
        UUID gameId = UUID.randomUUID();
        MoveRequest req = new MoveRequest(new Position(5, 0), new Position(4, 1));
        Move move = MoveMapper.toDomain(req);
        MoveResult result = new MoveResult(true, "Success");

        when(gameService.makeMove(eq(move), eq(gameId))).thenReturn(result);

        mockMvc.perform(post("/api/{gameId}/move", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.message").value("Success"));

        verify(gameService).makeMove(move, gameId);
    }

    @Test
    void resetGame_returnsOk() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(post("/api/" + id + "/reset"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Game reset successfully"));
        verify(gameService).resetGame(id);
    }

    @Test
    void makeMove_returnsSuccessJson() throws Exception {
        UUID id = UUID.randomUUID();
        MoveRequest req = new MoveRequest(new Position(5, 0), new Position(4, 1));
        Move move = MoveMapper.toDomain(req);

        when(gameService.makeMove(move, id)).thenReturn(new MoveResult(true, "OK"));

        mockMvc.perform(post("/api/" + id + "/move")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true))
                .andExpect(jsonPath("$.message").value("OK"));
    }
}