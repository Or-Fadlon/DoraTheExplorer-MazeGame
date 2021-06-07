package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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

    public void movePlayer(MouseEvent mouseEvent, double mousePosX, double mousePosY) {
        if (mouseEvent.getY() <= mousePosY)
            this.model.updatePlayerLocation(MovementDirection.UP);
        else if (mouseEvent.getX() >= mousePosX)
            this.model.updatePlayerLocation(MovementDirection.RIGHT);
        else if (mouseEvent.getY() >= mousePosY)
            this.model.updatePlayerLocation(MovementDirection.DOWN);
        else if (mouseEvent.getX() <= mousePosX)
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
