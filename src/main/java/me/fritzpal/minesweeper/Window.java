package me.fritzpal.minesweeper;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static int BOARD_SIZE = 30;
    public static int TILE_SIZE = 25;
    public static Color[] colors = {Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.CYAN, Color.YELLOW};

    public Window() {
        super("Minesweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE + TILE_SIZE * 2);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setPreferredSize(this.getSize());
        this.pack();

        Board board = new Board(BOARD_SIZE, BOARD_SIZE, 200);
        board.setBounds(0, TILE_SIZE * 2, BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE);
        this.add(board);

        this.setVisible(true);
    }
}
