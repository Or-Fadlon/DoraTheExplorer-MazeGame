package View;

import Model.ModelResponses;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Position;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MazeView extends AView implements Initializable {
    public MazeCanvasDisplay mazeCanvasDisplay;
    public BorderPane borderPane;
    public MenuBar TopBar;
    double lastMouseX = 0, lastMouseY = 0;
    boolean dragDetected = false;

    public MazeView() {
        this.myViewModel = new MyViewModel();
        this.myViewModel.addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void addResizeListener() {
        ((Stage) this.borderPane.getScene().getWindow()).setResizable(true);
        this.borderPane.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.mazeCanvasDisplay.setWidth((double) newVal); //TODO: handle min size
            this.mazeCanvasDisplay.resizeHandle();
        });
        this.borderPane.getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            this.mazeCanvasDisplay.setHeight((double) newVal); //TODO: handle min size
            this.mazeCanvasDisplay.resizeHandle();
        });
    }

    private void addCloseProperties() {
        this.mazeCanvasDisplay.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            this.myViewModel.exit();
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((ModelResponses) arg) {
            case MazeGenerated, MazeLoaded -> this.mazeCanvasDisplay.drawNewMaze(this.myViewModel.getMaze(), this.myViewModel.getPlayerPosition());
            case MazeSolved -> this.mazeCanvasDisplay.setSolution(this.myViewModel.getMazeSolution());
            case MoveAllowed -> this.mazeCanvasDisplay.setPlayerPosition(this.myViewModel.getPlayerPosition());
            case MoveNotAllowed -> this.mazeCanvasDisplay.wallHit();
            case Finish -> this.finishGame();
        }
    }

    public void generateNewMaze(int rows, int cols) {
        this.myViewModel.generateMaze(rows, cols);
        this.addResizeListener();//TODO: We need to move it!!
        this.addCloseProperties();//TODO: We need to move it!!
        //TODO: UPDATE DRAW
        //mazeCanvasDisplay.drawNewMaze(maze);
    }

    private void finishGame() {
        this.mazeCanvasDisplay.finish();
    }

    private void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        this.myViewModel.solveMaze();
        alert.hide();
    }

    private void saveMaze(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showSaveDialog(null);
        if (chosen != null)
            this.myViewModel.saveMaze(chosen);
    }

    private void loadMaze(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        this.myViewModel.loadMaze(chosen);
    }

    public void setPlayerPosition(Position position) {
        mazeCanvasDisplay.setPlayerPosition(position);
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.S) {
            this.myViewModel.solveMaze();
            MazeMediaPlayer.getInstance().play(MazeMediaPlayer.MazeSound.Solution);
        }
        else if (keyEvent.getCode() == KeyCode.L) //TODO: remove
            this.loadMaze(new ActionEvent());
        else if (keyEvent.getCode() == KeyCode.K) //TODO: remove
            this.saveMaze(new ActionEvent());
        else
            this.myViewModel.movePlayer(keyEvent);
        //TODO: smother move with speed (see older games)
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeCanvasDisplay.requestFocus();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        double diffX = mouseEvent.getX() - lastMouseX, diffY = mouseEvent.getY() - lastMouseY;
        double scale = this.mazeCanvasDisplay.getScale();
        if (!dragDetected) {
            dragDetected = true;
            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();
        } else if (Math.abs(diffX) >= scale ||
                Math.abs(diffY) >= scale) {
            myViewModel.movePlayer(diffX, diffY, scale);
            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();
        }
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        dragDetected = false;
    }

    public void scrollHandle(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY > 0)
                this.mazeCanvasDisplay.zoom = this.mazeCanvasDisplay.getScale() < 100 ? this.mazeCanvasDisplay.zoom * 1.1 : this.mazeCanvasDisplay.zoom;
            else
                this.mazeCanvasDisplay.zoom = this.mazeCanvasDisplay.getScale() > 10 ? this.mazeCanvasDisplay.zoom / 1.1 : this.mazeCanvasDisplay.zoom;
            this.mazeCanvasDisplay.resizeHandle();
        }
        scrollEvent.consume();
    }

}
