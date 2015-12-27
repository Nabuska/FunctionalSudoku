package com.company;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
/*
    @org.junit.Test
    public void testBlockValuesAt() throws Exception {

        SudokuSolver.init(unsolvedBoard);

        Set<Integer> assertedValues = new HashSet<>(Arrays.asList(0,3,5,6,8,9));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 0,0).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,1,5,7,9));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 3,0).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,6));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 7,0).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,4,7,8));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 0,5).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,2,3,6,8));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 4,4).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,1,3,6));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 8,4).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,6));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 0,6).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,1,4,8,9));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 4,7).boxed().collect(Collectors.toSet()));

        assertedValues = new HashSet<>(Arrays.asList(0,2,5,7,8,9));
        Assert.assertEquals(assertedValues, SudokuSolver.blockValuesAt(unsolvedBoard, 8,7).boxed().collect(Collectors.toSet()));
    }

    @Test
    public void testValidValueFor() throws Exception {
        Set<Integer> assertedValues = new HashSet<>(Arrays.asList(2,4,8));
        Set<Integer> actualValues = SudokuSolver.validValueFor(unsolvedBoard, 8,0).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);


        assertedValues = new HashSet<>(Arrays.asList(2, 4, 7));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 1,1).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = new HashSet<>(Arrays.asList(5,7,9));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 3,3).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = new HashSet<>(Arrays.asList(4,5));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 4,5).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = new HashSet<>(Arrays.asList(2,3,5,6));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 3,8).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = new HashSet<>(Arrays.asList(1,2,3,4,5));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 2,8).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = new HashSet<>(Arrays.asList(3,6));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 6,7).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = new HashSet<>(Arrays.asList(5,7,9));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 6,4).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);

        assertedValues = new HashSet<>(Arrays.asList(1,3,4,5,7));
        actualValues = SudokuSolver.validValueFor(unsolvedBoard, 6,2).boxed().collect(Collectors.toSet());
        Assert.assertEquals(assertedValues,actualValues);
    }

    @org.junit.Test
    public void testRowValues() throws Exception {
        Set<Integer> actualValues = SudokuSolver.rowValues(unsolvedBoard, 0).boxed().collect(Collectors.toSet());
        Set<Integer> assertedValues = new HashSet<>(Arrays.asList(5,3,0,7));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = SudokuSolver.rowValues(unsolvedBoard, 1).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(6,0,1,9,5));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.rowValues(unsolvedBoard, 2).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,9,8,6));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.rowValues(unsolvedBoard, 3).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,8,6,3));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.rowValues(unsolvedBoard, 4).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(4,0,8,3,1));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.rowValues(unsolvedBoard, 5).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(7,0,2,6));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.rowValues(unsolvedBoard, 6).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,6,2,8));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.rowValues(unsolvedBoard, 7).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,4,1,9,5));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.rowValues(unsolvedBoard, 8).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,8,7,9));
        Assert.assertEquals(assertedValues,actualValues);

    }

    @org.junit.Test
    public void testColumnValues() throws Exception {
        Set<Integer> actualValues = SudokuSolver.columnValues(unsolvedBoard, 0).boxed().collect(Collectors.toSet());
        Set<Integer> assertedValues = new HashSet<>(Arrays.asList(5,6,8,4,7,0));
        Assert.assertEquals(assertedValues,actualValues);

        actualValues = SudokuSolver.columnValues(unsolvedBoard, 1).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(3,9,0,6));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.columnValues(unsolvedBoard, 2).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,8));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.columnValues(unsolvedBoard, 3).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,1,4,8));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.columnValues(unsolvedBoard, 4).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(7,9,1,6,2,0,8));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.columnValues(unsolvedBoard, 5).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,5,3,9));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.columnValues(unsolvedBoard, 6).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,2));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.columnValues(unsolvedBoard, 7).boxed().collect(Collectors.toSet());
        assertedValues = new HashSet<>(Arrays.asList(0,6,8,7));
        Assert.assertEquals(assertedValues,actualValues);


        actualValues = SudokuSolver.columnValues(unsolvedBoard, 8).boxed().collect(Collectors.toSet());
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

        int [][] actualValue = SudokuSolver.cloneAndSet(unsolvedBoard, 3, 0, 1);
        actualValue=SudokuSolver.cloneAndSet(actualValue,1,1,3);
        actualValue=SudokuSolver.cloneAndSet(actualValue,6,8,7);
        actualValue=SudokuSolver.cloneAndSet(actualValue,5,6,4);
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

        Assert.assertArrayEquals((int[][])SudokuSolver.findAllSolutions(unsolvedBoard).get(0), solvedBoard);

    }*/
}