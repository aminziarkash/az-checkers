package com.az.software.checkers.controller;

import com.az.software.checkers.CheckersApplication;
import com.az.software.checkers.dto.MoveRequest;
import com.az.software.checkers.domain.model.Position;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(
        classes = CheckersApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class GameControllerIT {

    @Autowired MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    void fullGameFlow() throws Exception {
        UUID gameId = UUID.randomUUID();

        // 1) Reset
        mvc.perform(post("/api/{gameId}/reset", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Game reset successfully"));

        // 2) Get initial board
        mvc.perform(get("/api/{gameId}/board", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.squares[0][1]").value("B"));

        // 3) verify current player is BLACK
        mvc.perform(get("/api/{gameId}/player", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("BLACK"));

        // 4) Make a move
        MoveRequest req = new MoveRequest(Position.fromAlgebraic("B6"), Position.fromAlgebraic("C5"));
        mvc.perform(post("/api/{gameId}/move", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successful").value(true));

        // 5) Verify turn switched to WHITE
        mvc.perform(get("/api/{gameId}/player", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("WHITE"));
    }

}
