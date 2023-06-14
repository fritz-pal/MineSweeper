package me.fritzpal.minesweeper;

import javax.swing.*;

public class Board extends JPanel {
    private final Tile[][] tiles;
    private final int rows;
    private final int columns;
    private final int mines;
    private final Window window;
    private boolean firstClick = true;
    private boolean lost = false;
    private boolean win = false;
    private int minesLeft;

    public Board(Window window, int rows, int columns, int mines) {
        this.window = window;
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        this.minesLeft = mines;
        this.tiles = new Tile[rows][columns];
        this.setLayout(null);
        this.initialize();

        this.setBounds(0, window.getTileSize() * 2, window.getBoardSize() * window.getTileSize(), window.getBoardSize() * window.getTileSize());
        window.add(this);
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
        int counter = 0;
        while (mines < this.mines && counter < this.mines * 1000) {
            int randomRow = (int) (Math.random() * this.rows);
            int randomColumn = (int) (Math.random() * this.columns);
            if (!(randomRow == row && randomColumn == column) && !this.tiles[randomRow][randomColumn].isMine() && !isAdjacent(randomRow, randomColumn, row, column)) {
                this.tiles[randomRow][randomColumn].setMine(true);
                mines++;
            }
            counter++;
        }

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                this.tiles[r][c].setAdjacentMines(this.getAdjacentMines(r, c));
            }
        }
    }

    private boolean isAdjacent(int row, int column, int targetRow, int targetColumn) {
        return row >= targetRow - 1 && row <= targetRow + 1 && column >= targetColumn - 1 && column <= targetColumn + 1;
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
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                if (value.isMine()) {
                    if (value.getState() != TileState.FLAGGED) {
                        value.setState(TileState.UNCOVERED);
                        value.repaint();
                    }
                } else if (value.getState() == TileState.FLAGGED) {
                    value.setState(TileState.WRONG_FLAGGED);
                    value.repaint();
                }
            }
        }
    }

    public boolean isFirstClick() {
        return this.firstClick;
    }

    public boolean isLost() {
        return this.lost;
    }

    public boolean isWin() {
        return this.win;
    }

    public void oh(boolean oh) {
        if (oh) window.setResetIcon("oh.png");
        else window.setResetIcon("smile.png");
    }

    public int getMinesLeft() {
        return this.minesLeft;
    }

    public void setMinesLeft(int minesLeft) {
        this.minesLeft = minesLeft;
        window.updateMines();
    }

    public void uncover(int row, int column, boolean withSound) {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) return;
        if (this.tiles[row][column].getState() == TileState.UNCOVERED) return;
        tiles[row][column].setState(TileState.UNCOVERED);
        tiles[row][column].repaint();

        if (tiles[row][column].isMine()) {
            Sound.play("lose.wav");
            window.setResetIcon("dead.png");
            gameOver();
            window.stopTime();
            lost = true;
            tiles[row][column].setRed(true);
            return;
        }

        if (tiles[row][column].getAdjacentMines() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    uncover(row + i, column + j, false);
                }
            }
            if (withSound) Sound.play("uncover.wav");
        } else if (withSound) Sound.play("tick.wav");
    }

    public void reset() {
        firstClick = true;
        lost = false;
        win = false;
        minesLeft = mines;
        window.setResetIcon("smile.png");
        window.updateMines();
        window.resetTime();
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                value.setState(TileState.COVERED);
                value.setRed(false);
                value.setMine(false);
                value.setAdjacentMines(0);
                value.repaint();
            }
        }
    }

    public void checkWin() {
        int uncovered = 0;
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                if (value.getState() == TileState.UNCOVERED) {
                    uncovered++;
                }
            }
        }
        if (uncovered == rows * columns - mines) {
            Sound.play("win.wav");
            window.setResetIcon("cool.png");
            win = true;
            gameOver();
            window.stopTime();
            JOptionPane.showMessageDialog(window, "You won in " + window.getTime() + " seconds!");
        }
    }

    public int getTileSize() {
        return window.getTileSize();
    }

    public void autoUncover(int row, int column) {
        if (this.tiles[row][column].getState() != TileState.UNCOVERED) return;
        int flags = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (r >= 0 && r < this.rows && c >= 0 && c < this.columns) {
                    if (this.tiles[r][c].getState() == TileState.FLAGGED) {
                        flags++;
                    }
                }
            }
        }
        if (flags == tiles[row][column].getAdjacentMines()) {
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = column - 1; c <= column + 1; c++) {
                    if (r >= 0 && r < this.rows && c >= 0 && c < this.columns) {
                        if (this.tiles[r][c].getState() == TileState.COVERED) {
                            uncover(r, c, true);
                        }
                    }
                }
            }
        }
    }
}
