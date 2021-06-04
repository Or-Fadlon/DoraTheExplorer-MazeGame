package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MyViewController implements IView {
    @FXML
    public TextField textFieldMazeRows;
    public TextField textFieldMazeColumns;
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
        int rows = Integer.parseInt(textFieldMazeRows.getText());
        int cols = Integer.parseInt(textFieldMazeColumns.getText());
        MazeView mazeView = loader.getController();
        mazeView.generateNewMaze(rows, cols);

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
    }
}
