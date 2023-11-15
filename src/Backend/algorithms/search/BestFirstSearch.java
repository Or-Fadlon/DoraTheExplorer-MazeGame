package Backend.algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Best First Search algorithm
 */
public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch() {
        super("Best First Search", new PriorityQueue<>(new priceComparator()));
    }
}

/**
 * Comparator for the PriorityQueue
 */
class priceComparator implements Comparator<AState> {

    /**
     * @param o1 AState to compare
     * @param o2 AState to compare
     * @return =0 - equals, >0 - o1 is bigger, <0 - o2 is bigger
     * @throws IllegalArgumentException one or more of the given AState is Null
     */
    @Override
    public int compare(AState o1, AState o2) throws IllegalArgumentException {
        if (o1 == null || o2 == null)
            throw new IllegalArgumentException("one or more of the given AState is Null");
        return Integer.compare(o1.getCost(), o2.getCost());
    }
}
