package Backend.algorithms.mazeGenerators;

/**
 * abstract 2D maze generator
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime, finishTime;
        startTime = System.currentTimeMillis();
        generate(rows, columns);
        finishTime = System.currentTimeMillis();
        return finishTime - startTime;
    }
}
