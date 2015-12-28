package joona.enbuska.Logic;

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
import static java.util.stream.Collectors.toSet;


/**
 * Soduku solves sudokus in a functional way (findSolutionsHelper). It also has some handy util functions
 * */


public class SudokuSolver {

    public final int GRID_SIZE;     //standard GRID_SIZE for a 1-9 sudoku is 9
    public final int BLOCK_WIDTH;   //standard BLOCK_WIDTH for a 1-9 sudoku is 3
    public final int [] GRID_RANGE; //standard GRID_RANGE for a 1-9 sudoku is 0..8
    public final int [] ALL_VALUES; //standard ALL_VALUES for a 1-9 sudoku is 1..9
    public final int [][] grid;     //see class Sudoku static variables to see examples
    public Point[] emptyPoints;

    private final Point [][][] BLOCK_COORDS;

    /**After constructed, grid values should not be changed. Instead call method 'changeGridValue(x, y, newValue)' to change a value*/
    public SudokuSolver(int [][] grid){
        this.grid = grid;
        GRID_SIZE = grid.length;
        GRID_RANGE = IntStream.range(0,GRID_SIZE).toArray();
        ALL_VALUES = IntStream.rangeClosed(1,GRID_SIZE).toArray();

        BLOCK_WIDTH = (int) Math.round(Math.sqrt(GRID_SIZE));

        BiFunction<Integer, Integer, Stream<Point>> blockCoordsAt =
                (px, py) -> {
                    int y_from = (py / BLOCK_WIDTH) * BLOCK_WIDTH;
                    int x_from = (px / BLOCK_WIDTH) * BLOCK_WIDTH;
                    return IntStream.range(y_from, y_from + BLOCK_WIDTH).boxed()
                            .flatMap(y -> IntStream.range(x_from, x_from + BLOCK_WIDTH).boxed()
                                    .map(x -> new Point(x,y)));
                };

        BLOCK_COORDS = new Point[BLOCK_WIDTH][BLOCK_WIDTH][GRID_SIZE];

        for (int y = 0; y < GRID_SIZE; y+= BLOCK_WIDTH)
            for (int x = 0; x < GRID_SIZE; x+= BLOCK_WIDTH)
                BLOCK_COORDS[y/BLOCK_WIDTH][x/BLOCK_WIDTH] =
                        blockCoordsAt.apply(x,y).collect(toList())
                                .toArray(new Point[GRID_SIZE]);

        emptyPoints = getEmptyPoints();
    }

    /**this is the only way a grid value should be changed AFTER SudokuSolver has been constructed*/
    public void changeGridValue(int x, int y, int value){
        grid[y][x] = value;
        emptyPoints = getEmptyPoints();
    }

    /**Returs all the points that have a non 0 value in them*/
    public static List<Point> getValuedPoints(int [][] grid){
        return IntStream.range(0, grid.length).boxed()
                .flatMap(y -> IntStream.range(0, grid.length)
                        .mapToObj(x -> new Point(x,y)))
                .filter(p -> grid[p.y][p.x]!=0).collect(Collectors.toList());
    }

    /**Checks that the grid has no same places several times on a single row or column or block*/
    public static boolean checkIsLegit(int [][] grid) {
        int gridSize = grid.length;
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
        return IntStream.range(0, gridSize).allMatch(y ->
                IntStream.range(0, gridSize)
                        .allMatch(x -> legitBlock.test(x,y) && legitRow.test(y) && legitColumn.test(x)));
    }

    public Set<Integer> columnValues(int x){
        return columnValues(grid, x).boxed().collect(toSet());
    }

    public Set<Integer> rowValues(int y){
        return rowValues(grid, y).boxed().collect(toSet());
    }

    public Set<Integer> blockValuesAt(int x, int y){
        return blockValuesAt(grid, x, y).boxed().collect(toSet());
    }

    private IntStream blockValuesAt(int [][] grid, int x, int y){
        return  Arrays.stream(BLOCK_COORDS[y/BLOCK_WIDTH][x/BLOCK_WIDTH])
                .mapToInt(p -> grid[p.y][p.x]);
    }

    public List<Integer> validValueFor(int x, int y){
        return validValueFor(grid, x,y).boxed().collect(toList());
    }

    public List<int[][]> findAllSolutions(){
        return findSolutionsHelper(grid, 0).collect(Collectors.toList());
    }

    public List<int [][]> findFirstSolutions(int limit){
        return findSolutionsHelper(grid, 0).limit(limit).collect(Collectors.toList());
    }

    /**finds all the solutions for the given grid, currentEmptyIndex must be initialized to 0*/
    private Stream<int [][]> findSolutionsHelper(int [][] grid, int currentEmptyIndex){
        if (currentEmptyIndex < emptyPoints.length) {
            Point ep = emptyPoints[currentEmptyIndex];
            return validValueFor(grid, ep.x, ep.y)
                    .parallel()
                    .boxed()
                    .flatMap(v ->
                            findSolutionsHelper(cloneAndSet(grid, ep.x, ep.y, v), 1 + currentEmptyIndex));
        } else {
            int[][][] wrapped = {grid};
            return Stream.of(wrapped);
        }
    }

    /**produces a new array that has no attachments to the original array. Changes the value on the given x, y point an return the newly created array*/
    protected int [][] cloneAndSet(int [][] grid, int x, final int y, int value){
        int [][] clone = new int[GRID_SIZE][GRID_SIZE];
        IntStream.of(GRID_RANGE).forEach(i -> System.arraycopy(grid[i],0, clone[i],0, GRID_SIZE));
        clone[y][x] = value;
        return clone;
    }

    private IntStream rowValues(int [][] grid, int y){
        return IntStream.of(grid[y]);
    }

    private IntStream columnValues(int [][] grid, int x){
        return  Stream.of(grid).mapToInt(row -> row[x]);
    }

    /**is used by findSolutionsHelper*/
    private IntStream validValueFor(int [][] grid, int x, int y){
        boolean[] unused;
        Arrays.fill(unused=new boolean[GRID_SIZE+1], true);
        usedValues(grid, x, y).forEach(i -> unused[i]=false);
        return IntStream.of(ALL_VALUES).filter(i -> unused[i]);
    }

    /**returns a non distinct stream of all used values, possible including empty values (zeroes)*/
    private IntStream usedValues(int [][] grid, int x, int y){
        return IntStream.concat(columnValues(grid, x),
                IntStream.concat(blockValuesAt(grid, x, y), rowValues(grid, y)));
    }

    /**return all the points that have a value 0 (representing empty) in them*/
    private Point[] getEmptyPoints(){
        List<Point> holder = IntStream.range(0, GRID_SIZE).boxed()
                .flatMap(y -> IntStream.range(0, GRID_SIZE)
                        .mapToObj(x -> new Point(x,y)))
                .filter(p -> grid[p.y][p.x]==0).collect(Collectors.toList());
        return holder.toArray(new Point[holder.size()]);
    }
}