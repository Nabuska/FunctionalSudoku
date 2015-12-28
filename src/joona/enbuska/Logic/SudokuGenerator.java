package joona.enbuska.Logic;

import org.junit.Assert;

import java.util.*;

/**
 * Created by WinNabuska on 22.12.2015.
 */
public class SudokuGenerator {

    private static Random random = new Random();
    public static int UNSOLVED = 0, SOLVED = 1;

    /**Generates a random sudoku board with the given BLOCK_WIDTH
     * In a usual Sudoku game BLOCK_WIDTH is 3 and the grid values are 1-9
     * Forexample if a BLOCK_WIDTH is 4 the values are 1-16, if 2 the values are 1-4 etc.*/
    public static Map<Integer, int [][]> generate(int blockWidth, boolean extraHard){
        SudokuSolver solver = new SudokuSolver(Sudoku.EMPTY_GRID);

        placeRandomNumbers(solver, (int)Math.round(Math.pow(blockWidth,3)/2*3));

        makeUnambiguous(solver);

        int [][] solution;

        if(extraHard)
            solution = removeRedundantValues(solver);
        else
            solution = solver.findFirstSolutions(1).get(0);

        Map<Integer, int[][]> sudoku = new HashMap<>();
        sudoku.put(UNSOLVED, solver.grid);
        sudoku.put(SOLVED, solution);
        return sudoku;
    }

    /**Places the given number of random values on random places on the grid*/
    private static boolean placeRandomNumbers(SudokuSolver solver, int howMany){
        List<int[]> coords = Sudoku.allCoords(solver.grid);
        Collections.shuffle(coords);
        for(int i = 0; i<howMany;){
            int [] coord = coords.get(i);
            List<Integer> validValues = solver.validValueFor(coord[0], coord[1]);
            Collections.shuffle(validValues);
            boolean becomesClearlyUnsolvable = true;
            for (int j = 0; j < validValues.size() && becomesClearlyUnsolvable; j++) {
                solver.changeGridValue(coord[0], coord[1], validValues.get(j));
                becomesClearlyUnsolvable = coords.subList(i+1, coords.size()-1).stream()
                        .anyMatch(c -> solver.validValueFor(c[0], c[1]).size()==0);
            }
            if(becomesClearlyUnsolvable){//begin from the start
                i=0;
                for (int j = 0; j < solver.grid.length; j++)
                    for (int k = 0; k < solver.grid[j].length; k++)
                        solver.changeGridValue(k,j,0);
            }
            else
                i++;
        }
        return true;
    }


    /**alters the grid so that there will be only 1 right solution for it*/
    private static boolean makeUnambiguous(SudokuSolver solver) {
        List<int[][]> solutions;
        while ((solutions = solver.findFirstSolutions(2)).size() != 1) {
            List<Point> valuedPoints = SudokuSolver.getValuedPoints(solver.grid);
            if(valuedPoints.size()<21)
                return false;
            if (solutions.size() == 0) {
                Point point = valuedPoints.stream()
                        .parallel()
                        .filter(p -> new SudokuSolver(solver.cloneAndSet(solver.grid, p.x, p.y, 0)).findFirstSolutions(1).size()>0)
                        .findAny()
                        .orElse(SudokuSolver.getValuedPoints(solver.grid).get(random.nextInt(valuedPoints.size())));

                solver.changeGridValue(point.x, point.y, 0);

            } else if(solutions.size()>1){

                Point ep = solver.emptyPoints[random.nextInt(solver.emptyPoints.length)];
                int[][] solution = solutions.get(random.nextInt(solutions.size()));

                Assert.assertEquals(solver.grid[ep.y][ep.x], 0);

                solver.changeGridValue(ep.x, ep.y, solution[ep.y] [ep.x]);

                Assert.assertTrue(solver.grid[ep.y][ep.x]!=0);
            }
        }
        return true;
    }

    /**Removes redundant values from the grid so that no unambiguous solutions are generated as result*/
    private static int[][] removeRedundantValues(SudokuSolver solver){
        int [][] solution = null;
        int i = 0;
        int s = SudokuSolver.getValuedPoints(solver.grid).size();

        for(Point p : SudokuSolver.getValuedPoints(solver.grid)){

            //System.out.println(i++ +" / " + s);

            int originalValue = solver.grid[p.y][p.x];
            solver.changeGridValue(p.x, p.y, 0);
            List<int[][]> solutions = solver.findFirstSolutions(2);
            if(solutions.size()>1)
                solver.changeGridValue(p.x, p.y, originalValue);
            else {
                solution = solutions.get(0);
              //  System.out.println("++");
            }
        }

        if(solution==null)
            solution = solver.findFirstSolutions(1).get(0);

        Assert.assertTrue(SudokuSolver.checkIsLegit(solution));
        Assert.assertTrue(SudokuSolver.checkIsLegit(solver.grid));

        System.out.print(".");

        return solution;
    }
}
