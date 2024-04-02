package Frontend.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
            root = FXMLLoader.load(AView.class.getResource("/resources/FXML/MyView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage primaryStage = StageGenerator.getInstance(StageGenerator.StageName.Main);
        URL iconPath = AView.class.getResource("/resources/Icons/icon.png");
        if (iconPath != null) {
            primaryStage.getIcons().add(new Image(iconPath.toString()));
        } else {
            System.out.println("Error - Icon not found");
        }
        primaryStage.setTitle("ATP-Project - Dora The Explorer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, Color.BLACK));
        primaryStage.show();
        primaryStage.requestFocus();
    }

    public static void startVideo() {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource("/resources/FXML/VideoPlayer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Video);
        URL iconPath = AView.class.getResource("/resources/Icons/icon.png");
        if (iconPath != null) {
            stage.getIcons().add(new Image(iconPath.toString()));
        } else {
            System.out.println("Error - Icon not found");
        }
        stage.setTitle("ATP-Project - Dora The Explorer");
        stage.setResizable(true);
        stage.setScene(new Scene(root, Color.BLACK));
        stage.show();
        stage.requestFocus();
    }

    public static void startHelp() {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource("/resources/FXML/Help.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Video);
        URL iconPath = AView.class.getResource("/resources/Icons/icon.png");
        if (iconPath != null) {
            stage.getIcons().add(new Image(iconPath.toString()));
        } else {
            System.out.println("Error - Icon not found");
        }
        stage.setTitle("ATP-Project - Dora The Explorer");
        stage.setResizable(false);
        stage.setScene(new Scene(root, Color.BLACK));
        stage.show();
        stage.requestFocus();
    }

    public static void startAbout() {
        Parent root;
        try {
            root = FXMLLoader.load(AView.class.getResource("/resources/FXML/About.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Video);
        URL iconPath = AView.class.getResource("/resources/Icons/icon.png");
        if (iconPath != null) {
            stage.getIcons().add(new Image(iconPath.toString()));
        } else {
            System.out.println("Error - Icon not found");
        }
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
