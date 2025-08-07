package com.az.software.checkers.domain.model;

/**
 * Simple DTO to convey whether a move succeeded
 * and an optional human‐readable message.
 */
public record MoveResult(boolean successful, String message) {
}
