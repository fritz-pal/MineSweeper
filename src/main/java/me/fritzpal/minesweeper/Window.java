package me.fritzpal.minesweeper;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static Color[] colors = {Color.BLUE, new Color(0x007c00), Color.RED, Color.MAGENTA, new Color(0xfd9004), new Color(0x0097a7), new Color(0x7d007d), Color.BLACK};
    private final int boardSize;
    private final int tileSize;
    private final Board board;
    private final JLabel minesLeft;
    private final JLabel time = new JLabel("0");
    private final Timer timer;

    public Window(int size, int mines) {
        super("Minesweeper");
        this.boardSize = size;
        this.tileSize = 800 / boardSize;

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

        JButton reset = new JButton("Reset");
        reset.setBounds(boardSize * tileSize / 2 - 40, tileSize / 2, 80, tileSize);
        reset.setFocusable(false);
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

    public int getTileSize() {
        return tileSize;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
