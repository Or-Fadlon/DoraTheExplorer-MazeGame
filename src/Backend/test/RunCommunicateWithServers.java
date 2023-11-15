package Backend.test;

import Backend.Client.Client;
import Backend.Client.IClientStrategy;
import Backend.IO.MyDecompressorInputStream;
import Backend.Server.Server;
import Backend.Server.ServerStrategyGenerateMaze;
import Backend.Server.ServerStrategySolveSearchProblem;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.MyMazeGenerator;
import Backend.algorithms.search.AState;
import Backend.algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class RunCommunicateWithServers {
    public static void main(String[] args) {
//Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
//Starting servers
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();
//        Thread t = new Thread(() -> mazeGeneratingServer.start());
//        t.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int j = 0; j < 30; j++) {
            Thread a = new Thread(() -> CommunicateWithServer_MazeGenerating(1000, 1000));
            a.start();
        }
        for (int i = 0; i < 30; i++) {
            Thread b = new Thread(() -> CommunicateWithServer_SolveSearchProblem(1000, 1000));
            b.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        CommunicateWithServer_MazeGenerating(50, 50);
//        CommunicateWithServer_SolveSearchProblem(50, 50);
//        CommunicateWithServer_MazeGenerating(100, 100);
//        CommunicateWithServer_SolveSearchProblem(100, 100);
//        CommunicateWithServer_MazeGenerating(150, 150);
//        CommunicateWithServer_SolveSearchProblem(150, 150);
//        CommunicateWithServer_MazeGenerating(200, 200);
//        CommunicateWithServer_SolveSearchProblem(200, 200);
//        CommunicateWithServer_MazeGenerating(250, 250);
//        CommunicateWithServer_SolveSearchProblem(250, 250);
//        CommunicateWithServer_MazeGenerating(300, 300);
//        CommunicateWithServer_SolveSearchProblem(300, 300);
//        CommunicateWithServer_MazeGenerating(350, 350);
//        CommunicateWithServer_SolveSearchProblem(350, 350);
//        CommunicateWithServer_MazeGenerating(400, 400);
//        CommunicateWithServer_SolveSearchProblem(400, 400);
//        CommunicateWithServer_MazeGenerating(450, 450);
//        CommunicateWithServer_SolveSearchProblem(450, 450);
//        CommunicateWithServer_MazeGenerating(500, 500);
//        CommunicateWithServer_SolveSearchProblem(500, 500);


//        CommunicateWithServer_SolveSearchProblem();


//

//Stopping all servers
//        mazeGeneratingServer.stop();
//        solveSearchProblemServer.stop();
//        Scanner s = new Scanner(System.in);
//        while (true){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if(s.nextLine().equals("exit"))
//            break;
//        }
//        mazeGeneratingServer.stop();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Thread b = new Thread(() -> solveSearchProblemServer.start());
//        b.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Thread c = new Thread(() -> CommunicateWithServer_SolveSearchProblem());
//        c.start();
        Scanner s = new Scanner(System.in);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (s.nextLine().equals("e"))
                break;
        }
        solveSearchProblemServer.stop();
        mazeGeneratingServer.stop();
    }

    private static void CommunicateWithServer_MazeGenerating(int l, int r) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
//                        int l = 1000;
//                        int r =1000;
                        int[] mazeDimensions = new int[]{l, r};
                        toServer.writeObject(mazeDimensions); //send mazedimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[l * r + 1000]; //allocating byte[] for the decompressedmaze -
                        is.read(decompressedMaze); //Fill decompressed Maze with bytes
                        Maze maze = new Maze(decompressedMaze);
//                        maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem(int l, int r) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(l, r);
//                        maze.print();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
                        //Print Maze Solution retrieved from the server
//                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
//                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
//                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
//    private static void CommunicateWithServer_StringReverser() {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
//                        @Override
//                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                            try {
//                                BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
//                                PrintWriter toServer = new PrintWriter(outToServer);
//                                String message = "Client Message";
//                                String serverResponse;
//                                toServer.write(message + "\n");
//                                toServer.flush();
//                                serverResponse = fromServer.readLine();
//                                System.out.println(String.format("Server response:%s", serverResponse));
//                                toServer.flush();
//                                fromServer.close();
//                                toServer.close();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//            client.communicateWithServer();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
}