package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyViewController implements IView {
    public Label easyArrow;
    public Label mediumArrow;
    public Label hardArrow;

    private int mode = 0;
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void generateMazeButton(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MazeDisplayer.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int rows = 0, cols = 0;

        switch (this.mode) {
            case 0 -> {
                rows = 10;
                cols = 10;
            }
            case 1 -> {
                rows = 30;
                cols = 30;
            }
            case 2 -> {
                rows = 50;
                cols = 50;
            }
        }
        System.out.println(this.mode);

        MazeView mazeView = loader.getController();
        stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
        scene = new Scene(root);
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                System.out.println(keyEvent.getCode());
//            }
//        });
        stage.setScene(scene);
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
            case 0 -> {
                this.mode = 2;
                this.easyArrow.setStyle("visibility: hide");
                this.hardArrow.setStyle("visibility: visible");
            }
            case 1 -> {
                this.mode = 0;
                this.mediumArrow.setStyle("visibility: hide");
                this.easyArrow.setStyle("visibility: visible");
            }
            case 2 -> {
                this.mode = 1;
                this.hardArrow.setStyle("visibility: hide");
                this.mediumArrow.setStyle("visibility: visible");
            }
        }
    }

    private void changeDown() {
        switch (this.mode) {
            case 0 -> {
                this.mode = 1;
                this.easyArrow.setStyle("visibility: hide");
                this.mediumArrow.setStyle("visibility: visible");
            }
            case 1 -> {
                this.mode = 2;
                this.mediumArrow.setStyle("visibility: hide");
                this.hardArrow.setStyle("visibility: visible");
            }
            case 2 -> {
                this.mode = 0;
                this.hardArrow.setStyle("visibility: hide");
                this.easyArrow.setStyle("visibility: visible");
            }
        }
    }
}
