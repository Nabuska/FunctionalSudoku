package com.company;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

public class SudokuSolver {

    public final int GRID_SIZE;
    public final int BLOCK_WIDTH;
    public final int [] GRID_RANGE;
    public final int [] ALL_VALUES;
    public final int [][] GRID;
    public final int [][] EMPTY_COORDS;
    public final int [][][][] BLOCK_COORDS;


    public SudokuSolver(int [][] grid){
        this.GRID = grid;
        GRID_SIZE = grid.length;
        GRID_RANGE = IntStream.range(0,GRID_SIZE).toArray();
        ALL_VALUES = IntStream.rangeClosed(1,GRID_SIZE).toArray();

        BLOCK_WIDTH = (int) Math.round(Math.sqrt(GRID_SIZE));

        BiFunction<Integer, Integer, Stream<int[]>> blockCoordsAt =
                (x, y) -> {
                    int y_from = (y / BLOCK_WIDTH) * BLOCK_WIDTH;
                    int x_from = (x / BLOCK_WIDTH) * BLOCK_WIDTH;
                    return IntStream.range(y_from, y_from + BLOCK_WIDTH).boxed()
                            .flatMap(i -> IntStream.range(x_from, x_from + BLOCK_WIDTH).boxed()
                                    .map(j -> new int[]{i, j}));
                };

        BLOCK_COORDS = new int[BLOCK_WIDTH][BLOCK_WIDTH][GRID_SIZE][2];

        for (int i = 0; i < GRID_SIZE; i+= BLOCK_WIDTH)
            for (int j = 0; j < GRID_SIZE; j+= BLOCK_WIDTH)
                BLOCK_COORDS[i/BLOCK_WIDTH][j/BLOCK_WIDTH] =
                        blockCoordsAt.apply(i,j).collect(toList())
                                .toArray(new int [GRID_SIZE][2]);

        List<int []> holder = IntStream.range(0, GRID_SIZE).boxed()
                .flatMap(i -> IntStream.range(0, GRID_SIZE)
                        .mapToObj(j -> new int[]{i,j}))
                .filter(v -> grid[v[0]][v[1]]==0).collect(Collectors.toList());
        holder.add(0, new int[]{0,0});
        EMPTY_COORDS = holder.toArray(new int[holder.size()][2]);
    }

    public List<int[][]> findAllSolutions(){
        return solveHelper(GRID, 1).collect(Collectors.toList());
    }

    public List<int [][]> findFirstSolutions(int LIMIT){
        return solveHelper(GRID, 1).limit(LIMIT).collect(Collectors.toList());
    }

    private Stream<int [][]> solveHelper(int [][] grid, int currentEmptyIndex){
        if (currentEmptyIndex < EMPTY_COORDS.length) {
            int[]COORD = EMPTY_COORDS[currentEmptyIndex];
            return validValueFor(grid, COORD[1], COORD[0])
                    .parallel()
                    .boxed()
                    .flatMap(v ->
                            solveHelper(cloneAndSet(grid, COORD[1], COORD[0], v), 1 + currentEmptyIndex));
        } else {
            int[][][] wrapped = {grid};
            return Stream.of(wrapped);
        }
    }

    public static int[][] getAllCoords(int [][] grid){
        int coordsLength = grid.length*grid.length;
        return IntStream.range(0,coordsLength)
                .boxed()
                .flatMap(i -> IntStream.range(0, coordsLength)
                        .boxed()
                        .map(j -> new int[]{i,j}))
                .collect(Collectors.toList()).toArray(new int[coordsLength][2]);
    }

    public IntStream rowValues(int [][] grid, int y){
        return IntStream.of(grid[y]);
    }

    public IntStream columnValues(int [][] grid, int x){
        return  Stream.of(grid).mapToInt(row -> row[x]);
    }

    public IntStream blockValuesAt(int [][] grid, int x, int y){
        return  Arrays.stream(BLOCK_COORDS[y/BLOCK_WIDTH][x/BLOCK_WIDTH])
                .mapToInt(c -> grid[c[1]][c[0]]);
    }

    private IntStream validValueFor(int [][] grid, int x, int y){
        boolean[] unused;
        Arrays.fill(unused=new boolean[GRID_SIZE+1], true);
        usedValues(grid, x, y).forEach(i -> unused[i]=false);
        return IntStream.of(ALL_VALUES).filter(i -> unused[i]);
    }

    /*returns a non distinct stream of all used values, possible including empty values (zeroes)*/
    private IntStream usedValues(int [][] grid, int X, int Y){
        return IntStream.concat(columnValues(grid, X),
                IntStream.concat(blockValuesAt(grid, X, Y), rowValues(grid, Y)));
    }

    public boolean checkIsLegit(int [][] grid) {
        SudokuSolver solver = new SudokuSolver(grid);
        Predicate<Integer> legitRow = y -> {
            List<Integer> rowValues = solver.rowValues(grid, y).filter(i -> i!=0).boxed().collect(Collectors.toList());
            return rowValues.size()==rowValues.stream().distinct().count();
        };
        Predicate<Integer> legitColumn = x -> {
            List<Integer> columnValues = solver.columnValues(grid, x).filter(i -> i!=0).boxed().collect(Collectors.toList());
            return columnValues.size()==columnValues.stream().distinct().count();
        };
        BiPredicate<Integer, Integer> legitBlock = (x, y) -> {
            List<Integer> rowValues = solver.blockValuesAt(grid, x, y).filter(i -> i!=0).boxed().collect(Collectors.toList());
            return rowValues.size()==rowValues.stream().distinct().count();
        };
        return IntStream.range(0, GRID_SIZE).allMatch(y ->
                IntStream.range(0, GRID_SIZE)
                        .allMatch(x -> legitBlock.test(x,y) && legitRow.test(y) && legitColumn.test(x)));
    }

    private int [][] cloneAndSet(int [][] grid, int x, final int y, int value){
        int [][] clone = new int[GRID_SIZE][GRID_SIZE];
        IntStream.of(GRID_RANGE).forEach(i -> System.arraycopy(grid[i],0, clone[i],0, GRID_SIZE));
        clone[y][x] = value;
        return clone;
    }
}