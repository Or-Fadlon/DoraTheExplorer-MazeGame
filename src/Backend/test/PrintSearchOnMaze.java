package Backend.test;

import Backend.algorithms.mazeGenerators.IMazeGenerator;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.MyMazeGenerator;
import Backend.algorithms.mazeGenerators.Position;
import Backend.algorithms.search.*;

import java.util.ArrayList;
import java.util.HashSet;

public class PrintSearchOnMaze {
    public static void main(String[] args) {
        final int rows = 20, columns = 40;
        IMazeGenerator mg = new MyMazeGenerator();
        System.out.printf("Maze generation time(ms): %s %n", mg.measureAlgorithmTimeMillis(rows, columns));
        Maze maze = mg.generate(rows, columns);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        maze.printColored();
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.printf("'%s' algorithm - nodes evaluated:%s%n", searcher.getName(), searcher.getNumberOfNodesEvaluated());
        //Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();

        HashSet<Position> trace = new HashSet<>();
        for (AState state : solutionPath)
            trace.add((Position) state.getCurrentState());
        ((SearchableMaze) domain).getMaze().printColoredTrace(trace);

//        for (int i = 0; i < solutionPath.size(); i++) {
//            System.out.printf("%s.%s%n", i, solutionPath.get(i));
//        }
        System.out.printf("%s = %s%n", searcher.getName(), solution.getCost());
        System.out.println(String.format("%s.%s", 0, solutionPath.get(0)));
        System.out.println(String.format("%s.%s", solutionPath.size() - 1, solutionPath.get(solutionPath.size() - 1)));

    }
}