package Model;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.BestFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {
    private Maze maze;
    private Solution solution;
    private Position playerPosition;
    private IMazeGenerator mazeGenerator;
    private ISearchingAlgorithm searchingAlgorithm;

    public MyModel() {
        this.mazeGenerator = new MyMazeGenerator();
        this.searchingAlgorithm = new BestFirstSearch();
    }

    @Override
    public void assignObserver(Observer Observer) {
        this.addObserver(Observer);
    }

    private void setMaze(Maze maze) {
        this.maze = maze;
        this.playerPosition = this.maze.getStartPosition();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        //TODO: call the server!!
        this.setMaze(mazeGenerator.generate(rows, cols));
        setChanged();
        notifyObservers(ModelResponses.MazeGenerated);
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    @Override
    public void solveMaze() {
        //TODO: call the server!!
        solution = this.searchingAlgorithm.solve(new SearchableMaze(this.maze));
        setChanged();
        notifyObservers(ModelResponses.MazeSolved);
    }

    @Override
    public Solution getMazeSolution() {
        return this.solution;
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {
        Position newPosition;
        switch (direction) {
            case UP -> newPosition = this.playerPosition.getUpPosition(); //UP
            case UP_RIGHT -> newPosition = this.playerPosition.getUpRightPosition();
            case RIGHT -> newPosition = this.playerPosition.getRightPosition(); //RIGHT
            case DOWN_RIGHT -> newPosition = this.playerPosition.getDownRightPosition();
            case DOWN -> newPosition = this.playerPosition.getDownPosition(); //DOWN
            case DOWN_LEFT -> newPosition = this.playerPosition.getDownLeftPosition();
            case LEFT -> newPosition = this.playerPosition.getLeftPosition(); //LEFT
            case UP_LEFT -> newPosition = this.playerPosition.getUpLeftPosition();
            default -> {
                return;
            }
        }
        if (this.maze.validMazePosition(newPosition) && this.maze.positionOfTile(newPosition)) {
            this.playerPosition = newPosition;
            setChanged();
            notifyObservers(ModelResponses.MoveAllowed);
            if (this.playerPosition.equals(this.maze.getGoalPosition()))
                this.finishGame();
        } else {
            setChanged();
            notifyObservers(ModelResponses.MoveNotAllowed);
        }
    }

    @Override
    public void finishGame() {
        setChanged();
        notifyObservers(ModelResponses.Finish);
        //TODO: how to goal?
    }

    @Override
    public Position getPlayerPosition() {
        return this.playerPosition;
    }

    public void saveMaze(File chosen){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chosen))){
            out.writeObject(this.maze);
        } catch (IOException e) {
            System.out.println("Maze haven't saved");
        }
    }

    public void loadMaze(File chosen) {
        Maze tempMaze = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(chosen))){
            tempMaze = (Maze)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Maze not allowed");
            return;
        }
        this.setMaze(tempMaze);
        setChanged();
        notifyObservers(ModelResponses.MazeLoaded);
    }
}
