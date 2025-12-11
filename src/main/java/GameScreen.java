@Author Ajitesh
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
        statusLabel.setFont(new Font("Arial", 20));
        
        // Create column numbers (1-7) above the board
        HBox columnNumbers = new HBox(5);
        columnNumbers.setAlignment(Pos.CENTER);
        for (int i = 1; i <= 7; i++) {
            Label numLabel = new Label(String.valueOf(i));
            numLabel.setFont(new Font("Arial", 24));
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
        gridPane.setStyle("-fx-background-color: #0066CC; -fx-padding: 10;");
        
        // Create the visual board
        drawBoard();