import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class GameScreen {
private Stage stage;
private Board board;
private int currentPlayer; // 1 or 2
private GridPane gridPane;
private Label statusLabel;
private boolean gameOver;

public GameScreen(Stage stage) {
    this.stage = stage;
    this.board = new Board();
    this.currentPlayer = 1;
    this.gameOver = false;
}

public void show() {
    // Create status label
    statusLabel = new Label("Player 1's Turn (Red) - Press 1-7");
    statusLabel.setFont(new Font("Courier New", 20));
    
    // Create column numbers (1-7) above the board
    HBox columnNumbers = new HBox(5);
    columnNumbers.setAlignment(Pos.CENTER);
    for (int i = 1; i <= 7; i++) {
        Label numLabel = new Label(String.valueOf(i));
        numLabel.setFont(new Font("Courier New", 24));
        numLabel.setStyle("-fx-font-weight: bold;");
        numLabel.setPrefWidth(85);
        numLabel.setAlignment(Pos.CENTER);
        columnNumbers.getChildren().add(numLabel);
    }
    
    // Create grid for the game board
    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(5);
    gridPane.setVgap(5);
    gridPane.setStyle("-fx-background-color: #84c5e0; -fx-padding: 10;");
    
    // Create the visual board
    drawBoard();
    
    // Create restart button
    Button restartButton = new Button("Restart Game");
    restartButton.setFont(new Font("Courier New", 16));
    restartButton.setOnAction(e -> restartGame());
    
    // Create main menu button
    Button menuButton = new Button("Main Menu");
    menuButton.setFont(new Font("Courier New", 16));
    menuButton.setOnAction(e -> {
        StartScreen startScreen = new StartScreen(stage);
        startScreen.show();
    });
    
    // Button layout
    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(restartButton, menuButton);
    
    // Main layout
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(statusLabel, columnNumbers, gridPane, buttonBox);
    layout.setStyle("-fx-background-color: #84c5e0;");
    
    // Create scene
    Scene scene = new Scene(layout, 800, 700);
    
    // Handle keyboard input (keys 1-7)
    scene.setOnKeyPressed(e -> {
        if (gameOver) return;
        
        String key = e.getText();
        int col = -1;
        
        // Convert key press to column number
        if (key.equals("1")) col = 0;
        else if (key.equals("2")) col = 1;
        else if (key.equals("3")) col = 2;
        else if (key.equals("4")) col = 3;
        else if (key.equals("5")) col = 4;
        else if (key.equals("6")) col = 5;
        else if (key.equals("7")) col = 6;
        
        if (col != -1) {
            handleMove(col);
        }
    });
    
    stage.setScene(scene);
}


