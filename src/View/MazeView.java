package View;

import Model.IModel;
import Model.ModelResponses;
import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Position;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MazeView implements Initializable, Observer {
    public MazeCanvasDisplay mazeCanvasDisplay;
    public MenuBar TopBar;
    private MyViewModel myViewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IModel model = new MyModel();
        this.myViewModel = new MyViewModel(model);
        this.myViewModel.addObserver(this);
        mazeCanvasDisplay.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((ModelResponses) arg) {
            case MazeGenerated -> this.mazeCanvasDisplay.drawNewMaze(this.myViewModel.getMaze(), this.myViewModel.getPlayerPosition());
            case MazeSolved -> this.mazeCanvasDisplay.setSolution(this.myViewModel.getMazeSolution());
            case MoveAllowed -> this.mazeCanvasDisplay.setPlayerPosition(this.myViewModel.getPlayerPosition());
//            case MoveNotAllowed -> //TODO:Make Sound;
            case Finish -> this.finishGame();
        }
    }

    public void generateNewMaze(int rows, int cols) {
        this.myViewModel.generateMaze(rows, cols);
        //TODO: UPDATE DRAW
        //mazeCanvasDisplay.drawNewMaze(maze);
    }

    private void finishGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Finish!");
        alert.show();
    }

    private void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        this.myViewModel.solveMaze();
    }

    private void openFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        //...
    }

    public void setPlayerPosition(Position position) {
        mazeCanvasDisplay.setPlayerPosition(position);
    }

    public void keyPressed(KeyEvent keyEvent) {
        this.myViewModel.movePlayer(keyEvent);
        //TODO: smother move with speed (see older games)
        keyEvent.consume();
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        mazeCanvasDisplay.requestFocus();
    }
}
