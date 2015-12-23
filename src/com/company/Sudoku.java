package com.company;

/**
 * Created by WinNabuska on 22.12.2015.
 */
public class Sudoku {

    private static int X = 0;

    static final int [][] UNSOLVED_EXAMPLE_BOARD = {
            {5, 3, X, X, 7, X, X, X, X},
            {6, X, X, 1, 9, 5, X, X, X},
            {X, 9, 8, X, X, X, X, 6, X},
            {8, X, X, X, 6, X, X, X, 3},
            {4, X, X, 8, X, 3, X, X, 1},
            {7, X, X, X, 2, X, X, X, 6},
            {X, 6, X, X, X, X, 2, 8, X},
            {X, X, X, 4, 1, 9, X, X, 5},
            {X, X, X, X, 8, X, X, 7, 9}
    };

    static final int [][] MULTI_SOLUTION_EXAMPLE_BOARD = {
            {X, X, X, X, X, X, X, X, X},
            {X, X, X, 1, 9, 5, X, X, X},
            {X, X, 8, X, X, X, X, 6, X},
            {8, X, X, X, 6, X, X, X, X},
            {X, X, X, X, X, 3, X, X, X},
            {7, X, X, X, X, X, X, X, X},
            {X, 6, X, X, X, X, 2, 8, X},
            {X, X, X, X, X, 9, X, X, X},
            {X, X, X, X, 8, X, X, 7, X}
    };

    static final int [][] SOLVED_EXAMPLE_BOARD = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    static final int [][] WORLDS_HARDEST_BOARD = {
            {8, X, X, X, X, X, X, X, X},
            {X, X, 3, 6, X, X, X, X, X},
            {X, 7, X, X, 9, X, 2, X, X},
            {X, 5, X, X, X, 7, X, X, X},
            {X, X, X, X, 4, 5, 7, X, X},
            {X, X, X, 1, X, X, X, 3, X},
            {X, X, 1, X, X, X, X, 6, 8},
            {X, X, 8, 5, X, X, X, 1, X},
            {X, 9, X, X, X, X, 4, X, X}
    };

    static final int [][] FOUR_BY_FOUR_BLOCK = {
            {2,	X,	X,	X,	9,	3,	7,	X,	X,	X,	X,	X,	11,	10,	X,	13,},
            {X,	X,	1,	X,	X,	15,	13,	5,	14,	X,	X,	9,	X,	X,	X,	X},
            {X,	7,	X,	X,	X,	1,	X,	X,	X,	10,	4,	X,	5,	14,	3,	2},
            {X,	X,	12,	X,	10,	X,	2,	X,	X,	3,	8,	15,	X,	6,	X,	X},
            {X,	2,	X,	10,	X,	X,	X,	X,	15,	1,	7,	X,	8,	9,	X,	X},
            {X,	X,	7,	X,	3,	9,	1,	X,	X,	X,	X,	X,	X,	X,	X,	X},
            {X,	X,	6,	X,	X,	X,	10,	15,	X,	X,	X,	12,	X,	X,	X,	X},
            {13,X,	11,	X,	X,	6,	X,	2,	X,	8,	3,	X,	X,	12,	X,	X},
            {X,	X,	X,	X,	2,	X,	X,	9,	X,	15,	X,	X,	X,	X,	X,	X},
            {X,	X,	X,	13,	4,	X,	X,	X,	5,	14,	X,	X,	X,	X,	X,	X},
            {4,	X,	3,	X,	X,	X,	11,	X,	X,	7,	X,	2,	X,	X,	X,	X},
            {5,	X,	X,	X,	X,	X,	X,	6,	X,	13,	12,	X,	X,	2,	9,	X},
            {X,	3,	X,	6,	X,	10,	X,	X,	X,	X,	X,	X,	X,	X,	X,	14},
            {X,	X,	8,	X,	X,	X,	X,	3,	6,	X,	X,	4,	12,	X,	1,	X},
            {X,	X,	15,	X,	X,	13,	X,	X,	X,	X,	14,	8,	6,	X,	5,	X},
            {7,	X,	X,	X,	12,	X,	X,	8,	X,	11,	X,	10,	X,	X,	X,	3}
    };

    private Sudoku(){}
}
