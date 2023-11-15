package Backend.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;

public class AServerStrategy implements IServerStrategy {
    protected Logger LOG;
    private String loggerName;

    /**
     * @param loggerName logger name to use
     */
    public AServerStrategy(String loggerName) {
        this.loggerName = loggerName;
        this.LOG = LogManager.getLogger(loggerName);
    }

    public String getLoggerName() {
        return this.loggerName;
    }

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) {
        throw new NotImplementedException("ServerStrategy methode not Implemented...");
    }

    static class NotImplementedException extends RuntimeException {
        public NotImplementedException(String s) {
            super(s);
        }
    }
}


