package View;

import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MyViewController implements IView {
    @FXML
    public TextField textFieldMazeRows;
    public TextField textFieldMazeColumns;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void generateMazeButton(ActionEvent actionEvent) {
        FXMLLoader loader = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/MazeDisplayer.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int rows = Integer.parseInt(textFieldMazeRows.getText());
        int cols = Integer.parseInt(textFieldMazeColumns.getText());
        MazeDisplayerController mazeDisplayerController = new MazeDisplayerController();

        stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
