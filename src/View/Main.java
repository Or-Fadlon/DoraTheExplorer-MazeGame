package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StageGenerator.setMainStage(primaryStage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/VideoPlayer.fxml")));
        primaryStage.getIcons().add(new Image(getClass().getResource("/Icons/icon.png").toString()));
        primaryStage.setTitle("ATP-Project - Dora The Explorer");
        primaryStage.setResizable(false);
//        primaryStage.setX(150);
//        primaryStage.setY(150);
//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitHint("Let's Go!!!");

        primaryStage.setScene(new Scene(root, Color.BLACK));
        primaryStage.show();
        primaryStage.requestFocus();
    }
}
