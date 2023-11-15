package Backend.algorithms.search;

import Backend.algorithms.mazeGenerators.Position;

/**
 * 2D Maze problem state
 */
public class MazeState extends AState {
    public MazeState(AState prevState, Position stateName, int cost) {
        super(prevState, stateName, cost);
    }
}
