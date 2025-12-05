/**
 * BoardModel class - Manages the game board data structure
 * Stores the state of the game using a 2D array
 * 0 = empty, 1 = Player 1 (Red), 2 = Player 2 (Yellow)
 * 
 * @author Ajitesh
 * @date Dec 1, 2025
 */
public class BoardModel {
    
    // Board dimensions
    private static final int ROWS = 6;
    private static final int COLS = 7;
    
    // 2D array to store board state
    private int[][] board;
    
    // Current player (1 or 2)
    private int currentPlayer;
    
    /**
     * Constructor - Initializes an empty board
     */
    public BoardModel() {
        board = new int[ROWS][COLS];
        currentPlayer = 1; // Player 1 starts
        initializeBoard();
    }
    
    /**
     * Initializes the board with all empty slots (0)
     */
    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = 0;
            }
        }
    }
    
    /**
     * Gets the value at a specific cell
     * @param row Row index (0-5)
     * @param col Column index (0-6)
     * @return Cell value (0 = empty, 1 = Player 1, 2 = Player 2)
     */
    public int getCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return board[row][col];
        }
        return -1; // Invalid position
    }
    
    /**
     * Sets the value at a specific cell
     * @param row Row index (0-5)
     * @param col Column index (0-6)
     * @param player Player number (1 or 2)
     * @return true if successful, false if invalid
     */
    public boolean setCell(int row, int col, int player) {
        if (isValidPosition(row, col) && board[row][col] == 0) {
            board[row][col] = player;
            return true;
        }
        return false;
    }
    
    /**
     * Drops a piece in the specified column
     * The piece falls to the lowest available row in that column
     * @param col Column index (0-6)
     * @param player Player number (1 or 2)
     * @return Row where piece was placed, or -1 if column is full
     */
    public int dropPiece(int col, int player) {
        if (col < 0 || col >= COLS) {
            return -1; // Invalid column
        }
        
        // Find the lowest empty row in this column
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = player;
                return row;
            }
        }
        
        return -1; // Column is full
    }
    
    /**
     * Checks if a position is within the board boundaries
     * @param row Row index
     * @param col Column index
     * @return true if valid, false otherwise
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }
    
    /**
     * Checks if a column has space for another piece
     * @param col Column index (0-6)
     * @return true if column has space, false if full
     */
    public boolean isColumnAvailable(int col) {
        if (col < 0 || col >= COLS) {
            return false;
        }
        return board[0][col] == 0; // Check top row
    }
    
    /**
     * Gets the current player
     * @return Current player number (1 or 2)
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Sets the current player
     * @param player Player number (1 or 2)
     */
    public void setCurrentPlayer(int player) {
        if (player == 1 || player == 2) {
            this.currentPlayer = player;
        }
    }
    
    /**
     * Switches to the other player
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }
    
    /**
     * Resets the board to empty state
     */
    public void resetBoard() {
        initializeBoard();
        currentPlayer = 1;
    }
    
    /**
     * Checks if the board is completely full
     * @return true if no empty spaces remain, false otherwise
     */
    public boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Gets the number of rows
     * @return Number of rows (6)
     */
    public int getRows() {
        return ROWS;
    }
    
    /**
     * Gets the number of columns
     * @return Number of columns (7)
     */
    public int getColumns() {
        return COLS;
    }
    
    /**
     * Prints the board state to console (for debugging)
     */
    public void printBoard() {
        System.out.println("\n=== Board State ===");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("===================\n");
    }
}