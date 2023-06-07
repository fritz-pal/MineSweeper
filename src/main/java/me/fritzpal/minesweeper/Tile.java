package me.fritzpal.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tile extends JButton {
    private final Board board;
    private final int row;
    private final int column;
    private TileState state;
    private boolean isMine;
    private int adjacentMines;
    private long flagTime = 0;

    public Tile(Board board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        this.state = TileState.COVERED;
        this.isMine = false;
        this.adjacentMines = 0;
        this.setBounds(row * Window.TILE_SIZE, column * Window.TILE_SIZE, Window.TILE_SIZE, Window.TILE_SIZE);

        this.addMouseListener(mouseListener());
        board.add(this);
    }

    private MouseListener mouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (state == TileState.COVERED) {
                    if (e.getButton() == 3) {
                        state = TileState.FLAGGED;
                        flagTime = System.currentTimeMillis();
                        repaint();
                    } else {
                        if (board.isFirstClick()) {
                            board.setMines(row, column);
                        }
                        board.uncover(row, column);
                    }
                } else if (state == TileState.FLAGGED && e.getButton() == 3 && System.currentTimeMillis() - flagTime > 100) {
                    state = TileState.COVERED;
                    repaint();
                }
            }
        };
    }

    public boolean isMine() {
        return this.isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public int getAdjacentMines() {
        return this.adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public TileState getState() {
        return this.state;
    }

    public void setState(TileState state) {
        this.state = state;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == TileState.UNCOVERED) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, Window.TILE_SIZE, Window.TILE_SIZE);
            if (isMine) {
                if (board.isLost()) {
                    g.setColor(Color.RED);
                    g.fillRect(0, 0, Window.TILE_SIZE, Window.TILE_SIZE);
                }
                g.drawImage(new ImageIcon("src/main/resources/mine.png").getImage(), 0, 0, Window.TILE_SIZE, Window.TILE_SIZE, null);
            } else if (adjacentMines > 0) {
                g.setColor(Window.colors[adjacentMines]);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(String.valueOf(adjacentMines), 5, Window.TILE_SIZE - 5);
            }
        } else if (state == TileState.FLAGGED) {
            g.drawImage(new ImageIcon("src/main/resources/flag.png").getImage(), 0, 0, Window.TILE_SIZE, Window.TILE_SIZE, null);
        }
    }
}
