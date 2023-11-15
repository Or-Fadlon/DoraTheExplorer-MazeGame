package Backend.algorithms.search;

/**
 * search algorithm interface
 */
public interface ISearchingAlgorithm {
    /**
     * @param domain Searchable problem
     * @return Solution to the given problem
     */
    public Solution solve(ISearchable domain);

    /**
     * @return name of the algorithm
     */
    public String getName();

    /**
     * @return number of nodes the algorithm visited
     */
    public int getNumberOfNodesEvaluated();
}
