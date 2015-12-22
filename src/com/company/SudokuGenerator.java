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

    /**Generates a random sudoku board with the given BLOCK_SIDE_LENGTH
     * In a usual Sudoku game BLOCK_SIDE_LENGTH is 3 and the grid values are 1-9
     * Forexample if a BLOCK_SIDE_LENGTH is 4 the values are 1-16, if 2 the values are 1-4 etc.*/
    public static int [][] generate(final int BLOCK_SIDE_LENGTH){
        int BOARD_SIDE_LENGTH = BLOCK_SIDE_LENGTH*BLOCK_SIDE_LENGTH;
        int [][] board = new int [BOARD_SIDE_LENGTH][BOARD_SIDE_LENGTH];
        for(int[] row : board)
            Arrays.fill(row, 0);
        /*Integer [][] board = IntStream.range(0, SIDE_LENGTH*SIDE_LENGTH)
                .boxed().map(i -> IntStream.range(0, SIDE_LENGTH*SIDE_LENGTH)
                        .boxed().map(j -> 0)
                        .toArray(Integer[]::new))
                .toArray(Integer[][]::new);*/
        int hintNumbers = (int) Math.round(1.0*DEFAULT_HINT_NUMBERS/(DEFAULT_BLOCK_SIDE_LENGTH*DEFAULT_BLOCK_SIDE_LENGTH)*BLOCK_SIDE_LENGTH*BLOCK_SIDE_LENGTH);
        SudokuSolver.init(board);
        generateHelper(board, SudokuSolver.allCoords.stream().collect(Collectors.toList()), hintNumbers).invoke();
        return board;
    }

    //TODO tutustu tailCalliin
    private static TailCall<Void> generateHelper(int[][] board, List<int[]> unUsedCoords, int missingMinimumHintNumbers){
        try {
            if (missingMinimumHintNumbers != 0) {
                int[] validValues;
                int[] coord;
                do {
                    int boardIndex = (int) (unUsedCoords.size() * random.nextDouble());
                    coord = unUsedCoords.remove(boardIndex);
                    validValues = SudokuSolver.validValueFor(board, coord[1], coord[0]).toArray();
                } while (validValues.length == 0);
                board[coord[0]][coord[1]] = validValues[(int) (validValues.length * random.nextDouble())];
                return call(() -> generateHelper(board, unUsedCoords, missingMinimumHintNumbers - 1));
            } else {
                //SudokuSolver.printBoard(board);
                System.out.println("else");
                List<int[][]> solutions = SudokuSolver.findFirstSolutions(board, 2);
                System.out.println("solutions size = " + solutions.size());
                int value = Arrays.stream(board).mapToInt(row -> (int) Arrays.stream(row).filter(v -> v > 0).count()).sum();
                System.out.println("filled values " + value);
                if (solutions.isEmpty()) {
                    SudokuSolver.printBoard(board);
                    System.out.println("else ... if");
                    List<int[]> valuedCoords = SudokuSolver.allCoords.stream().filter(c -> board[c[1]][c[0]] != 0).collect(Collectors.toList());
                    int[] randomCoord = valuedCoords.get((int) (valuedCoords.size() * random.nextDouble()));
                    board[randomCoord[1]][randomCoord[0]] = 0;//TODO tarkista kummin päin indeksit menee
                    unUsedCoords.add(randomCoord);
                    return call(() -> generateHelper(board, unUsedCoords, 0));
                } else if (solutions.size() > 1) {
                    System.out.println("else ... else if");
                    int[][] randomSolution = solutions.get((int) (solutions.size() * random.nextDouble()));
                    int[] nextCoord = unUsedCoords.remove((int) (random.nextDouble() * unUsedCoords.size()));
                    board[nextCoord[1]][nextCoord[0]] = randomSolution[nextCoord[1]][nextCoord[0]];
                    return call(() -> generateHelper(board, unUsedCoords, 0));
                } else {//solutions.size() == 1
                    System.out.println("else ... else");
                    SudokuSolver.printBoard(board);
                    return TailCalls.done(null);
                }
            }
        }catch (Exception e){
            SudokuSolver.printBoard(board);
            System.out.println("UNUSED_COORDS ");
            Stream.of(unUsedCoords).forEach(System.out::println);
            System.out.println("unset min hint number = " +missingMinimumHintNumbers);
            e.printStackTrace();
            throw new Error("sudokuGenerator error");
        }
    }

}
