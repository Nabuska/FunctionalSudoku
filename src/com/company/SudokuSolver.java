package com.company;

import com.sun.xml.internal.ws.api.server.InstanceResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SudokuSolver {

    private static List<int []> allCoords;
    private static int [] allValues = {1,2,3,4,5,6,7,8,9};
    private static int [] gridRange = {0,1,2,3,4,5,6,7,8};
    private static int blockHeight = 3;

    static List<int[]> getAllCoords(int [][] BOARD){
        return IntStream.range(0,BOARD.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, BOARD.length)
                        .boxed()
                        .map(j -> new int[]{i,j})).collect(Collectors.toList());
    }

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
        allCoords = getAllCoords(BOARD);
        gridRange = IntStream.range(0,maxValue).toArray();
        allValues = IntStream.rangeClosed(1,maxValue).toArray();
        blockHeight = (int) Math.round(Math.sqrt(BOARD.length));
    }

    static List<int [][]> findFirstSolutions(int [][] BOARD, int LIMIT){
        init(BOARD);
        return solveHelper(BOARD, getEmptys(BOARD), 0).limit(LIMIT).peek(i -> System.out.println("found 1")).collect(Collectors.toList());
    }

    static List<int[][]> findAllSolutions(int [][] BOARD){
        init(BOARD);
        return solveHelper(BOARD, getEmptys(BOARD), 0).collect(Collectors.toList());
    }

    static Stream<int [][]> solveHelper(int [][] BOARD, int [][] EMPTYS, int currentEmptyIndex){

        if (currentEmptyIndex < EMPTYS.length) {
            int[] COORD = EMPTYS[currentEmptyIndex];
            return validValueFor(BOARD, COORD[1], COORD[0])
                    .parallel()
                    .boxed()
                    .flatMap(v -> solveHelper(cloneAndSet(BOARD, COORD[1], COORD[0], v), EMPTYS, 1 + currentEmptyIndex));
        } else {
            int[][][] wrapped = {BOARD};
            return Stream.of(wrapped);
        }
    }

    static int [][] cloneAndSet(int [][] BOARD, int X, final int Y, int VALUE){
        int [][] CLONE = new int[BOARD.length][BOARD.length];
        IntStream.of(gridRange).forEach(i -> System.arraycopy(BOARD[i],0, CLONE[i],0, BOARD.length));
        CLONE[Y][X] = VALUE;//TODO come up with a functional solution ???
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

    private static int[][] getEmptys(int [][]BOARD){
        List<int []> holder = allCoords.stream().filter(v -> BOARD[v[0]][v[1]]==0).collect(Collectors.toList());
        return holder.toArray(new int[holder.size()][2]);
    }

    public static boolean checkIsLegit(int [][]BOARD) {
        Predicate<Integer> legitRow = y -> {
            List<Integer> rowValues = rowValues(BOARD, y).filter(i -> i!=0).boxed().collect(Collectors.toList());
            return rowValues.size()==rowValues.stream().distinct().count();
        };
        Predicate<Integer> legitColumn = x -> {
            List<Integer> columnValues = columnValues(BOARD, x).filter(i -> i!=0).boxed().collect(Collectors.toList());
            return columnValues.size()==columnValues.stream().distinct().count();
        };
        BiPredicate<Integer, Integer> legitBlock = (x,y) -> {
            List<Integer> rowValues = blockValuesAt(BOARD, x, y).filter(i -> i!=0).boxed().collect(Collectors.toList());
            return rowValues.size()==rowValues.stream().distinct().count();
        };
        return IntStream.range(0, BOARD.length).allMatch(y ->
                IntStream.range(0, BOARD.length)
                        .allMatch(x -> legitBlock.test(x,y) && legitRow.test(y) && legitColumn.test(x)));
    }
}
