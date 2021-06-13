package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MyViewController extends AView { //TODO: request focus!!!!!!
    public Label easyArrow;
    public Label mediumArrow;
    public Label hardArrow;

    private GameDiff mode = GameDiff.Easy;

    public void generateMazeButton(ActionEvent actionEvent) {
        //TODO: change and use ChangeMainScene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MazeDisplayer.fxml"));

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int rows = 0, cols = 0;

        switch (this.mode) {
            case Easy -> {
                rows = 15;
                cols = 15;
            }
            case Medium -> {
                rows = 30;
                cols = 30;
            }
            case Hard -> {
                rows = 50;
                cols = 50;
            }
        }

        MazeView mazeView = loader.getController();
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Main);
        stage.setScene(new Scene(root));
        stage.show();
        mazeView.generateNewMaze(rows, cols);
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch ((keyEvent.getCode())) {
            case NUMPAD8, UP -> this.changeUp();
            case NUMPAD2, DOWN -> this.changeDown();
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
