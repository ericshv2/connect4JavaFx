import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameScreen {
    
    private Stage stage;
    private Board board;
    private int currentPlayer; // 1 or 2
    private GridPane gridPane;
    private Label statusLabel;
    private boolean gameOver;
    private int[][] winningPositions;
    private MediaPlayer bgMusicPlayer;
    private MediaPlayer sfxPlayer;
    
    public GameScreen(Stage stage) {
        this.stage = stage;
        this.board = new Board();
        this.currentPlayer = 1;
        this.gameOver = false;
        this.winningPositions = null;
        
        // Start background music
        playBackgroundMusic();
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
        gridPane.setStyle("-fx-background-color: #87CEEB; -fx-padding: 10;");
        
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
        layout.setStyle("-fx-background-color: #87CEEB;");
        
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
    
    // Draw the game board
    private void drawBoard() {
        gridPane.getChildren().clear();
        
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                int cellValue = board.getCell(row, col);
                
                // Check if this position is a winning position
                boolean isWinning = false;
                if (winningPositions != null) {
                    for (int[] pos : winningPositions) {
                        if (pos[0] == row && pos[1] == col) {
                            isWinning = true;
                            break;
                        }
                    }
                }
                
                // Create circle for coin
                Circle circle = new Circle(40);
                circle.setStroke(Color.BLACK);
                circle.setStrokeWidth(2);
                
                if (cellValue == 0) {
                    circle.setFill(Color.WHITE);
                } else if (cellValue == 1) {
                    circle.setFill(Color.RED);
                } else {
                    circle.setFill(Color.YELLOW);
                }
                
                // If this is a winning coin, add a star on top
                if (isWinning) {
                    StackPane stackPane = new StackPane();
                    
                    // Create a star shape
                    Polygon star = new Polygon();
                    star.getPoints().addAll(
                        0.0, -15.0,
                        4.0, -5.0,
                        15.0, -5.0,
                        6.0, 2.0,
                        10.0, 12.0,
                        0.0, 6.0,
                        -10.0, 12.0,
                        -6.0, 2.0,
                        -15.0, -5.0,
                        -4.0, -5.0
                    );
                    star.setFill(Color.GOLD);
                    star.setStroke(Color.ORANGE);
                    star.setStrokeWidth(1.5);
                    
                    stackPane.getChildren().addAll(circle, star);
                    gridPane.add(stackPane, col, row);
                } else {
                    gridPane.add(circle, col, row);
                }
            }
        }
    }
    
    // Handle a move
    private void handleMove(int col) {
        // Try to drop coin
        int row = board.dropCoin(col, currentPlayer);
        
        if (row == -1) {
            // Column is full
            statusLabel.setText("Column is full! Try another column.");
            return;
        }
        
        // Play coin drop sound effect
        if (currentPlayer == 1) {
            playSoundEffect("src/main/resources/red_coin.mp3");
        } else {
            playSoundEffect("src/main/resources/yellow_coin.mp3");
        }
        
        // Update the visual board
        drawBoard();
        
        // Check for win
        if (board.checkWin(currentPlayer)) {
            gameOver = true;
            winningPositions = board.getWinningPositions(currentPlayer);
            drawBoard(); // Redraw to show stars
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            playSoundEffect("src/main/resources/win.mp3");
            return;
        }
        
        // Check for tie
        if (board.isFull()) {
            gameOver = true;
            statusLabel.setText("It's a Tie!");
            return;
        }
        
        // Switch player
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        String color = (currentPlayer == 1) ? "Red" : "Yellow";
        statusLabel.setText("Player " + currentPlayer + "'s Turn (" + color + ") - Press 1-7");
    }
    
    // Restart the game
    private void restartGame() {
        board.reset();
        currentPlayer = 1;
        gameOver = false;
        winningPositions = null;
        statusLabel.setText("Player 1's Turn (Red) - Press 1-7");
        drawBoard();
    }
    
    // Play background music
    private void playBackgroundMusic() {
        try {
            File musicFile = new File("src/main/resources/bg_music.mp3");
            Media music = new Media(musicFile.toURI().toString());
            bgMusicPlayer = new MediaPlayer(music);
            bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop forever
            bgMusicPlayer.setVolume(0.1); // Set volume to 10%
            bgMusicPlayer.play();
        } catch (Exception e) {
            System.out.println("Background music not found or error playing: " + e.getMessage());
        }
    }
    
    // Play sound effect
    private void playSoundEffect(String filePath) {
        try {
            File soundFile = new File(filePath);
            Media sound = new Media(soundFile.toURI().toString());
            sfxPlayer = new MediaPlayer(sound);
            sfxPlayer.setVolume(0.5); // Set volume to 50%
            sfxPlayer.play();
        } catch (Exception e) {
            System.out.println("Sound effect not found: " + filePath);
        }
    }
}