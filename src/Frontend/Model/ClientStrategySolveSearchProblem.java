package Frontend.Model;

import Backend.Client.IClientStrategy;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.search.Solution;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ClientStrategySolveSearchProblem implements IClientStrategy {
    private Maze maze;
    private Solution mazeSolution;

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Solution getSolution() {
        return this.mazeSolution;
    }

    @Override
    public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
            ObjectInputStream fromServer = new ObjectInputStream(inputStream);
            toServer.writeObject(maze);
            toServer.flush();
            this.mazeSolution = (Solution) fromServer.readObject();
        } catch (Exception var9) {
            var9.printStackTrace();
        }
    }
}
