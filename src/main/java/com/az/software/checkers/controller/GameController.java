package com.az.software.checkers.controller;

import com.az.software.checkers.engine.GameEngine;
import com.az.software.checkers.model.Position;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameEngine engine = new GameEngine();

    @GetMapping("/board")
    public String getBoard() {
        return engine.getBoard().toString();
    }

    @GetMapping("/player")
    public String getPlayer() {
        return "Current turn: " + engine.getCurrentPlayer();
    }

    @PostMapping("/move")
    public String makeMove(@RequestBody Map<String, String> payload) {
        String fromStr = payload.get("from");
        String toStr = payload.get("to");

        Position from = Position.fromAlgebraic(fromStr);
        Position to = Position.fromAlgebraic(toStr);

        engine.move(from, to);

        return getBoard();
    }

    @PostMapping("/reset")
    public void reset() {
        engine.reset();
    }

}
