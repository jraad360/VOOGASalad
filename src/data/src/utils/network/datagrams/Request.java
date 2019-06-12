package utils.network.datagrams;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import utils.SerializationException;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for sending method calls across the network, these are sent via TCP.
 *
 * Each Request object is designed to contain one single method call which the recieveing Connectable
 * object should pass along to its parent interface it is representing.
 *
 * @author Jake Mullett
 */
public class Request extends Datagram {

    private boolean requiresResponse;

    @XStreamOmitField
    private transient UnaryOperator<Object> operation;

    /**
     * Instantiates a Request object which defines its own serialization in the class, and is ready
     * to send over the network.
     * @param method Method that you are wanting to call over the interface
     * @param args Arguments to supply to that method call
     * @throws SerializationException Errors that occur in serializing this method call or arguments.
     */
    public Request(Method method, Object[] args) throws SerializationException {
        super(DatagramType.REQUEST);
        // Methods cannot be serialized directly, so instead we wrap it in a lambda.
        this.payload = serializer.serialize((Serializable & UnaryOperator<Object>) (parent) -> {
            try {
                return method.invoke(parent, args);
            } catch (Exception ex) {
                Logger.getGlobal().log(Level.SEVERE, "Error in invoking method " + method.getName() + ex.getMessage());
                return ex;
            }
        });
        this.requiresResponse = !method.getReturnType().equals(Void.TYPE);
    }

    /**
     * Apply the method for this request to the supplied target.
     *
     * If an error occurs during the operation, it is returned as the result so it can be thrown
     * by the Response object.
     * @param target Object target that this request was destined for.
     * @return Response, if the method call of this request has a return type other than void.
     * @throws SerializationException Any exception from deserializing this object
     */
    public Response applyRequest(Object target) throws SerializationException {
        if (operation == null) {
            operation = (UnaryOperator<Object>)serializer.deserialize(payload);
        }
        Object result = operation.apply(target);
        return requiresResponse ? new Response(id, result) : null;
    }

    /**
     * @return if the method of this request has a non-void return type.
     */
    public boolean requiresResponse() {
        return requiresResponse;
    }
}
