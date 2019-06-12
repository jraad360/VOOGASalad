package utils.network;

import utils.ConnectableClient;
import utils.NetworkException;
import utils.network.datagrams.Request;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Client class for connecting to a server and running the network interface.
 * @author Jake Mullett
 */
public class NetworkedClient extends NetworkedBase implements ConnectableClient {

    private static final String INTERFACE_MISMATCH = "Server interface did not match the expected interface %s!";
    private static final String CONNECTION_ERROR = "Error in attempting to connect to server %s on port %d";
    private static final String INTERFACE_RETRIEVAL_ERROR = "Error in getting server interface, error: %s";
    private static final String GET_CLASS = "getClass";

    /**
     * Instantiate a NetworkedClient but do not connect.
     * @param parentObject Object which should implement the interface the server you are connecting to expects.
     */
    public NetworkedClient(Object parentObject) {
        super(parentObject);
    }

    /**
     * Instantiate a NetworkedClient but do not connect.
     * @param parentObject Object which should implement the interface the server you are connecting to expects.
     * @param ip String IP address to connect to
     * @param port int port of port to connect on
     * @throws NetworkException Error in trying to connect to the server
     */
    public NetworkedClient(Object parentObject, String ip, int port) throws NetworkException {
        super(parentObject);
        connect(ip, port);

    }

    /**
     * Connects this client to a listening client on the specified ip and port.
     * @param ip IP address to connect to
     * @param port int port number to try to itnerface with
     * @param interfaceKlazz Class expected on the server we are connecting to
     * @throws NetworkException Error in trying to connect to the server
     */
    @Override
    public void connect(String ip, int port, Class interfaceKlazz) throws NetworkException {
        connect(ip, port);
        Object serverIface = getServerIface();
        if (!serverIface.equals(interfaceKlazz)) {
            throw new NetworkException(INTERFACE_MISMATCH, interfaceKlazz.toString());
        }
    }

    /**
     * Connects this client to a listening client on the specified ip and port.
     * @param ip IP address to connect to
     * @param port int port number to try to itnerface with
     * @throws NetworkException Error in trying to connect to the server
     */
    @Override
    public void connect(String ip, int port) throws NetworkException {
        if (!isConnected()) {
            try {
                setupSocket(new Socket(ip, port));
            } catch (IOException ex) {
                throw new NetworkException(CONNECTION_ERROR, ex, ip, port);
            }
        }
    }

    @Override
    void handleNetworkDisconnection() {
        disconnect();
    }

    private Object getServerIface() throws NetworkException {
        try {
            Method getIface = Object.class.getMethod(GET_CLASS);
            return sendRequest(new Request(getIface, null));
        } catch (Throwable ex) {
            throw new NetworkException(INTERFACE_RETRIEVAL_ERROR, ex, ex.getMessage());
        }
    }

}
