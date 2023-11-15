package Frontend.Model;

import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.Position;
import Backend.algorithms.search.Solution;

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
