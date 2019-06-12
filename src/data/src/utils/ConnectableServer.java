package utils;

/**
 * Interface for operating a game server.
 * @author Jake Mullett
 */
public interface ConnectableServer extends Connectable {

    /**
     * Non-blocking call to open a socket at the provided port.
     * Use isConnected to see if the client has connected yet.
     * @param port
     */
    public void accept(int port);
}
