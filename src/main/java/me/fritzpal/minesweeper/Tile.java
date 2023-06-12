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
    private boolean isRed = false;

    public Tile(Board board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        this.state = TileState.COVERED;
        this.isMine = false;
        this.adjacentMines = 0;
        this.setBounds(row * board.getTileSize(), column * board.getTileSize(), board.getTileSize(), board.getTileSize());
        this.setFocusable(false);

        this.addMouseListener(mouseListener());
        board.add(this);
    }

    private MouseListener mouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (board.isLost()) return;
                if (state == TileState.COVERED) {
                    if (e.getButton() == 3) {
                        if (System.currentTimeMillis() - flagTime < 100) return;
                        state = TileState.FLAGGED;
                        board.setMinesLeft(board.getMinesLeft() - 1);
                        flagTime = System.currentTimeMillis();
                        repaint();
                        Sound.play("flag.wav");
                    } else {
                        if (board.isFirstClick()) {
                            board.setMines(row, column);
                        }
                        board.uncover(row, column, true);
                        board.checkWin();
                    }
                } else if (state == TileState.FLAGGED && e.getButton() == 3 && System.currentTimeMillis() - flagTime > 100) {
                    state = TileState.COVERED;
                    flagTime = System.currentTimeMillis();
                    board.setMinesLeft(board.getMinesLeft() + 1);
                    repaint();
                    Sound.play("flag.wav");
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
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, board.getTileSize(), board.getTileSize());
            if (isMine) {
                if (board.isLost() && isRed) {
                    g.setColor(Color.RED);
                    g.fillRect(0, 0, board.getTileSize(), board.getTileSize());
                }
                g.drawImage(ImagePath.getResource("mine.png"), 0, 0, board.getTileSize(), board.getTileSize(), null);
            } else if (adjacentMines > 0) {
                g.setColor(Window.colors[adjacentMines - 1]);
                g.setFont(new Font("Calibri", Font.BOLD, board.getTileSize()));
                g.drawString(String.valueOf(adjacentMines), 5, board.getTileSize() - 5);
            }
        } else if (state == TileState.FLAGGED) {
            g.drawImage(ImagePath.getResource("flag.png"), 0, 0, board.getTileSize(), board.getTileSize(), null);
        }
    }

    public void setRed(boolean red) {
        this.isRed = red;
    }
}
