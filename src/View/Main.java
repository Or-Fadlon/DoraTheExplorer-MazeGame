package View;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StageGenerator.setMainStage(primaryStage);
        primaryStage.hide();
        StageGenerator.startVideo();


//        primaryStage.setX(150);
//        primaryStage.setY(150);
//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitHint("Let's Go!!!");

    }
}
