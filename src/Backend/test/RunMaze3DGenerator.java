package Backend.test;

import Backend.algorithms.maze3D.IMaze3DGenerator;
import Backend.algorithms.maze3D.Maze3D;
import Backend.algorithms.maze3D.MyMaze3DGenerator;
import Backend.algorithms.maze3D.Position3D;

public class RunMaze3DGenerator {
    public static void main(String[] args) {
        testMazeGenerator(new MyMaze3DGenerator());
    }

    private static void testMazeGenerator(IMaze3DGenerator mazeGenerator) {
        final int depth = 3, rows = 10, columns = 10;
        // prints the time it takes the algorithm to run
        System.out.printf("Maze generation time(ms): %s %n", mazeGenerator.measureAlgorithmTimeMillis(depth, rows, columns));
        // generate another maze
        Maze3D maze = mazeGenerator.generate(depth, rows, columns);
//        int[][][] map = {
//                {
//                        {0, 0, 1, 0, 1},
//                        {0, 1, 1, 1, 0},
//                        {0, 1, 1, 0, 0}},
//                {
//                        {1, 1, 1, 0, 1},
//                        {1, 0, 0, 1, 0},
//                        {0, 0, 1, 0, 1}},
//                {
//                        {1, 1, 1, 0, 1},
//                        {1, 1, 0, 0, 0},
//                        {1, 1, 1, 0, 1}}
//        };
//        Maze3D maze = new Maze3D(map, new Position3D(0,0,0), new Position3D(0,2,4));
        // prints the maze
        maze.print();
        // get the maze entrance
        Position3D startPosition = maze.getStartPosition();
        // print the start position
        System.out.printf("Start Position: %s%n", startPosition); // format "{row,column}"
        // prints the maze exit position
        System.out.printf("Goal Position: %s%n", maze.getGoalPosition());
    }
}
