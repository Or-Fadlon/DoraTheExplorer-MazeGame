package Backend.Client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client class - using strategy pattern
 */
public class Client {
    private final InetAddress serverIP;
    private final int serverPort;
    private final IClientStrategy strategy;

    /**
     * Client constructor
     *
     * @param serverIP   server ip
     * @param serverPort server port
     * @param strategy   client strategy - need to compatible with the server
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    public void communicateWithServer() {
        try {
            Socket serverSocket = new Socket(this.serverIP, this.serverPort);
            System.out.printf("Client connected to server: %s:%s)%n", this.serverIP, this.serverPort);
            this.strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
            serverSocket.close();
        } catch (ConnectException e) {
            System.out.println("Cant find server...");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
