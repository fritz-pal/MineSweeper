package me.fritzpal.minesweeper;

import javax.swing.*;

public class Board extends JPanel {
    private final Tile[][] tiles;
    private final int rows;
    private final int columns;
    private final int mines;
    private boolean firstClick = true;
    private boolean lost = false;

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        this.tiles = new Tile[rows][columns];
        this.setLayout(null);
        this.initialize();
    }

    private void initialize() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                this.tiles[row][column] = new Tile(this, row, column);
            }
        }
    }

    public void setMines(int row, int column) {
        firstClick = false;

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                this.tiles[r][c].setMine(false);
            }
        }

        int mines = 0;
        while (mines < this.mines) {
            int randomRow = (int) (Math.random() * this.rows);
            int randomColumn = (int) (Math.random() * this.columns);
            if (randomRow != row && randomColumn != column && !this.tiles[randomRow][randomColumn].isMine()) {
                this.tiles[randomRow][randomColumn].setMine(true);
                mines++;
            }
        }

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                this.tiles[r][c].setAdjacentMines(this.getAdjacentMines(r, c));
            }
        }
        if (tiles[row][column].getAdjacentMines() != 0) {
            setMines(row, column);
        }
    }

    public int getAdjacentMines(int row, int column) {
        if (tiles[row][column].isMine()) {
            return -1;
        }
        int mines = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (r >= 0 && r < this.rows && c >= 0 && c < this.columns) {
                    if (this.tiles[r][c].isMine()) {
                        mines++;
                    }
                }
            }
        }
        return mines;
    }

    public void gameOver() {
        lost = true;
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                value.setState(TileState.UNCOVERED);
                value.repaint();
            }
        }
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public boolean isFirstClick() {
        return this.firstClick;
    }

    public boolean isLost() {
        return this.lost;
    }

    public void uncover(int row, int column) {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) return;
        if (this.tiles[row][column].getState() == TileState.UNCOVERED) return;
        tiles[row][column].setState(TileState.UNCOVERED);
        tiles[row][column].repaint();

        if (tiles[row][column].getAdjacentMines() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    uncover(row + i, column + j);
                }
            }
        }
    }
}
