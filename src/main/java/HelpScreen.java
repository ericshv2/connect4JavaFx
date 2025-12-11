import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelpScreen {
    
    private Stage stage;
    
    public HelpScreen(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        // Create title
        Label titleLabel = new Label("How to Play Connect 4");
        titleLabel.setFont(new Font("Arial", 28));
        titleLabel.setStyle("-fx-font-weight: bold;");
        
        // Create instructions
        Label instructions1 = new Label("1. Two players take turns: Red and Yellow");
        instructions1.setFont(new Font("Arial", 16));
        
        Label instructions2 = new Label("2. Press keys 1-7 to drop your coin in that column");
        instructions2.setFont(new Font("Arial", 16));
        
        Label instructions3 = new Label("3. Coins stack on top of each other");
        instructions3.setFont(new Font("Arial", 16));
        
        Label instructions4 = new Label("4. Get 4 coins in a row to WIN!");
        instructions4.setFont(new Font("Arial", 16));
        
        Label instructions5 = new Label("5. You can win horizontally, vertically, or diagonally");
        instructions5.setFont(new Font("Arial", 16));
        
        // Create back button
        Button backButton = new Button("Back to Menu");
        backButton.setFont(new Font("Arial", 18));
        backButton.setPrefSize(150, 40);
        
        // When back button is clicked, go back to start screen
        backButton.setOnAction(e -> {
            StartScreen startScreen = new StartScreen(stage);
            startScreen.show();
        });
        
        // Create layout
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, instructions1, instructions2, 
                                     instructions3, instructions4, instructions5, backButton);
        layout.setStyle("-fx-background-color: #87CEEB;");
        
        // Create scene
        Scene scene = new Scene(layout, 700, 600);
        stage.setScene(scene);
    }
}