/**
 * Interface which will be implemented as well as the interface for whatever player,
 * gameengine, or other interface this class is representing.
 * On instantiation, the class should be passed the object that it will be calling methods on.
 */
public interface NetworkedClientInterface {

    /**
     * Binds a port to listen for connections, and return any incoming traffic to the object that instantiated it.
     */
    public void bindPort(int port);

    /**
     * Connects this client to a listening client on the specified ip and port. Returns false on unable to connect.
     */
    public boolean connect(String ip, int port);

}