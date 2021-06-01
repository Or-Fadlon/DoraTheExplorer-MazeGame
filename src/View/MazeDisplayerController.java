package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MazeDisplayerController implements Initializable {
    public MazeDisplayer mazeDisplayer;
    public MyMazeGenerator generator = new MyMazeGenerator();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mazeDisplayer.requestFocus();
    }

    public void generateMaze(int rows, int cols) {
        Maze maze = generator.generate(rows, cols);
        mazeDisplayer.drawMaze(maze);
        setPlayerPosition(maze.getStartPosition());
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
    }

    public void openFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        //...
    }

    public void setPlayerPosition(Position position) {
        mazeDisplayer.setPlayerPosition(position);
    }

    public void keyPressed(KeyEvent keyEvent) {
        int row = mazeDisplayer.getPlayerRow();
        int col = mazeDisplayer.getPlayerCol();

        switch (keyEvent.getCode()) {
            case UP -> row -= 1;
            case DOWN -> row += 1;
            case RIGHT -> col += 1;
            case LEFT -> col -= 1;
        }
//        setPlayerPosition(row, col);

        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }
}
