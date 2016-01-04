package joona.enbuska;

import joona.enbuska.Logic.Point;
import joona.enbuska.Logic.SudokuGenerator;

import javax.swing.*;
import java.util.Map;
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
        view = new SudokuView(blockWidth, this);
        SwingUtilities.invokeLater(() -> view.setMainPanelVisibility(false));
        int choise = JOptionPane.showConfirmDialog(view, "Extra hard?", "Select difficulty", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
        JOptionPane.showMessageDialog(view, "Note that generating a random sudoku grid will take a while. If nothing has happened within 60 seconds after clicking ok you are probably stuck");
        Map<Integer, int[][]> grid = SudokuGenerator.generate(blockWidth, choise == JOptionPane.OK_OPTION);
        int[][]unsolved = grid.get(SudokuGenerator.UNSOLVED);
        for (int y = 0; y < unsolved.length; y++)
            for (int x = 0; x < unsolved[0].length; x++)
                view.setValue(x, y, grid.get(0)[y][x]+"");
        SwingUtilities.invokeLater(() -> view.setMainPanelVisibility(true));
        solvedGrid=grid.get(SudokuGenerator.SOLVED);
    }

    public static void main(String[] args) {
        new SudokuController();
        /*for (int i = 0; i < 10000; i++) {
            Map<Integer, int[][]> sudoku = SudokuGenerator.generate(3, true);
            if(SudokuSolver.getValuedPoints(sudoku.get(SudokuGenerator.SOLVED)).size()<24){
                System.out.println("Values " + SudokuSolver.getValuedPoints(sudoku.get(SudokuGenerator.SOLVED)).size());
                Sudoku.printGrid(sudoku.get(SudokuGenerator.UNSOLVED));
                Sudoku.printGrid(sudoku.get(SudokuGenerator.SOLVED));
            }
        }*/
    }

    public void onInspectButtonClick() {
        int helpLeft = view.getHelpLeft();
        if(helpLeft>0) {
            Set<Point> wrongValuePoints = IntStream.range(0, solvedGrid.length)
                    .boxed().flatMap(y -> IntStream.range(0, solvedGrid.length)
                            .filter(x -> view.getValue(x, y) != 0 && solvedGrid[y][x] != view.getValue(x, y))
                            .boxed().map(x -> new Point(x, y))).collect(Collectors.toSet());
            SwingUtilities.invokeLater(() -> wrongValuePoints.forEach(p -> view.markAsIncorrect(p.x, p.y)));
            view.setHelpLeft(--helpLeft);
            JOptionPane.showMessageDialog(view, helpLeft+" cheats remaining");
        }
        else {
            JOptionPane.showMessageDialog(view, "NO cheats remaining");
        }
    }

    public void afterKeyReleased() {
        boolean finnished =
                IntStream.range(0, solvedGrid.length)
                .allMatch(y -> IntStream.range(0, solvedGrid.length)
                        .allMatch(x -> solvedGrid[y][x] == view.getValue(x, y)));
        if(finnished) {
            JOptionPane.showMessageDialog(view, "Complete");
            System.exit(0);
        }
    }
}
