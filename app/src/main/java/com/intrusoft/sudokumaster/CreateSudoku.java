package com.intrusoft.sudokumaster;

import java.util.Random;

/**
 * Created by IntruSoft on 10/30/2015.
 */
public class CreateSudoku {

    int sudoku[][] = new int[9][9];
    int random[];

    public int[][] getSudoku() {
        getRandom();
        for(int i = 0;i<9;i++ )
            for(int j = 0;j<9;j++ )
                sudoku[i][j]=0;
        sudoku[0][0] = random[0];
        sudoku[0][8] = random[1];
        sudoku[8][0] = random[2];
        sudoku[8][8] = random[3];
        return sudoku;
    }

    public void getRandom() {
        Boolean status = false;
        int x[] = {0, 0, 0, 0};
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            x[i] = r.nextInt(9) + 1;
        }
        for (int i = 0; i < 4; i++) {
            int j;
            for (j = 0; j < 4; j++) {
                if (i != j)
                    if (x[i] == x[j]) {
                        status = true;
                        break;
                    }
            }
            if (j < 4) {
                break;
            }
        }
        if (status)
            getRandom();
        else
            random = x;
    }
}
