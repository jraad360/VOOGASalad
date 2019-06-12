package utils.network;

import utils.Connectable;
import utils.NetworkException;
import utils.SerializationException;
import utils.network.datagrams.Datagram;
import utils.network.datagrams.Request;
import utils.network.datagrams.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which implements shared utilites between the client and the server.
 * Its primary function is to read/write requests, and reply with Responses containing the result of the method
 * calls if required.
 * @author Jake Mullett
 */
public abstract class NetworkedBase implements Connectable {

    private static final String ERROR_IN_CLOSING_CONNECTION = "Error in closing connection! ";
    private static final String METHOD_CALL_HAS_TIMED_OUT = "Method call has timed out.";
    private static final String RESPONSE_READER_THREAD_FAIL = "Error in trying to wait for response!";
    private static final int TIMEOUT_REQUEST_MS = 500;

    protected Logger LOGGER = Logger.getGlobal();

    // Network accessors are private so that the NetworkedClient and NetworkedServer have to use
    // methods set up in this class for consistency.
    private ConcurrentMap<String, Response> requestPool;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Thread readerThread;
    private Object parent;

    NetworkedBase(Object parentObject) {
        parent = parentObject;
        requestPool = new ConcurrentHashMap<>();
    }

    /**
     * Send a request which expects a response, and thus has to wait for the response from the network.
     * Package-private.
     * @param request request to be made.
     * @throws Throwable Exception which either occured in trying to transmit the request,
     * or an exception in the operation of the request's method call.
     */
    Object sendRequest(Request request) throws Throwable {
        sendDatagram(request);
        return request.requiresResponse() ? getResponse(request.getId()) : null;
    }

    /**
     * Disconnects the networked object from the object it is currently connected to.
     *
     * If it is not connected, does nothing.
     */
    public void disconnect(){
        if (isConnected()) {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                readerThread.interrupt();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ERROR_IN_CLOSING_CONNECTION + ex.getMessage());
            } finally {
                readerThread.interrupt();
                socket = null;
            }
        }
    }

    /**
     * @return Returns if this object is currently connected or not.
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    // Has Throwable as we could get literally any exception in response to the method call.
    private Object getResponse(String id) throws Throwable {
        long callTime = System.currentTimeMillis();
        try {
            synchronized(requestPool) {
                while(!requestPool.containsKey(id)) {
                    if (System.currentTimeMillis() > callTime + TIMEOUT_REQUEST_MS) {
                        throw new TimeoutException(METHOD_CALL_HAS_TIMED_OUT);
                    }
                    requestPool.wait();
                }
                Object result = requestPool.remove(id).getResult();
                // If we got an error as a result, throw it instead of returning it.
                if (result instanceof Throwable) {
                    throw ((Throwable) result);
                }
                return result;
            }
        } catch (InterruptedException e) {
            throw new NetworkException(RESPONSE_READER_THREAD_FAIL, e);
        }
    }

    private void sendDatagram(Datagram datagram) throws IOException {
        objectOutputStream.writeObject(datagram);
    }

    protected final void setupSocket(Socket newSocket) throws IOException {
        socket = newSocket;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush(); // flush to send header for initialiation of input stream
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        readerThread = new Thread(new NetworkReaderWorker(this));
        readerThread.start();
    }

    abstract void handleNetworkDisconnection();

    void readDatagrams() throws IOException, ClassNotFoundException, SerializationException {
        Datagram datagram = readDatagram();
        while (datagram != null) {
            switch (datagram.getType()) {
                case REQUEST:
                    handleRequest((Request) datagram);
                    break;
                case RESPONSE:
                    handleResponse((Response) datagram);
                    break;
            }
            datagram = readDatagram();
        }
    }

    private Datagram readDatagram() throws IOException, ClassNotFoundException {
        return (Datagram) objectInputStream.readObject();
    }

    private void handleRequest(Request datagram) throws IOException, SerializationException {
        sendDatagram(datagram.applyRequest(parent));
    }

    private void handleResponse(Response response) {
        synchronized (requestPool) {
            requestPool.put(response.getId(), response);
            requestPool.notifyAll();
        }
    }

}
