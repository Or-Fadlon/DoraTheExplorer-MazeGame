package Backend.algorithms.mazeGenerators;

import Backend.algorithms.mazeGenerators.Maze;

/**
 * 2D maze generator interface
 */
public interface IMazeGenerator {
    /***
     * generate a maze
     * @param rows number of rows of the maze to generate
     * @param columns number of columns of the maze to generate
     * @return 2D generated maze
     */
    Maze generate(int rows, int columns);

    /***
     * measure the time to generate maze algorithm
     * @param rows number of rows of the maze to generate
     * @param columns number of columns of the maze to generate
     * @return time takes to generate in Milliseconds
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}
