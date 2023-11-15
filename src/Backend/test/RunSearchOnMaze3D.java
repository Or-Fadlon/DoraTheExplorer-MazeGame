package Backend.test;

import Backend.algorithms.maze3D.*;
import Backend.algorithms.search.*;

import java.util.ArrayList;

public class RunSearchOnMaze3D {
    public static void main(String[] args) {
        IMaze3DGenerator mg = new MyMaze3DGenerator();
//        Maze3D maze = mg.generate(100, 100, 100);
        int[][][] map = {
                {
                        {0, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {0, 1, 1, 0, 0}},
                {
                        {1, 1, 1, 0, 1},
                        {1, 0, 0, 1, 0},
                        {0, 0, 1, 0, 1}},
                {
                        {1, 1, 1, 0, 1},
                        {1, 1, 0, 0, 0},
                        {1, 1, 1, 0, 1}}
        };
        Maze3D maze = new Maze3D(map, new Position3D(0, 0, 0), new Position3D(0, 2, 4));

        SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);
//        maze.printColored();
        maze.print();

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
//        for (int i = 0; i < solutionPath.size(); i++) {
//            System.out.println(String.format("%s.%s", i, solutionPath.get(i)));
//        }
        System.out.printf("%s = %s%n", searcher.getName(), solution.getCost());
        System.out.printf("%s.%s%n", 0, solutionPath.get(0));
        System.out.printf("%s.%s%n", solutionPath.size() - 1, solutionPath.get(solutionPath.size() - 1));

    }
}
