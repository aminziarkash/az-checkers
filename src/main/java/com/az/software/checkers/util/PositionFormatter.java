package com.az.software.checkers.util;

public class PositionFormatter {

    public static String format(int row, int col) {
        // Convert 0-based column index to letter (A-H)
        char colLetter = (char) ('A' + col);

        // Convert 0-based row index to 1-based row number (assuming row 0 is bottom)
        int rowNumber = 8 - row; // flip the board vertically for human readability

        return String.format("(%s,%d)", colLetter, rowNumber);
    }

}
