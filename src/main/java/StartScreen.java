import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class StartScreen {
    
    private Stage stage;
    
    public StartScreen(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        // Create welcome message
        Label welcomeLabel = new Label("Welcome to Connect 4!");
        welcomeLabel.setFont(new Font("Courier New", 32));
        
        // Create instructions
        Label instructionsLabel = new Label("Press keys 1-7 to drop your coin");
        instructionsLabel.setFont(new Font("Arial", 16));
        
        // Create start button
        Button startButton = new Button("Start Game");
        startButton.setFont(new Font("Arial", 20));
        startButton.setPrefSize(150, 50);
        
        // When start button is clicked, show the game screen
        startButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen(stage);
            gameScreen.show();
        });
        
        // Create layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcomeLabel, instructionsLabel, startButton);
        layout.setStyle("-fx-background-color: #10EB7A;");
        
        // Create scene
        Scene scene = new Scene(layout, 700, 600);
        stage.setScene(scene);
    }
}