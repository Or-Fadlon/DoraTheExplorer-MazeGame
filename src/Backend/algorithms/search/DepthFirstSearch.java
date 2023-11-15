package Backend.algorithms.search;

import java.util.HashSet;
import java.util.Stack;

/**
 * Depth First Search algorithm
 */
public class DepthFirstSearch extends ASearchingAlgorithm {
    public DepthFirstSearch() {
        super("Depth First Search");
    }

    /***
     *procedure DFS_iterative(G, v) is
     * 1     let S be a stack
     * 2     S.push(v)
     * 3     while S is not empty do
     * 3.1         v = S.pop()
     * 3.2         if v is not labeled as discovered then
     * 3.2.1          label v as discovered
     * 3.2.2          for all edges from v to w in G.adjacentEdges(v) do
     *                    S.push(w)
     *
     * @param domain Searchable problem
     * @return solution to the given problem; empty Solution if solution not found
     * @throws IllegalArgumentException null -> cant solve NULL problem.
     */
    @Override
    public Solution solve(ISearchable domain) throws IllegalArgumentException {
        if (domain == null)
            throw new IllegalArgumentException("cant solve NULL problem");
        HashSet<String> visited = new HashSet<>();
        Stack<AState> stack = new Stack<>(); //1
        AState currentState;
        stack.push(domain.getStartState()); //2

        while (!stack.isEmpty()) { //3
            currentState = stack.pop(); //3.1
            if (currentState.equals(domain.getGoalState())) { //goal state found -> return Solution
                this.NumberOfNodesEvaluated = visited.size();
                return new Solution(currentState);
            }
            if (!visited.contains(currentState.toString())) { //3.2
                visited.add(currentState.toString()); //3.2.1
                stack.addAll(domain.getAllSuccessors(currentState)); //3.2.2
            }
        }
        this.NumberOfNodesEvaluated = visited.size();
        return new Solution();
    }
}
