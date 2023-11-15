package Backend.test;

import Backend.Server.Server;
import Backend.Server.ServerStrategyGenerateMaze;

import java.util.Scanner;

public class mazeGeneratorServer {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        Server mazeGeneratingServer = new Server(5400, 3000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        while (!in.nextLine().equals("exit")) {
            Thread.sleep(1000);
        }
        mazeGeneratingServer.stop();
    }
}
