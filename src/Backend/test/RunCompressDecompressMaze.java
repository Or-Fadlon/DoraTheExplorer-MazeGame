package Backend.test;

import Backend.IO.MyCompressorOutputStream;
import Backend.IO.MyDecompressorInputStream;
import Backend.algorithms.mazeGenerators.AMazeGenerator;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(1000, 1000); //Generate new maze
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
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(), maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s", areMazesEquals));
//        System.out.println(Arrays.toString(loadedMaze.toByteArray()));
//        System.out.println(Arrays.toString(maze.toByteArray()));
        //maze should be equal to loadedMaze
    }
}
