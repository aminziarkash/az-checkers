package com.az.software.checkers.dto;

import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.Position;
import jakarta.validation.constraints.NotNull;

// TODO, change to record
public class MoveRequest {

    @NotNull
    private Position from;

    @NotNull
    private Position to;

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public Move toDomain() {
        return new Move(from, to);
    }
}
