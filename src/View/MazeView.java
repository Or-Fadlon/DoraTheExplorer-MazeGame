package View;

import Model.ModelResponses;
import Model.MovementDirection;
import ViewModel.MyViewModel;
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
import java.util.Observable;
import java.util.Observer;

public class MazeView extends AView implements Observer {
    public MazeCanvasDisplay mazeCanvasDisplay;
    public BorderPane borderPane;
    public MenuBar topBar;
    private int rows = 0, cols = 0;
    private double lastMouseX = 0, lastMouseY = 0;
    private boolean dragDetected = false;

    public MazeView() {
        this.myViewModel = new MyViewModel();
        this.myViewModel.addObserver(this);
        TopBar.setState(TopBar.GameState.Play);
        TopBar.setMazeView(this);
    }

    public void addResizeListener() {
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Main);
        stage.setResizable(true);
        stage.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.mazeCanvasDisplay.setWidth((double) newVal);
            this.mazeCanvasDisplay.resizeHandle();
        });
        stage.getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            this.mazeCanvasDisplay.setHeight((double) newVal);
            this.mazeCanvasDisplay.resizeHandle();
        });
    }

    private void addCloseProperties() {
        StageGenerator.getInstance(StageGenerator.StageName.Main).setOnCloseRequest(windowEvent -> {
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

    public void generateNewMaze() {
        this.generateNewMaze(this.rows, this.cols);
    }

    public void generateNewMaze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mazeCanvasDisplay.setSolution(null);
        this.myViewModel.generateMaze(rows, cols);
        this.addResizeListener();
        this.addCloseProperties();
    }

    private void finishGame() {
        this.mazeCanvasDisplay.finish();
    }

    private void solveMaze() {
        MazeAudioPlayer.getInstance().stopAll();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        this.myViewModel.solveMaze();
        alert.hide();
    }

    public void saveMaze() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
//        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showSaveDialog(null);
        if (chosen != null)
            this.myViewModel.saveMaze(chosen);
    }

    public void loadMaze() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
//        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        this.myViewModel.loadMaze(chosen);
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.S) {
            this.solveMaze();
            MazeAudioPlayer.getInstance().play(MazeAudioPlayer.MazeSound.Solution);
        } else if (keyEvent.getCode() == KeyCode.M)
            this.mazeCanvasDisplay.toggleFreeCamera();
        else {
            MovementDirection direction = null;
            switch (keyEvent.getCode()) {
                case NUMPAD8, UP -> direction = MovementDirection.UP; //UP
                case NUMPAD9 -> direction = MovementDirection.UP_RIGHT;
                case NUMPAD6, RIGHT -> direction = MovementDirection.RIGHT; //RIGHT
                case NUMPAD3 -> direction = MovementDirection.DOWN_RIGHT;
                case NUMPAD2, DOWN -> direction = MovementDirection.DOWN; //DOWN
                case NUMPAD1 -> direction = MovementDirection.DOWN_LEFT;
                case NUMPAD4, LEFT -> direction = MovementDirection.LEFT; //LEFT
                case NUMPAD7 -> direction = MovementDirection.UP_LEFT;
            }
            this.mazeCanvasDisplay.setPlayerDirection(direction);
            this.myViewModel.movePlayer(keyEvent);
        }
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
            if (mouseEvent.isControlDown())
                this.mazeCanvasDisplay.moveFreeCamera(diffX, diffY, scale);
            else {
                myViewModel.movePlayer(diffX, diffY, scale);
                if (-diffY >= scale)
                    this.mazeCanvasDisplay.setPlayerDirection(MovementDirection.UP);
                if (diffX >= scale)
                    this.mazeCanvasDisplay.setPlayerDirection(MovementDirection.RIGHT);
                if (diffY >= scale)
                    this.mazeCanvasDisplay.setPlayerDirection(MovementDirection.DOWN);
                if (-diffX >= scale)
                    this.mazeCanvasDisplay.setPlayerDirection(MovementDirection.LEFT);

            }
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
