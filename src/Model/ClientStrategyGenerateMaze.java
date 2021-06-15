package Model;

import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;

import java.io.*;

public class ClientStrategyGenerateMaze implements IClientStrategy {
    private int rows = 10, cols = 10;
    private Maze maze;

    public void setMazeSize(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public Maze getMaze() {
        return this.maze;
    }

    @Override
    public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
            ObjectInputStream fromServer = new ObjectInputStream(inputStream);
            toServer.flush();
            int[] mazeDimensions = new int[]{rows, cols};
            toServer.writeObject(mazeDimensions);
            toServer.flush();
            byte[] compressedMaze = (byte[]) fromServer.readObject();
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
            byte[] decompressedMaze = new byte[rows * cols + 12];
            is.read(decompressedMaze);
            this.maze = new Maze(decompressedMaze);
        } catch (Exception var10) {
            var10.printStackTrace();
        }
    }
}
