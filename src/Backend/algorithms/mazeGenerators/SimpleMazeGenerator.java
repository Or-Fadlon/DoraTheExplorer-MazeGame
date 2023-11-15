package Backend.algorithms.mazeGenerators;

import java.util.Random;

/**
 * Simple maze generator
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * generate start and goal position.
     * make all the maze full of walls.
     * creates one main way from start position to goal position.
     * randomly remove walls to add more complexity to the maze.
     *
     * @param rows    number of rows of the maze to generate
     * @param columns number of columns of the maze to generate
     * @return Simple 2D maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        Random random = new Random();
        Maze maze = new Maze(rows, columns);
        Position currentPosition;
        maze.generateStartPosition();
        maze.generateGoalPosition();
        maze.makeAllWalls();
        currentPosition = maze.getStartPosition();
        maze.removeWall(currentPosition);
        maze.removeWall(maze.getGoalPosition());
        while (!currentPosition.equals(maze.getGoalPosition())) {
            if (random.nextInt(2) == 0) {
                if (currentPosition.getRowIndex() > maze.getGoalPosition().getRowIndex())
                    currentPosition = currentPosition.getUpPosition();
                if (currentPosition.getRowIndex() < maze.getGoalPosition().getRowIndex())
                    currentPosition = currentPosition.getDownPosition();
            } else {
                if (currentPosition.getColumnIndex() > maze.getGoalPosition().getColumnIndex())
                    currentPosition = currentPosition.getLeftPosition();
                if (currentPosition.getColumnIndex() < maze.getGoalPosition().getColumnIndex())
                    currentPosition = currentPosition.getRightPosition();
            }
            maze.removeWall(currentPosition);
        }
        for (int i = 0; i < maze.getRowsSize(); i++) {
            for (int j = 0; j < maze.getColumnsSize(); j++) {
                if (random.nextInt(2) == 0)
                    maze.removeWall(new Position(i, j));
            }
        }
        return maze;
    }
}

