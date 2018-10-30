package fall2018.csc207project.gamecenter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    final static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    final static int NUM_COLS = 4;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        // Temporary variable for storing a Tile.
        Tile temp;

        temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Returns an iterator for this Board.
     *
     * @return an iterator for this Board.
     */
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /**
     * An Iterator for Tile class
     */
    private class TileIterator implements Iterator<Tile> {

        /**
         * The index of the next Tile to return.
         */
        private int current = 0;

        /**
         * Returns whether there is another Tile to return.
         *
         * @return whether here is another Tile to return.
         */
        @Override
        public boolean hasNext() {
            return current < NUM_ROWS * NUM_COLS;
        }

        /**
         * Returns the next Tile.
         *
         * @return the next Tile.
         */
        @Override
        public Tile next() {
            // The Tile which are supposed to be return.
            Tile target;
            // The row and column position of the Tile.
            int row, col;

            row = current / NUM_ROWS;
            col = current % NUM_COLS;
            try {
                target = tiles[row][col];
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return target;
        }
    }
}
