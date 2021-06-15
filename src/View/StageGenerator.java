package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class StageGenerator {

    private static Stage mainStage;
    private static Stage properties;

    public static Stage getInstance(StageName stageName) {
        switch (stageName) {
            case Main -> {
                if (mainStage == null)
                    mainStage = new Stage();
                return mainStage;
            }
            case Properties -> {
                if (properties == null)
                    properties = new Stage();
                return properties;
            }
        }
        return null;
    }

    public static void setMainStage(Stage other) {
        if (mainStage == null)
            mainStage = other;
    }

    public static void changeMainScene(String viewPath) {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource(viewPath));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage primaryStage = StageGenerator.getInstance(StageGenerator.StageName.Main);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, Color.BLACK));
        primaryStage.show();
        primaryStage.requestFocus();
    }

    public enum StageName {
        Main,
        Properties
    }
}
