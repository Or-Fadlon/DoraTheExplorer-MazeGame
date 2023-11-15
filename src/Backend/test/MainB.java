package Backend.test;

import Backend.Client.Client;
import Backend.Client.IClientStrategy;
import Backend.IO.MyCompressorOutputStream;
import Backend.IO.MyDecompressorInputStream;
import Backend.Server.Server;
import Backend.Server.ServerStrategyGenerateMaze;
import Backend.Server.ServerStrategySolveSearchProblem;
import Backend.algorithms.mazeGenerators.AMazeGenerator;
import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.MyMazeGenerator;
import Backend.algorithms.search.AState;
import Backend.algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainB {

    private static final String resultsFilePath = "./results.txt";
    private static final String logFilePath = "results.log";
    private static final int ServerListeningIntervalMS = 1000;
    private static int Port_ServerMazeGenerating = getRandomNumber(5000, 6000);
    private static int Port_ServerSearchProblemSolver = getRandomNumber(6001, 7000);
    private static int total_test = 0;
    private static int total_pass = 0;
    private static double avg_comp;

    private static String getTestStatusString(boolean testPassed) {
        return testPassed ? TestStatus.Passed.toString() : TestStatus.Failed.toString();
    }

    private static void appendToFile(String text, String filePath) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(text + "\r\n");
        } catch (IOException ex) {
            System.out.println(String.format("Error appending text to file: %s", filePath));
        }
    }

    private static void appendToResultsFile(String text) {
        appendToFile(text, resultsFilePath);
    }

    private static void appendToLogFile(String text) {
        appendToFile(text, logFilePath);
    }

    private static void appendToResultsAndLogFiles(String text) {
        appendToResultsFile(text);
        appendToLogFile(text);
    }

    private static void appendExceptionToFile(Exception e, String filePath) {
        String message = e.getMessage();
        if (message == null) {
            message = String.valueOf(e);
        }
        appendToFile(String.format("Exception: %s", message), filePath);
        if (e.getStackTrace().length > 1) {
            String msg = String.valueOf(e.getStackTrace()[0]);
            appendToFile(String.format("Exception Stack Trace: %s", msg), filePath);
        } else {
            int x = 1;
        }
    }

    //</editor-fold>
    private static int getRandomNumber(int from, int to) {
        if (from < to)
            return from + new Random().nextInt(Math.abs(to - from));
        return from - new Random().nextInt(Math.abs(to - from));
    }

    private static Integer getFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    public static int[][] getRowsColumnsCombinations() {
        int[][] rowsColumnsCombinations = {
                {5, 5},
                {20, 50},
                {50, 20},
                {100, 100},
                {500, 500},
                {1000, 1000}
        };
        return rowsColumnsCombinations;
    }

    public static void main(String[] args) {
        try {
            appendToResultsFile("Running");
            Test_CompressDecompressMaze();
            Test_CommunicateWithServers();
            appendToResultsFile("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //<editor-fold desc="Test_CompressDecompressMaze">
    private static void Test_CompressDecompressMaze() throws Exception {
        int[][] rowsColumnsCombinations = getRowsColumnsCombinations();
        double[] compresses = new double[rowsColumnsCombinations.length];
        int rows;
        int columns;
        for (int i = 0; i < rowsColumnsCombinations.length; i++) {
            rows = rowsColumnsCombinations[i][0];
            columns = rowsColumnsCombinations[i][1];
            System.out.println(String.valueOf(rows) + "_" + String.valueOf(columns));
            appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "]"));
            String mazeFileName = "savedMaze.maze";
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(rows, columns); //Generate new maze
            double mazeOriginalSize = maze.toByteArray().length;
            try {
                OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
                out.write(maze.toByteArray());
                out.flush();
                out.close();
            } catch (IOException e) {
                appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "_CMP_OS_E]"));
            }

            byte savedMazeBytes[] = new byte[0];
            try {
                InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
                savedMazeBytes = new byte[maze.toByteArray().length];
                in.read(savedMazeBytes);
                in.close();
            } catch (IOException e) {
                appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "_CMP_IS_E]"));

            }
            File compressed = new File("savedMaze.maze");
            double current_comp = compressed.length();
            Maze loadedMaze = new Maze(savedMazeBytes);
            boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(), maze.toByteArray());
            if (areMazesEquals != true) {
                appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "_CMP_NE]"));
            }
            compresses[i] = current_comp;
        }
        double total = 0;
        int length = compresses.length;
        for (int i = 0; i < compresses.length; i++) {
            total = total + compresses[i];
        }
        appendToResultsFile("[AVG: " + String.valueOf(total / length) + "]");
    }

    //<editor-fold desc="Test_CommunicateWithServers">
    private static void Test_CommunicateWithServers() throws Exception {
        //Initializing servers
        for (int counter = 4; counter <= 10; counter = counter + 2) {
            Port_ServerMazeGenerating = getRandomNumber(5000, 6000);
            Port_ServerSearchProblemSolver = getRandomNumber(6001, 7000);
            try {
                Server mazeGeneratingServer = new Server(Port_ServerMazeGenerating, 1000, new ServerStrategyGenerateMaze());
                Server solveSearchProblemServer = new Server(Port_ServerSearchProblemSolver, 1000, new ServerStrategySolveSearchProblem());

                //Starting  servers
                solveSearchProblemServer.start();
                mazeGeneratingServer.start();

                CommunicateWithServer_MazeGenerating(counter);
                CommunicateWithServer_SolveSearchProblem(counter);

                //Stopping all servers
                mazeGeneratingServer.stop();
                solveSearchProblemServer.stop();
            } catch (Exception e) {
                appendToResultsFile(String.valueOf("[TCWS_E]"));
            }
        }
    }

    //</editor-fold>

    private static void CommunicateWithServer_MazeGenerating(int i) throws Exception {
        AtomicInteger testsPassed = new AtomicInteger(0);
        try {
            new Client(InetAddress.getLocalHost(), Port_ServerMazeGenerating, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        total_test++;
                        int size = (int) (50 * (i));
                        System.out.println(size);
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{size, size};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[1000000 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        // System.out.println("Dec");
                        Maze maze = new Maze(decompressedMaze);
                                if (maze.toByteArray().length > 1000) {
                                    testsPassed.incrementAndGet();
                                }
                                else
                                {
                                    appendToResultsFile(String.valueOf(total_test));
                                }
                    } catch (Exception e) {
                        appendToResultsFile(String.valueOf("[CG_E]"));
                    }
                }
            }).communicateWithServer();
        } catch (Exception e) {

        } finally {
        }
        total_pass += testsPassed.get();
    }

    private static void CommunicateWithServer_SolveSearchProblem(int i) throws Exception {
        AtomicInteger testsPassed = new AtomicInteger(0);
        double parallelTests = 5;
        try {
            new Client(InetAddress.getLocalHost(), Port_ServerSearchProblemSolver, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        total_test++;
                        int size = (int) (50 * (i + 1));
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();

                        Maze maze = mg.generate(size, size);
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                        //Print Maze Solution retrieved from the server
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        if (!mazeSolutionSteps.isEmpty()) {
                            testsPassed.incrementAndGet();
                        } else {
                            appendToResultsFile("[CS_EM]");

                        }
                    } catch (Exception e) {
                        appendToResultsFile("[CS_E]");

                    }
                }
            }).communicateWithServer();
        } catch (Exception e) {

            appendToResultsFile("[CS_E]");


        } finally {
        }
        total_pass += testsPassed.get();
    }

    //<editor-fold desc="General">
    public static enum TestStatus {
        Passed, Failed;
    }
    //</editor-fold>
}
