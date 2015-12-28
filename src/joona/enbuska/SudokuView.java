package joona.enbuska;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by joona on 23/12/2015.
 */
public class SudokuView extends JFrame{

    private final JTextField [][] numberGrid;
    private final int MAX_VALUE;
    private final int BLOCK_WIDTH;
    private KeyListener keyListener;
    private SudokuController controller;
    private JButton checkForErrorsButton;
    private JPanel mainPanel;
    private int helpLeft = 3;

    public SudokuView(int blockWidth, SudokuController controller){
        this.controller = controller;
        this.BLOCK_WIDTH = blockWidth;
        MAX_VALUE = BLOCK_WIDTH * BLOCK_WIDTH;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int sideLength = BLOCK_WIDTH * BLOCK_WIDTH;
        GridLayout gridLayout = new GridLayout(BLOCK_WIDTH, BLOCK_WIDTH);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(gridLayout);
        JPanel [] [] panels = new JPanel[BLOCK_WIDTH][BLOCK_WIDTH];
        for (int i = 0; i < BLOCK_WIDTH; i++) {
            for (int j = 0; j < BLOCK_WIDTH; j++) {
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                panel.setLayout(gridLayout);
                panels[i][j] = panel;
                gridPanel.add(panel);
            }
        }
        numberGrid = new JTextField[sideLength][sideLength];
        initKeyListener();
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                JPanel panel = panels[i/ BLOCK_WIDTH][j/ BLOCK_WIDTH];
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font(Font.SERIF,Font.BOLD, 25));
                field.setPreferredSize(new Dimension(40,40));
                field.addKeyListener(keyListener);
                numberGrid[i][j]=field;
                panel.add(field);
            }
        }
        mainPanel.add(gridPanel);
        checkForErrorsButton = new JButton("Check For Errors");
        checkForErrorsButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.onCheckForErrorsClick();
            }
        });
        mainPanel.add(checkForErrorsButton);
        SwingUtilities.invokeLater(()-> {
            add(mainPanel);
            pack();
            setVisible(true);
        });
    }

    private void initKeyListener(){
        keyListener = new KeyListener() {

            int x = 0, y = 1;

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code==KeyEvent.VK_UP ||code == KeyEvent.VK_DOWN ||
                        code == KeyEvent.VK_LEFT ||code == KeyEvent.VK_RIGHT) {

                    JTextField f = (JTextField)e.getSource();
                    int [] p = null;
                    for (int y = 0; y < MAX_VALUE && p == null; y++)
                        for (int x = 0; x < MAX_VALUE && p == null; x++)
                            if(numberGrid[y][x]==f)
                                p = new int[]{y, x};
                    int next;
                    switch (code) {
                        case KeyEvent.VK_UP:
                            next = p[x]-1<0 ? MAX_VALUE -1 : p[x]-1;
                            numberGrid[next][p[y]].grabFocus();
                            break;
                        case KeyEvent.VK_DOWN:
                            next = p[x]+1== MAX_VALUE ? 0 : p[0]+1;
                            numberGrid[next][p[y]].grabFocus();
                            break;
                        case KeyEvent.VK_RIGHT:
                            next = p[y]+1== MAX_VALUE ? 0 : p[y]+1;
                            numberGrid[p[x]][next].grabFocus();
                            break;
                        case KeyEvent.VK_LEFT:
                            next = p[y]-1<0 ? MAX_VALUE -1 : p[y]-1;
                            numberGrid[p[x]][next].grabFocus();
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                JTextField f = (JTextField)e.getSource();
                char c = e.getKeyChar();
                f.setText(f.getText().replaceAll("[^0-9]+", ""));

                try {
                    if(f.getText().length()>1 && f.getText().charAt(0)=='0'){
                        f.setText(f.getText().replaceFirst("0", ""));
                    }
                    else if (Character.isDigit(c) && Integer.parseInt(f.getText().substring(0, f.getText().length()>1 ? 2 :0)) > MAX_VALUE) {
                        f.setText(c + "");
                    }
                }catch (NumberFormatException exception){}

                if (f.getText().length() == 0 || f.getText().charAt(0) == '0') {
                    f.setText("0");
                    f.setForeground(Color.WHITE);
                } else if (f.getForeground() != Color.BLACK) {
                    f.setForeground(Color.BLACK);
                }

                controller.afterKeyReleased();

            }
            @Override
            public void keyTyped(KeyEvent e) {}
        };

    }

    public void setValue(int x, int y, String value){
        numberGrid[y][x].setText(value);
        if(value.equals("0"))
            numberGrid[y][x].setForeground(Color.WHITE);
    }

    public void markAsIncorrect(int x, int y){
        numberGrid[y][x].setForeground(Color.RED);
    }

    public int getValue(int x, int y){
        String val = numberGrid[y][x].getText().trim();
        return val.length()>0 ? Integer.valueOf(val) : 0;
    }

    public void setMainPanelVisibility(boolean visibility){
        mainPanel.setVisible(visibility);
    }

    public int getHelpLeft(){
        return helpLeft;
    }

    public void setHelpLeft(int n){
        helpLeft = n;
    }
}
