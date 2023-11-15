package Backend.algorithms.maze3D;

import Backend.algorithms.search.AState;
import Backend.algorithms.search.ISearchable;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    private final Maze3D maze;
    private final Maze3DState startState, goalState;

    /**
     * constructor
     *
     * @param maze 3D maze to solve
     * @throws IllegalArgumentException -> maze == null
     */
    public SearchableMaze3D(Maze3D maze) throws IllegalArgumentException {
        if (maze == null)
            throw new IllegalArgumentException("cant handle null maze");
        this.maze = maze;
        this.startState = new Maze3DState(null, maze.getStartPosition(), 0);
        this.goalState = new Maze3DState(null, maze.getGoalPosition(), 0);
    }

    @Override
    public AState getStartState() {
        return this.startState;
    }

    @Override
    public AState getGoalState() {
        return this.goalState;
    }

    /**
     * all optional 1 block moves in the maze from the given state
     *
     * @param state a valid state in the problem
     * @return all the possible next states of the given state
     */
    @Override
    public ArrayList<AState> getAllSuccessors(AState state) {
        ArrayList<AState> possibleStates = new ArrayList<>();
        if (state == null)
            return possibleStates;
        Position3D currentPosition = (Position3D) state.getCurrentState();
        if (maze.validMazePosition(currentPosition.getUpPosition()) && !maze.positionOfWall(currentPosition.getUpPosition())) // UP
            possibleStates.add(new Maze3DState(state, currentPosition.getUpPosition(), 0));
        if (maze.validMazePosition(currentPosition.getRightPosition()) && !maze.positionOfWall(currentPosition.getRightPosition())) // RIGHT
            possibleStates.add(new Maze3DState(state, currentPosition.getRightPosition(), 0));
        if (maze.validMazePosition(currentPosition.getDownPosition()) && !maze.positionOfWall(currentPosition.getDownPosition())) // DOWN
            possibleStates.add(new Maze3DState(state, currentPosition.getDownPosition(), 0));
        if (maze.validMazePosition(currentPosition.getLeftPosition()) && !maze.positionOfWall(currentPosition.getLeftPosition())) // LEFT
            possibleStates.add(new Maze3DState(state, currentPosition.getLeftPosition(), 0));
        if (maze.validMazePosition(currentPosition.getHigherPosition()) && !maze.positionOfWall(currentPosition.getHigherPosition())) // HIGH
            possibleStates.add(new Maze3DState(state, currentPosition.getHigherPosition(), 0));
        if (maze.validMazePosition(currentPosition.getLowerPosition()) && !maze.positionOfWall(currentPosition.getLowerPosition())) //LOW
            possibleStates.add(new Maze3DState(state, currentPosition.getLowerPosition(), 0));

        return possibleStates;
    }
}
