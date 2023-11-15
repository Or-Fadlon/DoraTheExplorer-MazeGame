package Backend.test;

import Backend.Client.Client;
import Backend.Client.IClientStrategy;
import Backend.Server.Server;
import Backend.Server.ServerStrategySolveSearchProblem;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.MyMazeGenerator;
import Backend.algorithms.search.AState;
import Backend.algorithms.search.Solution;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class mazeSolverServerTest {
    public static void main(String[] args) {
        Server server = new Server(5401, 2000, new ServerStrategySolveSearchProblem());
        server.start();
        MyMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(100, 100);

        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(() ->
                    CommunicateWithServer_SolveSearchProblem(maze));
            t1.start();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem(Maze maze) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

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
}
