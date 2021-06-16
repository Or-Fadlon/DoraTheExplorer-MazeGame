package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.util.Observer;

public interface IModel {
    void assignObserver(Observer Observer);

    void generateMaze(int rows, int cols);

    Maze getMaze();

    void solveMaze();

    Solution getMazeSolution();

    void updatePlayerLocation(MovementDirection direction);

    Position getPlayerPosition();

    void finishGame();
}
