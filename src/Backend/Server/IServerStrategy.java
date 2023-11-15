package Backend.Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface IServerStrategy {
    /**
     * server strategy
     *
     * @param inFromClient input-stream from client
     * @param outToClient  output-stream to client
     */
    void ServerStrategy(InputStream inFromClient, OutputStream outToClient);

    String getLoggerName();
}
