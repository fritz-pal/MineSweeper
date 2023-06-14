package me.fritzpal.minesweeper;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final int boardSize;
    private final int tileSize;
    private final Board board;
    private final JLabel minesLeft;
    private final JLabel time = new JLabel("0");
    private final Timer timer;
    JButton reset = new JButton();

    public Window(int size, int mines) {
        super("Minesweeper");
        this.boardSize = size;
        this.tileSize = 25;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(boardSize * tileSize, boardSize * tileSize + tileSize * 2);
        this.setResizable(false);
        this.setLayout(null);
        this.setIconImage(ImagePath.getResource("mine.png"));
        this.getContentPane().setPreferredSize(this.getSize());
        this.pack();
        this.setLocationRelativeTo(null);

        board = new Board(this, boardSize, boardSize, mines);

        time.setFont(new Font("Arial", Font.BOLD, 20));
        time.setIcon(new ImageIcon(ImagePath.getResource("time.png").getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)));
        time.setBounds(boardSize * tileSize / 3 * 2, 0, boardSize * tileSize / 3, tileSize * 2);
        time.setHorizontalAlignment(JLabel.CENTER);
        this.add(time);

        timer = new Timer(1000, e -> time.setText(String.valueOf(Integer.parseInt(time.getText()) + 1)));
        timer.start();

        minesLeft = new JLabel(String.valueOf(board.getMinesLeft()));
        minesLeft.setFont(new Font("Arial", Font.BOLD, 20));
        minesLeft.setIcon(new ImageIcon(ImagePath.getResource("flag.png").getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)));
        minesLeft.setHorizontalAlignment(JLabel.CENTER);
        minesLeft.setBounds(0, 0, boardSize * tileSize / 3, tileSize * 2);
        this.add(minesLeft);

        reset.setBounds((int) (boardSize * tileSize / 2 - ((tileSize*1.5/2))), (int) (tileSize - (tileSize*1.5/2)), (int) (tileSize * 1.5), (int) (tileSize * 1.5));
        reset.setFocusable(false);
        reset.setIcon(new ImageIcon(ImagePath.getResource("smile.png").getScaledInstance((int) (tileSize * 1.5), (int) (tileSize * 1.5), Image.SCALE_SMOOTH)));
        reset.addActionListener(e -> board.reset());
        this.add(reset);

        this.setVisible(true);
    }

    public void updateMines() {
        minesLeft.setText(String.valueOf(board.getMinesLeft()));
    }

    public void stopTime() {
        timer.stop();
    }

    public String getTime() {
        return time.getText();
    }

    public void resetTime() {
        time.setText("0");
        timer.start();
    }

    public void setResetIcon(String icon) {
        reset.setIcon(new ImageIcon(ImagePath.getResource(icon).getScaledInstance((int) (tileSize * 1.5), (int) (tileSize * 1.5), Image.SCALE_SMOOTH)));
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
