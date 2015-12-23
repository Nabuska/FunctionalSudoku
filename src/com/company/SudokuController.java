package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by WinNabuska on 22.12.2015.
 */
public class SudokuController {
    private int[][] solvedGrid;
    private SudokuView view;

    public SudokuController(){
        int blockWidth = 3;
        List<int[][]> grid = SudokuGenerator.generate(blockWidth);
        view = new SudokuView(blockWidth, this);
        for (int i = 0; i < grid.get(0).length; i++) {
            for (int j = 0; j < grid.get(0).length; j++) {
                view.setValue(i, j, grid.get(0)[i][j]+"");
            }
        }
        solvedGrid=grid.get(1);
    }

    public static void main(String[] args) {
        new SudokuController();
        /*for (int i = 0; i < 1000000; i++) {
            SudokuGenerator.generate(3);
            System.out.println("DONE------------------------------------------------------------------------------------");
        }*/
        //System.out.println(Stream.of(Sudoku.WORLDS_HARDEST_BOARD).mapToLong(row -> IntStream.of(row).filter(v -> v!=0).count()).sum());
        /*long t1 = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
            List<int[][]> solutions = SudokuSolver.findAllSolutions(Sudoku.WORLDS_HARDEST_BOARD);
            Sudoku.printBoard(solutions.get(0));
            //SudokuSolver.findFirstSolutions(Sudoku.FOUR_BY_FOUR_BLOCK,1);
            //System.out.println("solutions " + SudokuSolver.findAllSolutions(Sudoku.WORLDS_HARDEST_BOARD).size());
            //SudokuSolver.printBoard(SudokuSolver.findFirstSolutions(Sudoku.WORLDS_HARDEST_BOARD, 1).get(0));
        }
        long t2 = System.currentTimeMillis()-t1;
        System.out.println(t2);*/
        /*
        Stream.of(allCoords)
                .filter(c -> Sudoku.WORLDS_HARDEST_BOARD[c[0]][c[1]]!=0)
                .map(c ->  cloneAndSet(Sudoku.WORLDS_HARDEST_BOARD, c[1], c[0], 0))
                .filter(b -> findAllSolutions(b).size()==1)
        .forEach(SudokuSolver::printBoard);*/
    }

    public void onCheckClick() {
        Set<int[]> wrongValues = IntStream.range(0, solvedGrid.length)
                .boxed().flatMap(y -> IntStream.range(0, solvedGrid.length)
                        .filter(x -> view.getValue(x,y)!=0 && solvedGrid[y][x] !=view.getValue(x,y))
                            .boxed().map(x -> new int[]{x,y})).collect(Collectors.toSet());
        for(int[] coord : wrongValues)
            view.setValue(coord[1], coord[0], ""+0);
    }
}
