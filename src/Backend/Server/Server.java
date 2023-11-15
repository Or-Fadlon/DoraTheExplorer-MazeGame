package Backend.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server Class - using strategy pattern
 */
public class Server {
    private final int port; //Server Port
    private final int listeningIntervalMS; //Server Listening Interval before time-out
    private final IServerStrategy strategy; //Server strategy
    private final ExecutorService threadPool; // Thread pool
    private volatile boolean run = false; //Server run status
    private final Logger LOG;

    /**
     * Server Constructor
     *
     * @param port                server port
     * @param listeningIntervalMS Server Listening Interval before time-out
     * @param strategy            the server strategy we wish to implement
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = Executors.newFixedThreadPool(Configurations.getInstance().getThreadPoolSize());
        this.LOG = LogManager.getLogger(strategy.getLoggerName());
    }

    /**
     * starting the server in a new thread.
     * if the server already running show console message.
     */
    public void start() {
        if (this.run)
            LOG.info("Server is running already");
        else {
            Thread server = new Thread(() -> {
                this.run();
                LOG.info("Server stopped");
            });
            server.start();
        }
    }

    /**
     * the server routine
     */
    private void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(this.listeningIntervalMS);
            LOG.info("Starting server at port = " + this.port);
            this.run = true;

            while (this.run) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    LOG.info("Client accepted: " + clientSocket.toString());
                    // Insert the new task into the thread pool
                    this.threadPool.execute(() -> handleClient(clientSocket));
                } catch (SocketTimeoutException e) {
//                    System.out.println("Socket timeout");
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            this.run = false;
            LOG.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Stopping the running of the server.
     */
    public void stop() {
        LOG.info("Stopping server...");
        this.run = false;
        this.threadPool.shutdownNow();
    }

    /**
     * the client handle routine.
     *
     * @param clientSocket communication socket with the client.
     */
    private void handleClient(Socket clientSocket) {
        try {
            this.strategy.ServerStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            this.run = false;
            LOG.error(e.toString());
            e.printStackTrace();
        }
        LOG.info("Done handling client: " + clientSocket.toString());
    }


}
