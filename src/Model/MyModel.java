package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.*;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {
    private Maze maze;
    private Solution mazeSolution;
    private Position playerPosition;
    private Server mazeGeneratorServer;
    private Server mazeSolverServer;
    private Client mazeGeneratorClient;
    private Client mazeSolverClient;


    public MyModel() {
        this.startServers();
    }

    private void startServers() {
        this.mazeGeneratorServer = new Server(5400, 2000, new ServerStrategyGenerateMaze());
        this.mazeGeneratorServer.start();
        this.mazeSolverServer = new Server(5401, 2000, new ServerStrategySolveSearchProblem());
        this.mazeSolverServer.start();
    }

    public void stopServers() {
        if (mazeGeneratorServer != null)
            this.mazeGeneratorServer.stop();
        if (mazeSolverServer != null)
        this.mazeSolverServer.stop();
    }


    @Override
    public void assignObserver(Observer Observer) {
        this.addObserver(Observer);
    }

    @Override
    public void generateMaze(int rows, int cols) {
        try {
            this.mazeGeneratorClient = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[rows * cols + 12];
                        is.read(decompressedMaze);
                        setMaze(new Maze(decompressedMaze));
                    } catch (Exception e) {
                        System.out.println("Communication problem with the Maze-Generator server");
                    }
                }
            });
            this.mazeGeneratorClient.communicateWithServer();
        } catch (UnknownHostException e) {
            System.out.println("cant create new mazeGeneratorClient..");
            return;
        }
        setChanged();
        notifyObservers(ModelResponses.MazeGenerated);
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    private void setMaze(Maze maze) {
        this.maze = maze;
        this.playerPosition = this.maze.getStartPosition();
    }

    @Override
    public void solveMaze() {
        try {
            this.mazeSolverClient = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.writeObject(maze);
                        toServer.flush();
                        mazeSolution = (Solution) fromServer.readObject();
                    } catch (Exception e) {
                        System.out.println("Communication problem with the Maze-Solver server");
                    }
                }
            });
        } catch (UnknownHostException e) {
            System.out.println("cant create new mazeSolverClient..");
            return;
        }

        this.mazeSolverClient.communicateWithServer();

        setChanged();
        notifyObservers(ModelResponses.MazeSolved);
    }

    @Override
    public Solution getMazeSolution() {
        return this.mazeSolution;
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {
        Position newPosition;
        switch (direction) {
            case UP -> newPosition = this.playerPosition.getUpPosition(); //UP
            case UP_RIGHT -> newPosition = this.playerPosition.getUpRightPosition();
            case RIGHT -> newPosition = this.playerPosition.getRightPosition(); //RIGHT
            case DOWN_RIGHT -> newPosition = this.playerPosition.getDownRightPosition();
            case DOWN -> newPosition = this.playerPosition.getDownPosition(); //DOWN
            case DOWN_LEFT -> newPosition = this.playerPosition.getDownLeftPosition();
            case LEFT -> newPosition = this.playerPosition.getLeftPosition(); //LEFT
            case UP_LEFT -> newPosition = this.playerPosition.getUpLeftPosition();
            default -> {
                return;
            }
        }
        if (this.maze.validMazePosition(newPosition) && this.maze.positionOfTile(newPosition)) {
            this.playerPosition = newPosition;
            setChanged();
            notifyObservers(ModelResponses.MoveAllowed);
            if (this.playerPosition.equals(this.maze.getGoalPosition()))
                this.finishGame();
        } else {
            setChanged();
            notifyObservers(ModelResponses.MoveNotAllowed);
        }
    }

    @Override
    public void finishGame() {
        setChanged();
        notifyObservers(ModelResponses.Finish);
        //TODO: how to goal?
    }

    @Override
    public Position getPlayerPosition() {
        return this.playerPosition;
    }

    public void saveMaze(File chosen) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chosen))) {
            out.writeObject(this.maze);
        } catch (IOException e) {
            System.out.println("Maze haven't saved");
        }
    }

    public void loadMaze(File chosen) {
        Maze tempMaze = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(chosen))) {
            tempMaze = (Maze) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Maze not allowed");
            return;
        }
        this.setMaze(tempMaze);
        setChanged();
        notifyObservers(ModelResponses.MazeLoaded);
    }
}
