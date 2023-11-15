package Backend.Client;

import java.io.InputStream;
import java.io.OutputStream;

public interface IClientStrategy {
    /**
     * client strategy
     *
     * @param inFromServer input-stream from server
     * @param outToServer  output-stream to server
     */
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
