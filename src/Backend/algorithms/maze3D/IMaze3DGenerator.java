package Backend.algorithms.maze3D;

/**
 * 3D maze generator interface
 */
public interface IMaze3DGenerator {
    Maze3D generate(int depth, int row, int column);

    /**
     * measure the time take to generate maze
     *
     * @param depth  number of layers
     * @param row    number of rows
     * @param column number of columns
     * @return the time takes in MilliSeconds
     */
    long measureAlgorithmTimeMillis(int depth, int row, int column);

}
