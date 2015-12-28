package joona.enbuska.Logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Created by WinNabuska on 21.12.2015.
 */
public class SudokuSolverTest {

    static int [][] unsolvedBoard;

    static int [][] solvedBoard;

    static int [] allValues;

    @org.junit.Before
    public void setUp() throws Exception {
        unsolvedBoard = new int[][]{
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        solvedBoard = new int[][]{
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

        allValues = new int[] {1,2,3,4,5,6,7,8,9};
    }

    @org.junit.Test
    public void testBlockValuesAt() throws Exception {
        SudokuSolver solver = new SudokuSolver(unsolvedBoard);

        Set<Integer> assertedValues = new HashSet<>(Arrays.asList(0,3,5,6,8,9));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(0,0));

        assertedValues = new HashSet<>(Arrays.asList(0,1,5,7,9));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(3,0));

        assertedValues = new HashSet<>(Arrays.asList(0,6));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(7,0));

        assertedValues = new HashSet<>(Arrays.asList(0,4,7,8));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(0,5));

        assertedValues = new HashSet<>(Arrays.asList(0,2,3,6,8));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(4,4));

        assertedValues = new HashSet<>(Arrays.asList(0,1,3,6));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(8,4));

        assertedValues = new HashSet<>(Arrays.asList(0,6));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(0,6));

        assertedValues = new HashSet<>(Arrays.asList(0,1,4,8,9));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(4,7));

        assertedValues = new HashSet<>(Arrays.asList(0,2,5,7,8,9));
        Assert.assertEquals(assertedValues, solver.blockValuesAt(8,7));
    }


    @Test
    public void testValidValueFor() throws Exception {

        SudokuSolver solver = new SudokuSolver(unsolvedBoard);

        List<Integer> assertedValues = Arrays.asList(2,4,8);
        List<Integer> actualValues = solver.validValueFor(8,0);
        Assert.assertEquals(assertedValues,actualValues);


        assertedValues = Arrays.asList(2, 4, 7);
        actualValues = solver.validValueFor(1,1);
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = Arrays.asList(5,7,9);
        actualValues = solver.validValueFor(3,3);
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = Arrays.asList(4,5);
        actualValues = solver.validValueFor(4,5);
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = Arrays.asList(2,3,5,6);
        actualValues = solver.validValueFor(3,8);
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = Arrays.asList(1,2,3,4,5);
        actualValues = solver.validValueFor(2,8);
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = Arrays.asList(3,6);
        actualValues = solver.validValueFor(6,7);
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = Arrays.asList(5,7,9);
        actualValues = solver.validValueFor(6,4);
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = Arrays.asList(1,3,4,5,7);
        actualValues = solver.validValueFor(6,2);
        Assert.assertEquals(assertedValues,actualValues);
    }


    @org.junit.Test
    public void testRowValues() throws Exception {
        SudokuSolver solver = new SudokuSolver(unsolvedBoard);
        Set<Integer> actualValues =  solver.rowValues( 0) ;
        Set<Integer> assertedValues = new HashSet<>(Arrays.asList(5,3,0,7));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(1) ;
        assertedValues = new HashSet<>(Arrays.asList(6,0,1,9,5));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(2) ;
        assertedValues = new HashSet<>(Arrays.asList(0,9,8,6));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(3) ;
        assertedValues = new HashSet<>(Arrays.asList(0,8,6,3));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(4) ;
        assertedValues = new HashSet<>(Arrays.asList(4,0,8,3,1));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(5) ;
        assertedValues = new HashSet<>(Arrays.asList(7,0,2,6));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(6) ;
        assertedValues = new HashSet<>(Arrays.asList(0,6,2,8));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(7) ;
        assertedValues = new HashSet<>(Arrays.asList(0,4,1,9,5));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues =  solver.rowValues(8) ;
        assertedValues = new HashSet<>(Arrays.asList(0,8,7,9));
        Assert.assertEquals(assertedValues,actualValues);
    }

    @org.junit.Test
    public void testColumnValues() throws Exception {
        SudokuSolver solver = new SudokuSolver(unsolvedBoard);
        Set<Integer> actualValues = solver.columnValues(0);
        Set<Integer> assertedValues = new HashSet<>(Arrays.asList(5,6,8,4,7,0));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(1);
        assertedValues = new HashSet<>(Arrays.asList(3,9,0,6));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(2);
        assertedValues = new HashSet<>(Arrays.asList(0,8));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(3);
        assertedValues = new HashSet<>(Arrays.asList(0,1,4,8));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(4);
        assertedValues = new HashSet<>(Arrays.asList(7,9,1,6,2,0,8));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(5);
        assertedValues = new HashSet<>(Arrays.asList(0,5,3,9));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(6);
        assertedValues = new HashSet<>(Arrays.asList(0,2));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(7);
        assertedValues = new HashSet<>(Arrays.asList(0,6,8,7));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = solver.columnValues(8);
        assertedValues = new HashSet<>(Arrays.asList(0,1,3,6,5,9));
        Assert.assertEquals(assertedValues,actualValues);
    }

    @Test
    public void testCloneAndSet() throws Exception {

        unsolvedBoard = new int[][]{
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        SudokuSolver solver = new SudokuSolver(unsolvedBoard);



        int [][] actualValue = solver.cloneAndSet(unsolvedBoard, 3, 0, 1);
        actualValue=solver.cloneAndSet(actualValue,1,1,3);
        actualValue=solver.cloneAndSet(actualValue,6,8,7);
        actualValue=solver.cloneAndSet(actualValue,5,6,4);

        int [][] assertedValue = new int[][]{
                {5, 3, 0, 1, 7, 0, 0, 0, 0},
                {6, 3, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 4, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 7, 7, 9}
        };

        Assert.assertArrayEquals(assertedValue, actualValue);
        for(int i = 0; i<unsolvedBoard.length; i++){
            for(int j = 0; j<unsolvedBoard.length;j++){
                System.out.print(unsolvedBoard[i][j]);
            }
            System.out.println();
        }
        Assert.assertArrayEquals(new int[][]{
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        }, unsolvedBoard);
    }


    @Test
    public void testSolve() throws Exception {
        SudokuSolver solver = new SudokuSolver(unsolvedBoard);
        List<int[][]> solutions = solver.findAllSolutions();
        Assert.assertEquals(solutions.size(), 1);
        Assert.assertArrayEquals(solutions.get(0), solvedBoard);
    }

    @Test
    public void testChangeGridValueEmptyCoords() throws Exception {
        SudokuSolver solver = new SudokuSolver(Sudoku.EMPTY_GRID);
        List<Point> assertedEmptys = IntStream.range(0,9).boxed().flatMap(y -> IntStream.range(0,9).boxed().map(x -> new Point(x,y))).collect(toList());
        Assert.assertEquals(assertedEmptys.size(), solver.emptyPoints.length);
        for (int i = 0; i < assertedEmptys.size(); i++) {
            Assert.assertEquals(assertedEmptys.get(i), solver.emptyPoints[i]);
        }
        solver.changeGridValue(0,0,1);
        Assert.assertTrue(Arrays.asList(solver.emptyPoints).stream().noneMatch(p -> p.equals(new Point(0,0))));
        Assert.assertEquals(80, solver.emptyPoints.length);
        solver.changeGridValue(5,7,5);
        Assert.assertTrue(Arrays.asList(solver.emptyPoints).stream().noneMatch(p -> p.equals(new Point(5,7))));
        Assert.assertEquals(79, solver.emptyPoints.length);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solver.changeGridValue(j,i,2);
            }
        }
        Assert.assertEquals(0, solver.emptyPoints.length);
        solver.changeGridValue(3,5,0);
        Assert.assertEquals(1, solver.emptyPoints.length);
        Assert.assertEquals(solver.emptyPoints[0], new Point(3,5));
    }
}