package utils.network;

import java.io.EOFException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runnable reader worker which is designed to be instantiated for running in a separate thread.
 *
 * It does not directly interface with any of the data, but simply operates control signals and instructs
 * the networkedBase to read datagrams, disconnect, and the like.
 *
 * @author Jake Mullett
 */
public class NetworkReaderWorker implements Runnable {

    private static final String EOF_EXCEPTION = "Socket/EOF exception, likely due to closed port on other side. Closing reading thread. ";
    private static final String DATAGRAM_FROM_NETWORK_ERROR = "Error in trying to process datagram from network. Error: \n";
    private static final int READ_INTERVAL_MS = 1;

    private NetworkedBase networkedBase;
    private Logger LOGGER = Logger.getGlobal();

    NetworkReaderWorker(NetworkedBase base) {
        networkedBase = base;
    }

    /**
     * Runs the worker thread.
     */
    @Override
    public void run() {
        boolean continueReading = true;
        while (networkedBase.isConnected() && continueReading) {
            try {
                networkedBase.readDatagrams();
                Thread.sleep(READ_INTERVAL_MS);
            } catch (SocketException | EOFException sockEx) {
                networkedBase.handleNetworkDisconnection();
                continueReading = false;
                LOGGER.log(Level.INFO, EOF_EXCEPTION);
            } catch (Exception ex) {
                // Because this is a separate thread, log and continue.
                LOGGER.log(Level.SEVERE, DATAGRAM_FROM_NETWORK_ERROR + ex.getMessage());
            }
        }
    }
}
