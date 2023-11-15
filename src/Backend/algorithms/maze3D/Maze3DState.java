package Backend.algorithms.maze3D;

import Backend.algorithms.search.AState;

/**
 * 2D Maze problem state
 */
public class Maze3DState extends AState {
    public Maze3DState(AState prevState, Position3D currentState, int cost) {
        super(prevState, currentState, cost);
    }
}
