package Frontend.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StageGenerator {

    private static Stage mainStage;
    private static Stage properties;
    private static Stage video;
    private static Stage helpAbout;

    public static Stage getInstance(StageName stageName) {
        switch (stageName) {
            case Main -> {
                if (mainStage == null)
                    mainStage = new Stage();
                return mainStage;
            }
            case Properties -> {
                if (properties == null) {
                    properties = new Stage();
                    properties.initModality(Modality.APPLICATION_MODAL);
                }
                return properties;
            }
            case Video -> {
                if (video == null)
                    video = new Stage();
                return video;
            }

            case Help -> {
                if (helpAbout == null)
                    helpAbout = new Stage();
                return helpAbout;
            }
        }
        return null;
    }

    public static void setMainStage(Stage other) {
        if (mainStage == null)
            mainStage = other;
    }

    public static void startMain() {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource("./MyView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage primaryStage = StageGenerator.getInstance(StageGenerator.StageName.Main);
        primaryStage.getIcons().add(new Image("./Icons/icon.png"));
        primaryStage.setTitle("ATP-Project - Dora The Explorer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, Color.BLACK));
        primaryStage.show();
        primaryStage.requestFocus();
    }

    public static void startVideo() {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource("./VideoPlayer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Video);
        stage.getIcons().add(new Image("./Icons/icon.png"));
        stage.setTitle("ATP-Project - Dora The Explorer");
        stage.setResizable(true);
        stage.setScene(new Scene(root, Color.BLACK));
        stage.show();
        stage.requestFocus();
    }

    public static void startHelp() {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource("./Help.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Video);
        stage.getIcons().add(new Image("./Icons/icon.png"));
        stage.setTitle("ATP-Project - Dora The Explorer");
        stage.setResizable(false);
        stage.setScene(new Scene(root, Color.BLACK));
        stage.show();
        stage.requestFocus();
    }

    public static void startAbout() {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource("./About.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Video);
        stage.getIcons().add(new Image("./Icons/icon.png"));
        stage.setTitle("ATP-Project - Dora The Explorer");
        stage.setResizable(false);
        stage.setScene(new Scene(root, Color.BLACK));
        stage.show();
        stage.requestFocus();
    }

    public enum StageName {
        Main,
        Properties,
        Video,
        Help
    }
}
