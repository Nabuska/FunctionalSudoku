package com.company;

/**
 * Created by WinNabuska on 22.12.2015.
 */
public class SudokuController {
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            SudokuGenerator.generate(3);
            System.out.println("DONE------------------------------------------------------------------------------------");
        }
        //System.out.println(Stream.of(Sudoku.WORLDS_HARDEST_BOARD).mapToLong(row -> IntStream.of(row).filter(v -> v!=0).count()).sum());
        long t1 = System.currentTimeMillis();
        SudokuSolver.printBoard(SudokuSolver.findAllSolutions(Sudoku.WORLDS_HARDEST_BOARD).get(0));
        long t2 = System.currentTimeMillis()-t1;
        System.out.println(t2);
        t1 = System.currentTimeMillis();
        SudokuSolver.printBoard(SudokuSolver.findOneSolution(Sudoku.WORLDS_HARDEST_BOARD).get());
        t2 = System.currentTimeMillis()-t1;
        System.out.println(t2);


        /*
        Stream.of(allCoords)
                .filter(c -> Sudoku.WORLDS_HARDEST_BOARD[c[0]][c[1]]!=0)
                .map(c ->  cloneAndSet(Sudoku.WORLDS_HARDEST_BOARD, c[1], c[0], 0))
                .filter(b -> findAllSolutions(b).size()==1)
        .forEach(SudokuSolver::printBoard);*/
    }
}
