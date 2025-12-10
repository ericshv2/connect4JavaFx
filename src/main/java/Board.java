public class Board {
    
    private int[][] grid; // 0 = empty, 1 = player 1 (red), 2 = player 2 (yellow)
    private int rows = 6;
    private int cols = 7;
    
    public Board() {
        // Initialize empty board
        grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 0;
            }
        }
    }
    
    // Drop a coin in a column
    public int dropCoin(int col, int player) {
        // Check if column is valid
        if (col < 0 || col >= cols) {
            return -1;
        }
        
        // Find the lowest empty row in this column
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == 0) {
                grid[row][col] = player;
                return row; // Return the row where coin was placed
            }
        }
        
        return -1; // Column is full
    }
    
    // Check if someone won
    public boolean checkWin(int player) {
        // Check horizontal
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (grid[row][col] == player &&
                    grid[row][col + 1] == player &&
                    grid[row][col + 2] == player &&
                    grid[row][col + 3] == player) {
                    return true;
                }
            }
        }
        
        // Check vertical
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == player &&
                    grid[row + 1][col] == player &&
                    grid[row + 2][col] == player &&
                    grid[row + 3][col] == player) {
                    return true;
                }
            }
        }
        
        // Check diagonal (bottom-left to top-right)
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (grid[row][col] == player &&
                    grid[row - 1][col + 1] == player &&
                    grid[row - 2][col + 2] == player &&
                    grid[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        
        // Check diagonal (top-left to bottom-right)
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (grid[row][col] == player &&
                    grid[row + 1][col + 1] == player &&
                    grid[row + 2][col + 2] == player &&
                    grid[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    // Check if board is full (tie)
    public boolean isFull() {
        for (int col = 0; col < cols; col++) {
            if (grid[0][col] == 0) {
                return false;
            }
        }
        return true;
    }
    
    // Get cell value
    public int getCell(int row, int col) {
        return grid[row][col];
    }
    
    // Reset the board
    public void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 0;
            }
        }
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
}