
package com.intrusoft.sudokumaster;

public class Sudoku {

    public static int SIZE_SUDOKU = 9;
    public static int Array[][];

    public static boolean fill() {
        int row = -1, col = -1;
        ///find next empty element
        for (int i = 0; i < SIZE_SUDOKU; i++) {
            int j;
            for (j = 0; j < SIZE_SUDOKU; j++) {
                if (Array[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (j < SIZE_SUDOKU)
                break;
        }

        ////if array if filled return true
        if (row == -1 && col == -1)
            return true;
        ///// try all possible numbers at empty location
        for (int num = 1; num <= 9; num++) {

            Array[row][col] = num;
            if (legal(Array, row, col)) {
                if (fill())
                    return true;
            }

        }
        /////no possible solution for current row and col, make it empty and return false
        Array[row][col] = 0;
        return false;
    }

    public static boolean legal(int Array[][], int row, int col) {
        for (int i = 0; i < SIZE_SUDOKU; i++)     ////check if same number in row
        {
            if (i == col)
                continue;
            if (Array[row][i] == Array[row][col])
                return false;
        }
        for (int i = 0; i < SIZE_SUDOKU; i++)     ////check if same number in col
        {
            if (i == row)
                continue;
            if (Array[i][col] == Array[row][col])
                return false;
        }

        int row_0 = 0, row_1 = 0, col_0 = 0, col_1 = 0;
        switch (row) {
            case 0:
            case 1:
            case 2:
                row_0 = 0;
                row_1 = 2;
                break;
            case 3:
            case 4:
            case 5:
                row_0 = 3;
                row_1 = 5;
                break;
            case 6:
            case 7:
            case 8:
                row_0 = 6;
                row_1 = 8;
                break;
        }
        switch (col) {
            case 0:
            case 1:
            case 2:
                col_0 = 0;
                col_1 = 2;
                break;
            case 3:
            case 4:
            case 5:
                col_0 = 3;
                col_1 = 5;
                break;
            case 6:
            case 7:
            case 8:
                col_0 = 6;
                col_1 = 8;
                break;
        }

        for (int i = row_0; i <= row_1; i++)           /////check same number in a grid
            for (int j = col_0; j <= col_1; j++) {
                if (i == row && j == col)
                    continue;
                if (Array[i][j] == Array[row][col])
                    return false;
            }
        return true;
    }
}
