import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class - Entry point for Connect 4 game
 * Sets up the JavaFX application and launches the game
 * 
 * @author Eric
 * @date Dec 1, 2025
 */
public class Main extends Application {
    
    /**
     * Start method - initializes the primary stage and shows the game board
     * @param primaryStage The main window of the application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the game board UI
            GameBoard gameBoard = new GameBoard();
            
            // Set up the stage
            primaryStage.setTitle("Connect 4 Game");
            primaryStage.setScene(gameBoard.getScene());
            primaryStage.setResizable(false);
            primaryStage.show();
            
            System.out.println("Connect 4 Game launched successfully!");
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Main method - launches the JavaFX application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}