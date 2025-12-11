import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class StartScreen {
    
    private Stage stage;
    private MediaPlayer sfxPlayer;
    
    public StartScreen(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        // Create welcome message
        Label welcomeLabel = new Label("Welcome to Connect 4!");
        welcomeLabel.setFont(new Font("Arial", 32));
        
        // Create instructions
        Label instructionsLabel = new Label("Press keys 1-7 to drop your coin");
        instructionsLabel.setFont(new Font("Arial", 16));
        
        // Create start button
        Button startButton = new Button("Start Game");
        startButton.setFont(new Font("Arial", 20));
        startButton.setPrefSize(150, 50);
        
        // When start button is clicked, show the game screen
        startButton.setOnAction(e -> {
            playButtonSound();
            GameScreen gameScreen = new GameScreen(stage);
            gameScreen.show();
        });
        
        // Create help button
        Button helpButton = new Button("How to Play");
        helpButton.setFont(new Font("Arial", 20));
        helpButton.setPrefSize(150, 50);
        
        // When help button is clicked, show the help screen
        helpButton.setOnAction(e -> {
            playButtonSound();
            HelpScreen helpScreen = new HelpScreen(stage);
            helpScreen.show();
        });
        
        // Create layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcomeLabel, instructionsLabel, startButton, helpButton);
        layout.setStyle("-fx-background-color: #87CEEB;");
        
        // Create scene
        Scene scene = new Scene(layout, 700, 600);
        stage.setScene(scene);
    }
    
    // Play button click sound
    private void playButtonSound() {
        try {
            File soundFile = new File("src/main/resources/button_press.mp3");
            Media sound = new Media(soundFile.toURI().toString());
            sfxPlayer = new MediaPlayer(sound);
            sfxPlayer.setVolume(0.5);
            sfxPlayer.play();
        } catch (Exception e) {
            System.out.println("Button sound not found: " + e.getMessage());
        }
    }
}