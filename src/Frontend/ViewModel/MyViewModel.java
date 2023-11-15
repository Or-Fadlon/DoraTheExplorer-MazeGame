package Frontend.ViewModel;

import Backend.algorithms.search.Solution;
import Frontend.Model.MovementDirection;
import Frontend.Model.MyModel;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.Position;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private MyModel model;

    public MyViewModel() {
        this.model = new MyModel();
        this.model.assignObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void generateMaze(int rows, int cols) {
        this.model.generateMaze(rows, cols);
    }

    public Maze getMaze() {
        return this.model.getMaze();
    }

    public void solveMaze() {
        this.model.solveMaze();
    }

    public Solution getMazeSolution() {
        return this.model.getMazeSolution();
    }

    public void movePlayer(KeyEvent keyEvent) {
        MovementDirection direction;
        switch (keyEvent.getCode()) {
            case NUMPAD8, UP -> direction = MovementDirection.UP; //UP
            case NUMPAD9 -> direction = MovementDirection.UP_RIGHT;
            case NUMPAD6, RIGHT -> direction = MovementDirection.RIGHT; //RIGHT
            case NUMPAD3 -> direction = MovementDirection.DOWN_RIGHT;
            case NUMPAD2, DOWN -> direction = MovementDirection.DOWN; //DOWN
            case NUMPAD1 -> direction = MovementDirection.DOWN_LEFT;
            case NUMPAD4, LEFT -> direction = MovementDirection.LEFT; //LEFT
            case NUMPAD7 -> direction = MovementDirection.UP_LEFT;
            default -> {
                return;
            }
        }
        this.model.updatePlayerLocation(direction);
    }

    public void movePlayer(double diffX, double diffY, double scale) {
        if (-diffY >= scale)
            this.model.updatePlayerLocation(MovementDirection.UP);
        if (diffX >= scale)
            this.model.updatePlayerLocation(MovementDirection.RIGHT);
        if (diffY >= scale)
            this.model.updatePlayerLocation(MovementDirection.DOWN);
        if (-diffX >= scale)
            this.model.updatePlayerLocation(MovementDirection.LEFT);
    }

    public Position getPlayerPosition() {
        return this.model.getPlayerPosition();
    }

    public void loadMaze(File chosen) {
        this.model.loadMaze(chosen);
    }

    public void saveMaze(File chosen) {
        this.model.saveMaze(chosen);
    }

    public void exit() {
        this.model.stopServers();
    }
}
