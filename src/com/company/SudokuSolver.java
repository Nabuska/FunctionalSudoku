package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SudokuSolver {

    static List<int []> allCoords;
    static int [] allValues = {1,2,3,4,5,6,7,8,9};
    static int [] gridRange = {0,1,2,3,4,5,6,7,8};
    static int blockHeight = 3;

    static IntStream blockValuesAt(int [][] BOARD, int X, int Y){
        final int Y_RANGE_START = (Y/blockHeight)*blockHeight, X_RANGE_START = (X/blockHeight)*blockHeight;
        return IntStream.range(Y_RANGE_START,(Y_RANGE_START+blockHeight))
                .flatMap(y -> IntStream.range(X_RANGE_START,(X_RANGE_START+blockHeight))
                        .map(x -> BOARD[y][x]));
    }

    static IntStream rowValues(int [][] BOARD, int Y){
        return IntStream.of(BOARD[Y]);
    }

    static IntStream columnValues(int [][] BOARD, int X){
        return Stream.of(BOARD).mapToInt(row -> row[X]);
    }

    static void init(int [][] BOARD){
        int maxValue = BOARD.length;
        allCoords = IntStream.range(0,BOARD.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, BOARD.length)
                        .boxed()
                        .map(j -> new int[]{i,j})).collect(Collectors.toList());
        gridRange = IntStream.range(0,maxValue).toArray();
        allValues = IntStream.rangeClosed(1,maxValue).toArray();
        blockHeight = (int) Math.round(Math.sqrt(BOARD.length));
    }

    static Optional<int [][]> findOneSolution(int [][] BOARD){
        init(BOARD);
        return solveHelper(BOARD).findAny();
    }

    static List<int[][]> findAllSolutions(int [][] BOARD){
        init(BOARD);
        return solveHelper(BOARD).collect(Collectors.toList());
    }

    static Stream<int [][]> solveHelper(int [][] BOARD){
        final Optional<int []> firstEmpty = firstEmpty(BOARD);
        if(firstEmpty.isPresent()){
            int [] COORD = firstEmpty.get();
            return validValueFor(BOARD, COORD[1], COORD[0])
                    .parallel()
                    .boxed()
                    .flatMap(v -> solveHelper(cloneAndSet(BOARD, COORD[1], COORD[0], v)));
        }
        else{
            int [][][] wrapped =  {BOARD};
            return Stream.of(wrapped);
        }
    }

    static int [][] cloneAndSet(int [][] BOARD, int X, final int Y, int VALUE){
        int [][] CLONE = new int[BOARD.length][BOARD.length];
        IntStream.of(gridRange).forEach(i -> System.arraycopy(BOARD[i],0, CLONE[i],0, BOARD.length));
        CLONE[Y][X] = VALUE;
        return CLONE;
    }

    static IntStream validValueFor(int [][] BOARD, int X, int Y){
        Set<Integer> invalidValues = usedValues(BOARD, X, Y).boxed().collect(Collectors.toSet());
        return IntStream.of(allValues).filter(i -> !invalidValues.contains(i));
    }

    private static IntStream usedValues(int [][] BOARD, int X, int Y){
        return IntStream.concat(columnValues(BOARD, X),
                IntStream.concat(blockValuesAt(BOARD, X, Y), rowValues(BOARD, Y)))
                .filter(i -> i!=0);
    }

    private static Optional<int[]> firstEmpty(int[][] BOARD) {
        return allCoords.stream().filter(v -> BOARD[v[0]][v[1]]==0).findFirst();
    }

    static void printBoard(int [][] BOARD){
        for (int i = 0; i < BOARD.length; i++) {
            if(i%blockHeight==0){
                System.out.print("  ");
                for(int j = 0; j<blockHeight; j++)
                    for(int k = 0; k<blockHeight; k++)
                        System.out.print("----");
                System.out.println();
            }
            for (int j = 0; j < BOARD[i].length; j++) {
                if(j%blockHeight==0)
                    System.out.print(" | ");
                else
                    System.out.print("   ");
                System.out.print(BOARD[i][j]);
            }
            System.out.println(" |");
        }
        System.out.println("  -----------------------------------");
    }

}
