package Backend.Server;

import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.search.ISearchingAlgorithm;
import Backend.algorithms.search.SearchableMaze;
import Backend.algorithms.search.Solution;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

/**
 * solving maze server strategy
 */
public class ServerStrategySolveSearchProblem extends AServerStrategy {

    private static final String directoryPath = System.getProperty("java.io.tmpdir") + "Maze-Project\\"; //temp folder
    private int counter;
    private HashSet<Integer> hashSet;

    /**
     * constructor
     * load older mazes that solved before
     */
    public ServerStrategySolveSearchProblem() {
        super("solve");
        this.hashSet = new HashSet<>();
        this.counter = 0;
        Maze loadedMaze;
        ObjectInputStream inMazeFile;
        File file;
        new File(directoryPath).mkdir();
        while (true) { //load all solved old
            file = new File(directoryPath + "maze" + counter + ".maze");
            if (file.exists()) {
                try {
                    inMazeFile = new ObjectInputStream(new FileInputStream(directoryPath + "maze" + counter + ".maze"));
                    loadedMaze = (Maze) inMazeFile.readObject();
                    hashSet.add(loadedMaze.hashCode());
                    counter++;
                } catch (IOException | ClassNotFoundException e) {
                    LOG.error(e.toString());
                    e.printStackTrace();
                    break;
                }
            } else
                break;
        }
    }

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) {
        Solution solution;

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Maze mazeFromClient = (Maze) fromClient.readObject();

            solution = searchForOlderSolution(mazeFromClient);

            if (solution == null) {
                solution = solveAndSave(mazeFromClient);
            }
            toClient.writeObject(solution);
            toClient.flush();
            toClient.close();
        } catch (ClassNotFoundException | IOException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * search for older solutions of the given maze.
     *
     * @param mazeFromClient maze to solve
     * @return Solution if found. otherwise - null
     */
    private Solution searchForOlderSolution(Maze mazeFromClient) {
        Solution solution = null;
        byte[] mazeFromClientAsArray = mazeFromClient.toByteArray();
        try {
            if (this.hashSet.contains(mazeFromClient.hashCode())) { //maybe solved
                Maze loadedMaze;
                ObjectInputStream inMazeFile;
                for (int i = 0; i < counter; i++) { //search for solve
                    inMazeFile = new ObjectInputStream(new FileInputStream(directoryPath + "maze" + i + ".maze"));
                    loadedMaze = (Maze) inMazeFile.readObject();
                    if (mazeFromClient.equals(loadedMaze)) {
                        ObjectInputStream inSolutionFile = new ObjectInputStream(new FileInputStream(directoryPath + "solvedMaze" + i + ".solution"));
                        return (Solution) inSolutionFile.readObject();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * solve given maze and save the solution to the directory
     *
     * @param mazeFromClient maze to solve
     * @return the given maze solution
     */
    private Solution solveAndSave(Maze mazeFromClient) {
        synchronized (this) {
            // now when this is lock recheck if we have older solution
            Solution solution;
            if (this.hashSet.contains(mazeFromClient.hashCode())) {
                solution = searchForOlderSolution(mazeFromClient);
                if (solution != null)
                    return solution;
            }


            try {
                //create ISearchingAlgorithm class using the name that in configuration file to solve the maze with.
                Class<?> mazeSearchClass = Class.forName("Backend.algorithms.search." + Configurations.getInstance().getMazeSearchingAlgorithm());
                ISearchingAlgorithm mazeSearchAlgorithm = (ISearchingAlgorithm) mazeSearchClass.getDeclaredConstructor().newInstance();
                solution = mazeSearchAlgorithm.solve(new SearchableMaze(mazeFromClient));
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                LOG.error(e.toString());
                e.printStackTrace();
                return null;
            }
            try {
                // save maze to a file
                ObjectOutputStream outToMazeFile = new ObjectOutputStream(new FileOutputStream(directoryPath + "maze" + counter + ".maze"));
                outToMazeFile.writeObject(mazeFromClient);
                outToMazeFile.flush();
                outToMazeFile.close();
                // save Solution to a file
                ObjectOutputStream outToSolutionFile = new ObjectOutputStream(new FileOutputStream(directoryPath + "solvedMaze" + counter++ + ".solution"));
                outToSolutionFile.writeObject(solution);
                outToSolutionFile.flush();
                outToSolutionFile.close();
                hashSet.add(mazeFromClient.hashCode());
            } catch (IOException e) {
                LOG.error(e.toString());
                e.printStackTrace();
            }
            return solution;
        }
    }
}
