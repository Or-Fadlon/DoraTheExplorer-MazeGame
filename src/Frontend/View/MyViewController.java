package Frontend.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyViewController extends AView implements Initializable {
    public Label easyArrow;
    public Label mediumArrow;
    public Label hardArrow;
    public MenuBar topBar;
    public ImageView imageView;

    private GameDiff mode = GameDiff.Easy;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MazeAudioPlayer.getInstance().play(MazeAudioPlayer.MazeSound.BackGround);
        TopBar.setState(TopBar.GameState.Select);
        Image image = new Image(getClass().getResourceAsStream("/Images/select.png"));
        imageView.setImage(image);
    }

    public void generateMazeButton(ActionEvent actionEvent) {
        int rows = 0, cols = 0;
        switch (this.mode) {
            case Easy -> {
                CharSelect.rows = 15;
                CharSelect.cols = 15;
            }
            case Medium -> {
                CharSelect.rows = 30;
                CharSelect.cols = 30;
            }
            case Hard -> {
                CharSelect.rows = 50;
                CharSelect.cols = 50;
            }
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("./CharecterSelect.fxml"));
            StageGenerator.getInstance(StageGenerator.StageName.Main).setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch ((keyEvent.getCode())) {
            case W, NUMPAD8, UP -> this.changeUp();
            case S, NUMPAD2, DOWN -> this.changeDown();
            case ENTER -> this.generateMazeButton(null);
        }
        keyEvent.consume();
    }

    private void changeUp() {
        switch (this.mode) {
            case Easy -> {
                this.mode = GameDiff.Hard;
                this.easyArrow.setStyle("visibility: hide");
                this.hardArrow.setStyle("visibility: visible");
            }
            case Medium -> {
                this.mode = GameDiff.Easy;
                this.mediumArrow.setStyle("visibility: hide");
                this.easyArrow.setStyle("visibility: visible");
            }
            case Hard -> {
                this.mode = GameDiff.Medium;
                this.hardArrow.setStyle("visibility: hide");
                this.mediumArrow.setStyle("visibility: visible");
            }
        }
    }

    private void changeDown() {
        switch (this.mode) {
            case Easy -> {
                this.mode = GameDiff.Medium;
                this.easyArrow.setStyle("visibility: hide");
                this.mediumArrow.setStyle("visibility: visible");
            }
            case Medium -> {
                this.mode = GameDiff.Hard;
                this.mediumArrow.setStyle("visibility: hide");
                this.hardArrow.setStyle("visibility: visible");
            }
            case Hard -> {
                this.mode = GameDiff.Easy;
                this.hardArrow.setStyle("visibility: hide");
                this.easyArrow.setStyle("visibility: visible");
            }
        }
    }

    private enum GameDiff {
        Easy,
        Medium,
        Hard
    }
}
