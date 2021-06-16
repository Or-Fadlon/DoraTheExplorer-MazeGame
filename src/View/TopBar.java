package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class TopBar {
    public void newOne(ActionEvent actionEvent) {
        System.out.println("new");
    }

    public void save(ActionEvent actionEvent) {
        System.out.println("new");
    }

    public void load(ActionEvent actionEvent) {
        System.out.println("new");
    }

    public void properties(ActionEvent actionEvent) {
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Properties);
        stage.setResizable(false);
        stage.setTitle("Properties");
        stage.getIcons().add(new Image(getClass().getResource("/Icons/icon.png").toString()));
        MazeAudioPlayer.getInstance().play(MazeAudioPlayer.MazeSound.Properties);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                MazeAudioPlayer.getInstance().play(MazeAudioPlayer.MazeSound.BackGround);
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PlayerProperties.fxml"));
        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(10);
    }

    public void help(MouseEvent mouseEvent) {
    }

    public void about(MouseEvent mouseEvent) {
    }
}
