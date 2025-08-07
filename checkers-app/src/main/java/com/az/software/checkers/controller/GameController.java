package com.az.software.checkers.controller;

import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.dto.BoardResponse;
import com.az.software.checkers.dto.MoveRequest;
import com.az.software.checkers.dto.MoveResponse;
import com.az.software.checkers.dto.SimpleResponse;
import com.az.software.checkers.mapper.BoardMapper;
import com.az.software.checkers.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Returns the current board state as JSON.
     */
    @GetMapping("/board")
    public ResponseEntity<BoardResponse> getBoard() {
        Board board = gameService.getBoard();
        List<List<String>> squares = BoardMapper.toDto(board);
        return ResponseEntity.ok(new BoardResponse(squares));
    }

    /**
     * Returns the current board state as a 2D list of strings.
     */
    @GetMapping("/board/text")
    public ResponseEntity<String> getBoardAscii() {
        return ResponseEntity.ok(gameService.getBoard().toString());
    }

    /**
     * Returns whose turn it is.
     */
    @GetMapping("/player")
    public ResponseEntity<SimpleResponse> getCurrentPlayer() {
        String player = gameService.getCurrentPlayer();
        return ResponseEntity.ok(new SimpleResponse(player));
    }

    /**
     * Attempts a move. Expects JSON matching MoveRequest.
     * Returns MoveResponse on success, or 400 with error on invalid move.
     */
    @PostMapping("/move")
    public ResponseEntity<MoveResponse> makeMove(@Validated @RequestBody MoveRequest moveRequest) {
        var result = gameService.makeMove(moveRequest.toDomain());
        return ResponseEntity.ok(new MoveResponse(result.successful(), result.message()));
    }

    /**
     * Resets the game to its initial state.
     */
    @PostMapping("/reset")
    public ResponseEntity<SimpleResponse> reset() {
        gameService.resetGame();
        return ResponseEntity.ok(new SimpleResponse("Game reset successfully"));
    }

}
