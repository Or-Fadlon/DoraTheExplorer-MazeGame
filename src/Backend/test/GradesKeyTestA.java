package Backend.test;

import Backend.algorithms.maze3D.Maze3D;
import Backend.algorithms.maze3D.MyMaze3DGenerator;
import Backend.algorithms.maze3D.SearchableMaze3D;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.MyMazeGenerator;
import Backend.algorithms.mazeGenerators.SimpleMazeGenerator;
import Backend.algorithms.search.BestFirstSearch;
import Backend.algorithms.search.BreadthFirstSearch;
import Backend.algorithms.search.DepthFirstSearch;
import Backend.algorithms.search.SearchableMaze;

public class GradesKeyTestA {
    public static void main(String[] args) {
        //1 : Generate Simple maze [2,2] and checking printing of maze
        Maze simpleMaze2x2 = (new SimpleMazeGenerator()).generate(2, 2);
        simpleMaze2x2.print();
        // 2 : Generate My maze [2,2] and checking printing of maze
        Maze myMaze2x2 = (new MyMazeGenerator()).generate(2, 2);
        myMaze2x2.print();
        // 3 : Generate Simple maze [2,3]
        Maze simpleMaze2x3 = (new SimpleMazeGenerator()).generate(2, 3);
        simpleMaze2x3.print();
        //4 : Generate My maze [2,3]
        Maze myMaze2x3 = (new MyMazeGenerator()).generate(2, 3);
        myMaze2x3.print();
        //5 : Generate Simple maze [3,3]
        Maze simpleMaze3x3 = (new SimpleMazeGenerator()).generate(3, 3);
        simpleMaze3x3.print();
        //6 : Generate My maze [3,3]
        Maze myMaze3x3 = (new MyMazeGenerator()).generate(3, 3);
        myMaze3x3.print();
        //7 : Generate Simple maze [3,2]
        Maze simpleMaze3x2 = (new SimpleMazeGenerator()).generate(3, 2);
        simpleMaze3x2.print();
        //8 : Generate My maze [3,2]
        Maze myMaze3x2 = (new MyMazeGenerator()).generate(3, 2);
        myMaze3x2.print();
        //9 : Generate Simple maze [5,6]
        Maze simpleMaze5x6 = (new SimpleMazeGenerator()).generate(5, 6);
        simpleMaze5x6.print();
        //10 : Generate My maze [5,6]
        Maze myMaze5x6 = (new MyMazeGenerator()).generate(5, 6);
        myMaze5x6.print();
        //11 : Generate Simple maze [57,67]
        Maze simpleMaze57x67 = (new SimpleMazeGenerator()).generate(57, 67);
        simpleMaze57x67.print();
        //12 : Generate My maze [57,67]
        Maze myMaze57x67 = (new MyMazeGenerator()).generate(57, 67);
        myMaze57x67.print();
        //13 : Generate Simple maze [50,10]
        Maze simpleMaze50x10 = (new SimpleMazeGenerator()).generate(50, 10);
        simpleMaze50x10.print();
        //14 : Generate My maze [50,10]
        Maze myMaze50x10 = (new MyMazeGenerator()).generate(50, 10);
        myMaze50x10.print();
        //15 : Generate Simple maze [111,111]
        Maze simpleMaze111x111 = (new SimpleMazeGenerator()).generate(111, 111);
        //16 : Checking 60 second for Simple maze [111,111]
        long timeSimple111x111 = (new SimpleMazeGenerator()).measureAlgorithmTimeMillis(111, 111);
        System.out.println(timeSimple111x111 + " -> " + (timeSimple111x111 < 60000));
        //17 : Generate My maze [111,111]
        Maze myMaze111x111 = (new MyMazeGenerator()).generate(111, 111);
        //18 : Checking 60 second for My maze [111,111]
        long timeMy111x111 = (new MyMazeGenerator()).measureAlgorithmTimeMillis(111, 111);
        System.out.println(timeMy111x111 + " -> " + (timeMy111x111 < 60000));
        //19 : Generate Simple maze [999,999]
        Maze simpleMaze999x999 = (new SimpleMazeGenerator()).generate(999, 999);
        //20 : Checking 60 second for Simple maze [999,999]
        long timeSimple999x999 = (new SimpleMazeGenerator()).measureAlgorithmTimeMillis(999, 999);
        System.out.println(timeSimple999x999 + " -> " + (timeSimple999x999 < 60000));
        //21 : Generate My maze [999,999]
        Maze myMaze999x999 = (new MyMazeGenerator()).generate(999, 999);
        //22 : Checking 60 second for My maze [999,999]
        long timeMy999x999 = (new MyMazeGenerator()).measureAlgorithmTimeMillis(999, 999);
        System.out.println(timeMy999x999 + " -> " + (timeMy999x999 < 60000));


        // 1 : Generate My maze [2,2,2] and checking printing of maze
        Maze3D myMaze2x2x2 = (new MyMaze3DGenerator()).generate(2, 2, 2);
        myMaze2x2x2.print();
        // 2 : Generate My maze [2,2,2] and checking printing of maze
        Maze3D myMaze3x2x2 = (new MyMaze3DGenerator()).generate(3, 2, 2);
        myMaze3x2x2.print();
        // 3 : Generate My maze [2,3,2] and checking printing of maze
        Maze3D myMaze2x3x2 = (new MyMaze3DGenerator()).generate(2, 3, 2);
        myMaze2x3x2.print();
        // 4 : Generate My maze [2,2,3] and checking printing of maze
        Maze3D myMaze2x2x3 = (new MyMaze3DGenerator()).generate(2, 2, 3);
        myMaze2x2x3.print();
        // 5 : Generate My maze [3,3,3] and checking printing of maze
        Maze3D myMaze3x3x3 = (new MyMaze3DGenerator()).generate(3, 3, 3);
        myMaze3x3x3.print();
        // 6 : Generate My maze [5,6,7] and checking printing of maze
        Maze3D myMaze5x6x7 = (new MyMaze3DGenerator()).generate(5, 6, 7);
        myMaze5x6x7.print();
        // 7 : Generate My maze [47,57,67] and checking printing of maze
        Maze3D myMaze47x57x67 = (new MyMaze3DGenerator()).generate(47, 57, 67);
        // 8 : Generate My maze [100,100,100] and checking printing of maze
        Maze3D myMaze100x100x100 = (new MyMaze3DGenerator()).generate(100, 100, 100);
        // 9 : Checking 60 second for My maze [100,100,100]
        long timeMy100x100x100 = (new MyMaze3DGenerator()).measureAlgorithmTimeMillis(100, 100, 100);
        System.out.println(timeMy100x100x100 + " -> " + (timeMy100x100x100 < 60000));

        ///
        BreadthFirstSearch BFS = new BestFirstSearch();
        DepthFirstSearch DFS = new DepthFirstSearch();
        BestFirstSearch Best = new BestFirstSearch();
        ///

        //23 : Solving maze [2,2], using BFS
        System.out.println("BFS Solve MyMaze2X2: ");
        BFS.solve(new SearchableMaze(myMaze2x2)).print();
        // 24 : Solving maze [2,2], using DFS
        System.out.println("DFS Solve MyMaze2X2: ");
        DFS.solve(new SearchableMaze(myMaze2x2)).print();
        // 25 : Solving maze [2,2], using Best
        System.out.println("Best Solve MyMaze2X2: ");
        Best.solve(new SearchableMaze(myMaze2x2)).print();
        // 26 : Solving maze [2,3], using BFS
        System.out.println("BFS Solve MyMaze2X3: ");
        BFS.solve(new SearchableMaze(myMaze2x3)).print();
        // 27 : Solving maze [2,3], using DFS
        System.out.println("DFS Solve MyMaze2X3: ");
        DFS.solve(new SearchableMaze(myMaze2x3)).print();
        // 28 : Solving maze [2,3], using Best
        System.out.println("Best Solve MyMaze2X3: ");
        Best.solve(new SearchableMaze(myMaze2x3)).print();
        // 29 : Solving maze [3,3], using BFS
        System.out.println("BFS Solve MyMaze3X3: ");
        BFS.solve(new SearchableMaze(myMaze3x3)).print();
        // 30 : Solving maze [3,3], using DFS
        System.out.println("DFS Solve myMaze3x3: ");
        DFS.solve(new SearchableMaze(myMaze3x3)).print();
        // 31 : Solving maze [3,3], using Best
        System.out.println("Best Solve myMaze3x3: ");
        Best.solve(new SearchableMaze(myMaze3x3)).print();
        // 32 : Solving maze [3,2], using BFS
        System.out.println("BFS Solve MyMaze3X2: ");
        BFS.solve(new SearchableMaze(myMaze3x2)).print();
        // 33 : Solving maze [3,2], using DFS
        System.out.println("DFS Solve myMaze3x2: ");
        DFS.solve(new SearchableMaze(myMaze3x2)).print();
        // 34 : Solving maze [3,2], using Best
        System.out.println("Best Solve myMaze3x2: ");
        Best.solve(new SearchableMaze(myMaze3x2)).print();
        // 35 : Solving maze [5,6], using BFS
        System.out.println("BFS Solve MyMaze5X6: ");
        BFS.solve(new SearchableMaze(myMaze5x6)).print();
        // 36 : Solving maze [5,6], using DFS
        System.out.println("DFS Solve myMaze5x6: ");
        DFS.solve(new SearchableMaze(myMaze5x6)).print();
        // 37 : Solving maze [5,6], using Best
        System.out.println("Best Solve myMaze5x6: ");
        Best.solve(new SearchableMaze(myMaze5x6)).print();
        // 38 : Solving maze [57,67], using BFS
        System.out.println("BFS Solve myMaze57x67: ");
        BFS.solve(new SearchableMaze(myMaze57x67));
        // 39 : Solving maze [57,67], using DFS
        System.out.println("DFS Solve myMaze57x67: ");
        DFS.solve(new SearchableMaze(myMaze57x67));
        // 40 : Solving maze [57,67], using Best
        System.out.println("Best Solve myMaze57x67: ");
        Best.solve(new SearchableMaze(myMaze57x67));
        // 41 : Solving maze [50,10], using BFS
        System.out.println("BFS Solve myMaze50x10: ");
        BFS.solve(new SearchableMaze(myMaze50x10));
        // 42 : Solving maze [50,10], using DFS
        System.out.println("DFS Solve myMaze50x10: ");
        DFS.solve(new SearchableMaze(myMaze50x10));
        // 43 : Solving maze [50,10], using Best
        System.out.println("Best Solve myMaze50x10: ");
        Best.solve(new SearchableMaze(myMaze50x10));
        // 44 : Solving maze [111,111], using BFS
        System.out.println("BFS Solve myMaze111x111: ");
        BFS.solve(new SearchableMaze(myMaze111x111));
        // 45 : Solving maze [111,111], using DFS
        System.out.println("DFS Solve myMaze111x111: ");
        DFS.solve(new SearchableMaze(myMaze111x111));
        // 46 : Solving maze [111,111], using Best
        System.out.println("Best Solve myMaze111x111: ");
        Best.solve(new SearchableMaze(myMaze111x111));
        // 47 : Solving maze [999,999], using BFS
        System.out.println("BFS Solve myMaze999x999: ");
        BFS.solve(new SearchableMaze(myMaze999x999));
        // 48 : Solving maze [999,999], using DFS
        System.out.println("DFS Solve myMaze999x999: ");
        DFS.solve(new SearchableMaze(myMaze999x999));
        // 49 : Solving maze [999,999], using Best
        System.out.println("Best Solve myMaze999x999: ");
        Best.solve(new SearchableMaze(myMaze999x999));
        // 50 : Solving maze [2,2,2], using Best
        System.out.println("Best Solve myMaze2x2x2: ");
        Best.solve(new SearchableMaze3D(myMaze2x2x2)).print();
        // 51 : Solving maze [3,3,3], using Best
        System.out.println("Best Solve myMaze3x3x3: ");
        Best.solve(new SearchableMaze3D(myMaze3x3x3)).print();
        // 52 : Solving maze [5,6,7], using Best
        System.out.println("Best Solve myMaze5x6x7: ");
        Best.solve(new SearchableMaze3D(myMaze5x6x7)).print();
        // 53 : Solving maze [47,57,67], using Best
        System.out.println("Best Solve myMaze47x57x67: ");
        Best.solve(new SearchableMaze3D(myMaze47x57x67));
        // 53 : Solving maze [100,100,100], using Best
        System.out.println("Best Solve myMaze47x57x67: ");
        Best.solve(new SearchableMaze3D(myMaze100x100x100));
    }
}
