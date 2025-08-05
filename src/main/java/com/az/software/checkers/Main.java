package com.az.software.checkers;

import com.az.software.checkers.engine.GameEngine;
import com.az.software.checkers.model.Position;

public class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        engine.printBoard();

        System.out.println("Move 1: Valid forward move by BLACK");
        engine.move(new Position(2, 1), new Position(3, 2)); // OK

        System.out.println("Move 2: Invalid move by WHITE (not diagonal)");
        engine.move(new Position(5, 0), new Position(4, 0)); // INVALID

        System.out.println("Move 3: Valid move by WHITE");
        engine.move(new Position(5, 0), new Position(4, 1)); // OK

        engine.printBoard();
    }
}