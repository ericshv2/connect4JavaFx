import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * GameBoard class - Creates and manages the Connect 4 game board UI
 * Displays a 6x7 grid with visual representation of the game state
 * 
 * @author Aarav
 * @date Dec 1, 2025
 */
public class GameBoard {
    
    // Constants for board dimensions
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int CELL_SIZE = 80;
    private static final int CIRCLE_RADIUS = 30;
    
    // UI Components
    private Scene scene;
    private GridPane boardGrid;
    private Circle[][] circleSlots;
    private Label statusLabel;
    private BoardModel boardModel;
    
    /**
     * Constructor - Initializes the game board UI
     */
    public GameBoard() {
        // Initialize the board model
        boardModel = new BoardModel();
        
        // Create UI components
        createUI();
    }
    
    /**
     * Creates the complete UI layout for the game board
     */
    private void createUI() {
        // Main container
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #2C3E50;");
        
        // Title label at top
        Label titleLabel = new Label("CONNECT 4");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        titleLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(0, 0, 20, 0));
        root.setTop(titleLabel);
        
        // Create the game board grid
        createBoardGrid();
        root.setCenter(boardGrid);
        
        // Status label at bottom
        statusLabel = new Label("Player 1's Turn (Red)");
        statusLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        statusLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        BorderPane.setMargin(statusLabel, new Insets(20, 0, 0, 0));
        root.setBottom(statusLabel);
        
        // Create the scene
        scene = new Scene(root, COLS * CELL_SIZE + 100, ROWS * CELL_SIZE + 200);
    }
    
    /**
     * Creates the 6x7 grid with circular slots for game pieces
     */
    private void createBoardGrid() {
        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        boardGrid.setPadding(new Insets(10));
        
        // Set blue background for the board
        boardGrid.setStyle("-fx-background-color: #3498DB; -fx-background-radius: 10;");
        
        // Initialize the circle slots array
        circleSlots = new Circle[ROWS][COLS];
        
        // Create each cell in the grid
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // Create a stack pane to hold the cell background and circle
                StackPane cell = createCell(row, col);
                boardGrid.add(cell, col, row);
            }
        }
    }
    
    /**
     * Creates a single cell with a circular slot
     * @param row Row index of the cell
     * @param col Column index of the cell
     * @return StackPane containing the cell components
     */
    private StackPane createCell(int row, int col) {
        StackPane cell = new StackPane();
        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        
        // Background rectangle for the cell
        Rectangle bg = new Rectangle(CELL_SIZE, CELL_SIZE);
        bg.setFill(Color.web("#3498DB"));
        
        // Circle representing the slot
        Circle circle = new Circle(CIRCLE_RADIUS);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.web("#2C3E50"));
        circle.setStrokeWidth(2);
        
        // Store the circle reference
        circleSlots[row][col] = circle;
        
        // Add hover effect for columns
        cell.setOnMouseEntered(e -> {
            if (boardModel.getCell(row, col) == 0) {
                cell.setStyle("-fx-cursor: hand;");
            }
        });
        
        cell.setOnMouseExited(e -> {
            cell.setStyle("-fx-cursor: default;");
        });
        
        // Add components to cell
        cell.getChildren().addAll(bg, circle);
        
        return cell;
    }
    
    /**
     * Updates the visual display of a cell based on the board model
     * @param row Row index
     * @param col Column index
     */
    public void updateCell(int row, int col) {
        int player = boardModel.getCell(row, col);
        Circle circle = circleSlots[row][col];
        
        if (player == 1) {
            circle.setFill(Color.web("#E74C3C")); // Red for Player 1
        } else if (player == 2) {
            circle.setFill(Color.web("#F1C40F")); // Yellow for Player 2
        } else {
            circle.setFill(Color.WHITE); // Empty slot
        }
    }
    
    /**
     * Updates the status label text
     * @param status New status text to display
     */
    public void updateStatus(String status) {
        statusLabel.setText(status);
    }
    
    /**
     * Gets the scene containing the game board
     * @return Scene object for displaying in a Stage
     */
    public Scene getScene() {
        return scene;
    }
    
    /**
     * Gets the board model
     * @return BoardModel instance
     */
    public BoardModel getBoardModel() {
        return boardModel;
    }
    
    /**
     * Gets the GridPane containing the game board
     * @return GridPane with the game board
     */
    public GridPane getBoardGrid() {
        return boardGrid;
    }
}