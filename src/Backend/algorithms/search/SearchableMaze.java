package Backend.algorithms.search;

import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    private final Maze maze;
    private final MazeState startState, goalState;

    /**
     * constructor
     *
     * @param maze 3D maze to solve
     * @throws IllegalArgumentException -> maze == null
     */
    public SearchableMaze(Maze maze) throws IllegalArgumentException {
        if (maze == null)
            throw new IllegalArgumentException("cant handle null maze");
        this.maze = maze;
        this.startState = new MazeState(null, maze.getStartPosition(), 0);
        this.goalState = new MazeState(null, maze.getGoalPosition(), 0);
    }

    /**
     * @return maze we try to solve
     */
    public Maze getMaze() {
        return maze;
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
        Position currentPosition = (Position) state.getCurrentState();
        boolean upWall = maze.positionOfWall(currentPosition.getUpPosition()),
                downWall = maze.positionOfWall(currentPosition.getDownPosition()),
                rightWall = maze.positionOfWall(currentPosition.getRightPosition()),
                leftWall = maze.positionOfWall(currentPosition.getLeftPosition());
        if (maze.validMazePosition(currentPosition.getUpRightPosition()) && !maze.positionOfWall(currentPosition.getUpRightPosition()) && (!upWall || !rightWall)) //UP-RIGHT
            possibleStates.add(new MazeState(state, currentPosition.getUpRightPosition(), state.getCost() + 15));
        if (maze.validMazePosition(currentPosition.getDownRightPosition()) && !maze.positionOfWall(currentPosition.getDownRightPosition()) && (!downWall || !rightWall)) //DOWN-RIGHT
            possibleStates.add(new MazeState(state, currentPosition.getDownRightPosition(), state.getCost() + 15));
        if (maze.validMazePosition(currentPosition.getUpLeftPosition()) && !maze.positionOfWall(currentPosition.getUpLeftPosition()) && (!upWall || !leftWall)) //UP-LEFT
            possibleStates.add(new MazeState(state, currentPosition.getUpLeftPosition(), state.getCost() + 15));
        if (maze.validMazePosition(currentPosition.getDownLeftPosition()) && !maze.positionOfWall(currentPosition.getDownLeftPosition()) && (!downWall || !leftWall)) //DOWN-LEFT
            possibleStates.add(new MazeState(state, currentPosition.getDownLeftPosition(), state.getCost() + 15));
        if (maze.validMazePosition(currentPosition.getUpPosition()) && !upWall) //UP
            possibleStates.add(new MazeState(state, currentPosition.getUpPosition(), state.getCost() + 10));
        if (maze.validMazePosition(currentPosition.getDownPosition()) && !downWall) //DOWN
            possibleStates.add(new MazeState(state, currentPosition.getDownPosition(), state.getCost() + 10));
        if (maze.validMazePosition(currentPosition.getRightPosition()) && !rightWall) //RIGHT
            possibleStates.add(new MazeState(state, currentPosition.getRightPosition(), state.getCost() + 10));
        if (maze.validMazePosition(currentPosition.getLeftPosition()) && !leftWall) //LEFT
            possibleStates.add(new MazeState(state, currentPosition.getLeftPosition(), state.getCost() + 10));

        return possibleStates;
    }
}
