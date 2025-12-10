import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create the start screen
        StartScreen startScreen = new StartScreen(primaryStage);
        startScreen.show();
        
        // Set up the window
        primaryStage.setTitle("Connect 4 Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}