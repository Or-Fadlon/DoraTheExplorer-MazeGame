package Backend.test;

import Backend.IO.MyCompressorOutputStream;
import Backend.IO.MyDecompressorInputStream;
import Backend.algorithms.mazeGenerators.AMazeGenerator;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class GradesKeyTestB {
    public static void main(String[] args) {
        compDecomp(50, 50);
        compDecomp(100, 100);
        compDecomp(150, 150);
        compDecomp(200, 200);
        compDecomp(250, 250);
        compDecomp(300, 300);
        compDecomp(350, 350);
        compDecomp(400, 400);
        compDecomp(450, 450);
        compDecomp(500, 500);
    }

    private static void compDecomp(int x, int y) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(x, y); //Generate new maze
        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals = maze.equals(loadedMaze);
        System.out.println(String.format("Mazes equal: %s", areMazesEquals));
    }
}
