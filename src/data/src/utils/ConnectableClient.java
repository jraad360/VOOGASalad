package utils;

/**
 * Interface which will be implemented as well as the interface for whatever player,
 * authoring, or other interface this class is representing.
 * On instantiation, the class should be passed the object that it will be calling methods on.
 * @author Jake Mullett
 */
public interface ConnectableClient extends Connectable {

    /**
     * Connects this client to a listening client on the specified ip and port.
     * @param ip IP address to connect to
     * @param port int port number to try to itnerface with
     * @throws NetworkException Error in trying to connect to the server
     */
    public void connect(String ip, int port) throws NetworkException;

    /**
     * Connects this client to a listening client on the specified ip and port.
     * @param ip IP address to connect to
     * @param port int port number to try to itnerface with
     * @param interfaceKlazz Class expected on the server we are connecting to
     * @throws NetworkException Error in trying to connect to the server
     */
    public void connect(String ip, int port, Class interfaceKlazz) throws NetworkException;
}
