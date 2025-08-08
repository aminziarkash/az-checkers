package com.az.software.checkers.controller;

import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.dto.BoardResponse;
import com.az.software.checkers.dto.MoveRequest;
import com.az.software.checkers.dto.MoveResponse;
import com.az.software.checkers.dto.SimpleResponse;
import com.az.software.checkers.mapper.BoardMapper;
import com.az.software.checkers.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.az.software.checkers.config.ApiVersionsConfig.API_V1;

@Tag(name = "AZ Checkers Game", description = "Play and inspect my checkers games")
@RestController
@RequestMapping(API_V1 + "/{gameId}")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(
            summary = "Get current board",
            description = "Retrieve the 8×8 board state for a given game"
    )
    @ApiResponse(responseCode = "200", description = "Board successfully retrieved")
    @GetMapping("/board")
    public ResponseEntity<BoardResponse> getBoard(
            @Parameter(description = "UUID of the game")
            @PathVariable("gameId") UUID gameId) {
        Board board = gameService.getBoard(gameId);
        List<List<String>> squares = BoardMapper.toDto(board);
        return ResponseEntity.ok(new BoardResponse(squares));
    }

    @Operation(
            summary = "Get current board as text",
            description = "Retrieve the 8×8 board state for a given game"
    )
    @ApiResponse(responseCode = "200", description = "Board successfully retrieved")
    @GetMapping("/board/text")
    public ResponseEntity<String> getBoardAsText(
            @Parameter(description = "UUID of the game")
            @PathVariable("gameId") UUID gameId) {
        return ResponseEntity.ok(gameService.getBoard(gameId).toString());
    }

    @Operation(summary = "Get current player", description = "Whose turn is it?")
    @ApiResponse(responseCode = "200", description = "Player color returned (BLACK / WHITE)")
    @GetMapping("/player")
    public ResponseEntity<SimpleResponse> getCurrentPlayer(
            @Parameter(description = "UUID of the game")
            @PathVariable("gameId") UUID gameId) {
        String player = gameService.getCurrentPlayer(gameId);
        return ResponseEntity.ok(new SimpleResponse(player));
    }

    @Operation(summary = "Make a move", description = "Attempt a move in this game")
    @ApiResponse(responseCode = "200", description = "Move applied or rejected")
    @ApiResponse(responseCode = "400", description = "Invalid move or malformed request")
    @PostMapping("/move")
    public ResponseEntity<MoveResponse> makeMove(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Move to apply", required = true
            )
            @Validated @RequestBody MoveRequest moveRequest,
            @Parameter(description = "UUID of the game")
            @PathVariable("gameId") UUID gameId) {
        var result = gameService.makeMove(moveRequest.toDomain(), gameId);
        return ResponseEntity.ok(new MoveResponse(result.successful(), result.message()));
    }

    @Operation(summary = "Reset game", description = "Start the game over")
    @ApiResponse(responseCode = "200", description = "Game reset")
    @PostMapping("/reset")
    public ResponseEntity<SimpleResponse> reset(
            @Parameter(description = "UUID of the game")
            @PathVariable("gameId") UUID gameId) {
        gameService.resetGame(gameId);
        return ResponseEntity.ok(new SimpleResponse("Game reset successfully"));
    }

}
