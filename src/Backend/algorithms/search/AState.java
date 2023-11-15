package Backend.algorithms.search;

import java.io.Serializable;

/**
 * abstract State in searching problem
 */
public abstract class AState implements Serializable {

    private final AState prevState;
    private final Object currentState;
    private final int cost;

    public AState(AState prevState, Object currentState, int cost) {
        this.currentState = currentState;
        this.prevState = prevState;
        this.cost = cost;
    }

    public AState getPrevState() {
        return prevState;
    }

    public Object getCurrentState() {
        return currentState;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AState aState = (AState) o;
        return currentState != null ? currentState.equals(aState.currentState) : aState.currentState != null;
    }

    @Override
    public String toString() {
        return this.currentState.toString();
    }
}
