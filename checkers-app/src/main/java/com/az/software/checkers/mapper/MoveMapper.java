package com.az.software.checkers.mapper;

import com.az.software.checkers.domain.model.Move;
import com.az.software.checkers.dto.MoveRequest;

public interface MoveMapper {

    /**
     * Convert this MoveRequest DTO into the domain Move object.
     */
    static Move toDomain(MoveRequest moveRequest) {
        return new Move(moveRequest.from(), moveRequest.to());
    }

}
