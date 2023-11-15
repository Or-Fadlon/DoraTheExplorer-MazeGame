package Backend.algorithms.search;

import java.util.ArrayList;

/**
 * Searchable problem interface
 */
public interface ISearchable {
    /**
     * @return start state
     */
    AState getStartState();

    /**
     * @return goal state
     */
    AState getGoalState();

    /**
     * @param state a valid state in the problem
     * @return all the possible next states of the given state
     */
    ArrayList<AState> getAllSuccessors(AState state);
}
