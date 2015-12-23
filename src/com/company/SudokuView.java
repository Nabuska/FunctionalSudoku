package com.company;

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
    private final int maxValue;
    private final int blockHeight;
    private KeyListener keyListener;
    private SudokuController controller;
    private JButton checkButton;

    public SudokuView(int blockHeight, SudokuController controller){
        this.controller = controller;
        this.blockHeight = blockHeight;
        maxValue=blockHeight*blockHeight;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int sideLength = blockHeight*blockHeight;
        GridLayout gridLayout = new GridLayout(blockHeight,blockHeight);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(gridLayout);
        JPanel [] [] panels = new JPanel[blockHeight][blockHeight];
        for (int i = 0; i < blockHeight; i++) {
            for (int j = 0; j < blockHeight; j++) {
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
                JPanel panel = panels[i/blockHeight][j/blockHeight];
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
        checkButton = new JButton("Check");
        checkButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.onCheckClick();
            }
        });
        mainPanel.add(checkButton);
        SwingUtilities.invokeLater(()-> {
            add(mainPanel);
            pack();
            setVisible(true);
        });
    }

    private void initKeyListener(){
        keyListener =
        new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code==KeyEvent.VK_UP ||code == KeyEvent.VK_DOWN ||
                        code == KeyEvent.VK_LEFT ||code == KeyEvent.VK_RIGHT) {

                    JTextField f = (JTextField)e.getSource();
                    int [] coord = null;
                    for (int y = 0; y < maxValue && coord == null; y++)
                        for (int x = 0; x < maxValue && coord == null; x++)
                            if(numberGrid[y][x]==f)
                                coord = new int[]{y, x};
                    int next;
                    switch (code) {
                        case KeyEvent.VK_UP:
                            next = coord[0]-1<0 ? maxValue-1 : coord[0]-1;
                            numberGrid[next][coord[1]].grabFocus();
                            break;
                        case KeyEvent.VK_DOWN:
                            next = coord[0]+1==maxValue ? 0 : coord[0]+1;
                            numberGrid[next][coord[1]].grabFocus();
                            break;
                        case KeyEvent.VK_RIGHT:
                            next = coord[1]+1==maxValue ? 0 : coord[1]+1;
                            numberGrid[coord[0]][next].grabFocus();
                            break;
                        case KeyEvent.VK_LEFT:
                            next = coord[1]-1<0 ? maxValue-1 : coord[1]-1;
                            numberGrid[coord[0]][next].grabFocus();
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                JTextField f = (JTextField)e.getSource();
                char c = e.getKeyChar();
                if (f.getText().length() != 0) {
                    if (f.getText().charAt(0) == '0')
                        f.setText(f.getText().replace("0", ""));
                    else if (!Character.isDigit(c)) {
                        f.setText(f.getText().replaceFirst("[^1-9]", ""));
                    } else if (Integer.parseInt(f.getText().trim()) > maxValue) {
                        f.setText(c+"");
                    }
                }
                if(f.getText().length()==0){
                    f.setText("0");
                    f.setForeground(Color.WHITE);
                }
                else if(f.getForeground()!=Color.BLACK)
                    f.setForeground(Color.BLACK);
            }
        };

    }

    public void setValue(int x, int y, String value){
        numberGrid[y][x].setText(value);
        if(value.equals("0"))
            numberGrid[y][x].setForeground(Color.WHITE);
    }

    public int getValue(int x, int y){
        String val = numberGrid[x][y].getText();
        return val.length()>0 ? Integer.valueOf(val) : 0;
    }
}
