package Backend.algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Solution of a problem
 */
public class Solution implements Serializable {
    private final ArrayList<AState> path;
    private int cost;

    /**
     * constructor
     *
     * @param goal the goal state after running a search algorithm.
     */
    public Solution(AState goal) {
        AState temp = goal;
        this.path = new ArrayList<>();
        while (temp != null) {
            this.path.add(temp);
            temp = temp.getPrevState();
        }
        this.cost = path.get(0).getCost();
        Collections.reverse(path);
    }

    /**
     * empty constructor
     */
    public Solution() {
        this.path = new ArrayList<>();
    }

    /**
     * @return ArrayList full of states of the problem solution
     */
    public ArrayList<AState> getSolutionPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    public void print() {
        for (int i = 0; i < this.path.size(); i++)
            System.out.printf("%s.%s%n", i, this.path.get(i));
    }
}
