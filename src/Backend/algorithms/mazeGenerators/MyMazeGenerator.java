package Backend.algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {
    /**
     * maze generator using one of the knowing algorithms:
     *
     * @param rows    number of rows of the maze to generate
     * @param columns number of columns of the maze to generate
     * @return generated maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        if (rows == 2 || columns == 2)
            return primsMazeGenerator(rows, columns);
        return DFSMazeGenerator(rows, columns);
    }

    /**
     * maze generator using Prims algorithm:
     * <p>
     * 1. Start with a grid full of walls.
     * 2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
     * 3. While there are walls in the list:
     * 3.1.     Pick a random wall from the list.
     * 3.2.     If only one of the two cells that the wall divides is visited, then:
     * 3.2.1.       Make the wall a passage and mark the unvisited cell as part of the maze.
     * 3.2.2.       Add the neighboring walls of the cell to the wall list.
     * 3.3.     Remove the wall from the list.
     *
     * @param rows    number of rows of the maze to generate
     * @param columns number of columns of the maze to generate
     * @return generated maze
     */
    private Maze primsMazeGenerator(int rows, int columns) {
        Random random = new Random();
        Maze maze = new Maze(rows, columns);
        ArrayList<Position> wallsList;
        ArrayList<Position> neighbourTiles;
        maze.makeAllWalls(); //1
        maze.generateStartPosition();
        Position currentPosition = maze.getStartPosition();
        maze.removeWall(currentPosition);
        wallsList = new ArrayList<>(maze.getNeighbourWalls(currentPosition)); //2
        while (!wallsList.isEmpty()) { //3
            currentPosition = wallsList.remove(random.nextInt(wallsList.size()));
            neighbourTiles = maze.getNeighbourTiles(currentPosition);
            if (neighbourTiles.size() == 1) { //3.1
                maze.removeWall(currentPosition);
                wallsList.addAll(maze.getNeighbourWalls(currentPosition));
            }
        }

        maze.generateGoalPosition();
        return maze;
    }

    /**
     * maze generator using iterative DFS algorithm:
     * <p>
     * 1. Choose the initial cell, mark it as visited and push it to the stack
     * 2. While the stack is not empty
     * 2.1.     Pop a cell from the stack and make it a current cell
     * 2.2.     If the current cell has any neighbours which have not been visited
     * 2.2.1        Push the current cell to the stack
     * 2.2.2        Choose one of the unvisited neighbours
     * 2.2.3        Remove the wall between the current cell and the chosen cell
     * 2.2.4        Mark the chosen cell as visited and push it to the stack
     *
     * @param rows    number of rows of the maze to generate
     * @param columns number of columns of the maze to generate
     * @return generated maze
     */
    private Maze DFSMazeGenerator(int rows, int columns) {
        Random random = new Random();
        Maze maze = new Maze(rows, columns);
        Stack<Position> neighbours = new Stack<>();
        ArrayList<Position> neighbourWalls;
        maze.makeAllWalls();
        maze.generateStartPosition();
        Position currentPosition = maze.getStartPosition();
        maze.removeWall(currentPosition);
        neighbours.push(currentPosition);
        while (!neighbours.isEmpty()) {
            currentPosition = neighbours.pop();
            neighbourWalls = maze.wallsTwoBlocksAway(currentPosition);
            if (neighbourWalls.size() != 0) {
                neighbours.push(currentPosition);
                Position randNeighbour = neighbourWalls.get(random.nextInt(neighbourWalls.size()));
                maze.removeWall(randNeighbour);
                maze.connectNeighbours(currentPosition, randNeighbour);
                neighbours.push(randNeighbour);
            }
        }

        maze.generateGoalPosition();
        return maze;
    }
}
