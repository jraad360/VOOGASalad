package utils.network;

import utils.network.datagrams.Request;
import utils.reflect.MethodUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is used to wrap the game client or server class with logic for determining what kind
 * of request to send, as well as passing normal method calls such as disconnect() to the local instance.
 * @author Jake Mullett
 */
public class NetworkedInterfaceWrapper implements InvocationHandler {

    private NetworkedBase networkInterface;

    /**
     * Public instantiator for this wrapper proxy.
     *
     * @param networkInterface GameBase network handler for sending packets
     */
    public NetworkedInterfaceWrapper(NetworkedBase networkInterface) {
        this.networkInterface = networkInterface;
    }

    /**
     * Proxy wrapper which parses the method and determines which class should handle the method call.
     *
     * @param proxy  Object this method was called on. Unused here.
     * @param method Method that was called
     * @param args   Arguments supplied to this method
     * @return Result of method call, if any.
     * @throws Throwable Any exception that occurs during this method call.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            // if the method is a network command like connect, disconnect etc, call it.
            if (MethodUtils.isMethodInList(method, networkInterface.getClass().getMethods())) {
                return method.invoke(networkInterface, args);
            }
            // Otherwise send out a request.
            return networkInterface.sendRequest(new Request(method, args));
        } catch (InvocationTargetException ex) {
            // Because we use reflection, if it is an InvocationTargetException we get the wrapped
            // exception inside it that was the cause so we abstract the reflection away.
            throw ex.getTargetException();
        }
    }

}
