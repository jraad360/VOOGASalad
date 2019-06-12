package utils.network;

import utils.ConnectableServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;

/**
 * Server class for hosting a client connection and running the network interface.
 * @author Jake Mullett
 */
public class NetworkedServer extends NetworkedBase implements ConnectableServer {

    private static final String ACCEPTOR_THREAD_FOR_SERVER_FAILED = "Acceptor thread for server failed! ";

    private int openedPort;

    /**
     * Instantiate a NetworkedServer but do not open a port for connection.
     * @param parent Object which should implement the interface the client connecting to us expects.
     */
    public NetworkedServer(Object parent) {
        super(parent);
    }

    /**
     * Instantiate a NetworkedServer but do not open a port for connection.
     * @param parent Object which should implement the interface the client connecting to us expects.
     * @param port int port to open
     */
    public NetworkedServer(Object parent, int port) {
        super(parent);
        accept(port);
    }

    /**
     * Open a port to accept a TCP connection, non blocking as it is done in a separate thread.
     * @param port int port to open
     */
    @Override
    public void accept(int port) {
        if (!isConnected()) {
            Thread acceptThread = new Thread(() -> {
                try {
                    setupSocket(new ServerSocket(port).accept());
                    openedPort = port;
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ACCEPTOR_THREAD_FOR_SERVER_FAILED + ex.getMessage());
                }
            });
            acceptThread.start();
        }
    }

    @Override
    void handleNetworkDisconnection() {
        disconnect();
        accept(openedPort);
    }
}
