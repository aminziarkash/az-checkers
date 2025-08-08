package com.az.software.checkers.dto;

import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.domain.model.Position;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for a move request. Immutable record with validation annotations.
 */
public record MoveRequest(
        @NotNull Position from,
        @NotNull Position to
) {
    /**
     * Convert this DTO into the domain Move object.
     */
    public Move toDomain() {
        return new Move(from, to);
    }
}
