package Backend.algorithms.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Breadth First Search algorithm
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    private Queue<AState> queue = new LinkedList<>();

    public BreadthFirstSearch() {
        super("Breadth First Search");
    }

    protected BreadthFirstSearch(String name, Queue<AState> queue) {
        super(name);
        this.queue = queue;
    }

    /**
     * procedure BFS(G, root) is
     * 1     let Q be a queue
     * 2      label root as discovered
     * 3      Q.enqueue(root)
     * 4      while Q is not empty do
     * 4.1          v := Q.dequeue()
     * 4.2          if v is the goal then
     * return v
     * 4.3          for all edges from v to w in G.adjacentEdges(v) do
     * 4.3.1              if w is not labeled as discovered then
     * label w as discovered
     * Q.enqueue(w)
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
        AState currentState;

        this.queue.clear(); //1
        visited.add(domain.getStartState().toString()); //2
        queue.add(domain.getStartState()); //3
        while (!queue.isEmpty()) { //4
            currentState = queue.remove(); //4.1
            if (currentState.equals(domain.getGoalState())) { //4.2
                this.NumberOfNodesEvaluated = visited.size();
                return new Solution(currentState);
            }
            for (AState a : domain.getAllSuccessors(currentState)) { //4.3
                if (!visited.contains(a.toString())) { //4.3.1
                    visited.add(a.toString());
                    queue.add(a);
                }
            }
        }
        this.NumberOfNodesEvaluated = visited.size();
        return new Solution();
    }
}
