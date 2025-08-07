package com.az.software.checkers.mapper;

import com.az.software.checkers.domain.model.Board;
import com.az.software.checkers.domain.model.Piece;

import java.util.ArrayList;
import java.util.List;

public interface BoardMapper {

    static List<List<String>> toDto(Board board) {
        List<List<String>> rows = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            List<String> cols = new ArrayList<>();
            for (int c = 0; c < 8; c++) {
                Piece piece = board.getPiece(r, c);
                cols.add(piece == null ? "" : piece.toString());
            }
            rows.add(cols);
        }
        return rows;
    }

}
