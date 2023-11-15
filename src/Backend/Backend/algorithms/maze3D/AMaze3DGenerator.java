package Backend.Backend.algorithms.maze3D;

import Backend.algorithms.maze3D.IMaze3DGenerator;

/**
 * abstract 3D maze generator
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long startTime, finishTime;
        startTime = System.currentTimeMillis();
        generate(depth, row, column);
        finishTime = System.currentTimeMillis();
        return finishTime - startTime;
    }
}
