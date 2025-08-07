package com.az.software.checkers.domain.model;

/**
 * A move from one Position to another.
 */
public record Move(
        Position from,
        Position to
) {}