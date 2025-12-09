import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;

/**
 * GameBoard class - Creates and manages the Connect 4 game board UI
 * Displays a 6x7 grid with visual representation of the game state
 * Handles coin dropping animations with physics
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
    private static final double BOARD_START_Y = 150; // Where the board actually starts
    
    // UI Components
    private Scene scene;
    private GridPane boardGrid;
    private Pane animationPane;
    private ImageView[][] coinSlots;
    private Label statusLabel;
    private BoardModel boardModel;
    private Physics physics;
    private Button[] columnButtons;
    private BorderPane mainLayout;
    
    // Animation state
    private boolean isAnimating = false;
    private ImageView fallingCoin;
    
    /**
     * Constructor - Initializes the game board UI
     */
    public GameBoard() {
        // Initialize the board model and physics
        boardModel = new BoardModel();
        physics = new Physics();
        
        // Create UI components
        createUI();
    }
    
    /**
     * Creates the complete UI layout for the game board
     */
    private void createUI() {
        // Main container with animation pane overlay
        StackPane root = new StackPane();
        
        // Background pane
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        updateBackgroundColor(); // Set initial background color
        
        // Title label at top
        Label titleLabel = new Label("CONNECT 4");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        titleLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(0, 0, 20, 0));
        
        // Column selection buttons
        HBox buttonBox = createColumnButtons();
        
        // Combine title and buttons in top section
        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);
        topSection.getChildren().addAll(titleLabel, buttonBox);
        mainLayout.setTop(topSection);
        
        // Create the game board grid
        createBoardGrid();
        mainLayout.setCenter(boardGrid);
        
        // Status label at bottom
        statusLabel = new Label("Player 1's Turn (Red)");
        statusLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        statusLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        BorderPane.setMargin(statusLabel, new Insets(20, 0, 0, 0));
        mainLayout.setBottom(statusLabel);
        
        // Animation pane for falling coins (overlays the board)
        animationPane = new Pane();
        animationPane.setMouseTransparent(true);
        
        // Add both to root
        root.getChildren().addAll(mainLayout, animationPane);
        
        // Create the scene
        scene = new Scene(root, COLS * CELL_SIZE + 100, ROWS * CELL_SIZE + 300);
    }
    
    /**
     * Updates the background color based on current player
     */
    private void updateBackgroundColor() {
        String bgColor;
        if (boardModel.getCurrentPlayer() == 1) {
            bgColor = "#FF0000"; // Red for Player 1
        } else {
            bgColor = "#FFFF00"; // Yellow for Player 2
        }
        mainLayout.setStyle("-fx-background-color: " + bgColor + ";");
    }
    
    /**
     * Creates numbered buttons (1-7) for column selection
     * @return HBox containing the column buttons
     */
    private HBox createColumnButtons() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        columnButtons = new Button[COLS];
        
        for (int i = 0; i < COLS; i++) {
            final int col = i;
            Button btn = new Button(String.valueOf(i + 1));
            btn.setPrefSize(60, 40);
            btn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                        "-fx-background-color: #3498DB; -fx-text-fill: white; " +
                        "-fx-background-radius: 5;");
            
            // Hover effect
            btn.setOnMouseEntered(e -> 
                btn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                           "-fx-background-color: #5DADE2; -fx-text-fill: white; " +
                           "-fx-background-radius: 5;"));
            btn.setOnMouseExited(e -> 
                btn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; " +
                           "-fx-background-color: #3498DB; -fx-text-fill: white; " +
                           "-fx-background-radius: 5;"));
            
            // Click handler
            btn.setOnAction(e -> handleColumnClick(col));
            
            columnButtons[i] = btn;
            buttonBox.getChildren().add(btn);
        }
        
        return buttonBox;
    }
    
    /**
     * Handles clicking a column button to drop a coin
     * @param col Column index (0-6)
     */
    private void handleColumnClick(int col) {
        if (isAnimating) {
            return; // Don't allow moves during animation
        }
        
        if (!boardModel.isColumnAvailable(col)) {
            updateStatus("Column " + (col + 1) + " is full! Choose another.");
            return;
        }
        
        // Drop the piece in the model
        int row = boardModel.dropPiece(col, boardModel.getCurrentPlayer());
        
        if (row != -1) {
            // Start animation
            animateCoinDrop(row, col, boardModel.getCurrentPlayer());
        }
    }
    
    /**
     * Animates a coin dropping into a column with physics
     * @param targetRow Final row position
     * @param col Column position
     * @param player Player number (1 or 2)
     */
    private void animateCoinDrop(int targetRow, int col, int player) {
        isAnimating = true;
        disableButtons();
        
        // Calculate target Y position (where coin should land)
        double targetY = BOARD_START_Y + (targetRow * (CELL_SIZE + 5)) + CELL_SIZE / 2;
        
        // Starting position (above the board)
        double startX = 50 + col * (CELL_SIZE + 5) + CELL_SIZE / 2 - 14; // Shifted 10px left
        double startY = BOARD_START_Y - 50;
        