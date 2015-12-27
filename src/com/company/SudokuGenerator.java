package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.company.TailCalls.call;

/**
 * Created by WinNabuska on 22.12.2015.
 */
public class SudokuGenerator {

    private static Random random = new Random();
    private static final int DEFAULT_HINT_NUMBERS = 21;
    private static final int DEFAULT_BLOCK_SIDE_LENGTH = 3;

    /**Generates a random sudoku board with the given BLOCK_WIDTH
     * In a usual Sudoku game BLOCK_WIDTH is 3 and the grid values are 1-9
     * Forexample if a BLOCK_WIDTH is 4 the values are 1-16, if 2 the values are 1-4 etc.*/
    /*public static List<int [][]> generate(final int BLOCK_WIDTH){
        int BOARD_SIDE_LENGTH = BLOCK_WIDTH*BLOCK_WIDTH;
        int [][] board = new int [BOARD_SIDE_LENGTH][BOARD_SIDE_LENGTH];
        for(int[] row : board)
            Arrays.fill(row, 0);
        Sudoku sudoku = new Sudoku(Sudoku.EMPTY_GRID);
        List<int[]> unUsedEmptys = Sudoku.allCoords(BLOCK_WIDTH);
        placeDefaultHintNumbers(sudoku,unUsedEmptys, BLOCK_WIDTH*BLOCK_WIDTH*BLOCK_WIDTH/2*3);
        int [][] solution = generateHelper(sudoku, Sudoku.allCoords(BLOCK_WIDTH)).invoke();
        return Arrays.asList(board,solution);
    }

    private static void placeDefaultHintNumbers(Sudoku sudoku, List<int[]> unUsedEmptys, int missingHints){
        int[] validValues;
        int[] coord;
        while(missingHints>0){
            do {
                int boardIndex = (int) (unUsedEmptys.size() * random.nextDouble());
                coord = unUsedEmptys.remove(boardIndex);
                validValues = sudoku.validValues(coord[1], coord[0]).toArray();
            } while (validValues.length == 0);
            sudoku.setValue(coord[1],coord[0], validValues[(int) (validValues.length * random.nextDouble()));
        }
    }

    private static TailCall<int[][]> generateHelper(Sudoku sudoku, List<int[]> unUsedCoords){
        try {
                List<int[][]> solutions = sudoku.findFirstSolutions(2);
                if (solutions.isEmpty()) {
                    List<int[]> valuedCoords = Sudoku.allCoords()SudokuSolver.getAllCoords(board).stream().filter(c -> board[c[1]][c[0]] != 0).collect(Collectors.toList());
                    int[] randomCoord = valuedCoords.get((int) (valuedCoords.size() * random.nextDouble()));
                    board[randomCoord[1]][randomCoord[0]] = 0;
                    unUsedCoords.add(randomCoord);
                    return call(() -> generateHelper(board, unUsedCoords, 0));
                } else if (solutions.size() > 1) {
                    System.out.println("too many");
                    int[][] randomSolution = solutions.get((int) (solutions.size() * random.nextDouble()));
                    int[] nextCoord = unUsedCoords.remove((int) (random.nextDouble() * unUsedCoords.size()));
                    board[nextCoord[1]][nextCoord[0]] = randomSolution[nextCoord[1]][nextCoord[0]];
                    return call(() -> generateHelper(board, unUsedCoords, 0));
                } else {//solutions.size() == 1
                    Sudoku.printBoard(board);
                    return TailCalls.done(solutions.get(0));
                }

        }catch (Exception e){
            Sudoku.printBoard(board);
            System.out.println("UNUSED_COORDS ");
            Stream.of(unUsedCoords).forEach(System.out::println);
            System.out.println("unset min hint number = " +hintNumbers);
            e.printStackTrace();
            throw new Error("sudokuGenerator error");
        }
    }*/

}
