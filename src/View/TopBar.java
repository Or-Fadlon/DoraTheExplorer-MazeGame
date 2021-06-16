package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class TopBar {
    public static GameState gameState = GameState.Select;
    private static MazeView mazeView;
    public MenuItem newItem;
    public MenuItem saveItem;

    public static void setState(GameState state) {
        gameState = state;
    }

    public static void setMazeView(MazeView maze) {
        mazeView = maze;
    }

    public void newOne(ActionEvent actionEvent) {
        mazeView.generateNewMaze();
    }

    public void save(ActionEvent actionEvent) {
        mazeView.saveMaze();
    }

    public void load(ActionEvent actionEvent) {
        mazeView.loadMaze();
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

    public void updateState(MouseEvent mouseEvent) {
        boolean disable = gameState != GameState.Play;
        this.newItem.setDisable(disable);
        this.saveItem.setDisable(disable);
    }

    public enum GameState {
        Select,
        Play
    }
}
