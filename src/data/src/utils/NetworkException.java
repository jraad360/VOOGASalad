package utils;

/**
 * Exception which occurred during the runtime of one of the NetworkUtils classes, related to serialization,
 * IO transmitting issues, timeouts, etc.
 */
public class NetworkException extends Exception {

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Create an exception from this string containing formatting information and the args to add within.
     * @param message String message
     * @param args Args to format in the string
     */
    public NetworkException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * Create an exception from this string containing formatting information and the args to add within.
     * @param message String message
     * @param throwable Exception to wrap with this Network exception.
     * @param args Args to format in the string
     */
    public NetworkException(String message, Throwable throwable,  Object... args) {
        super(String.format(message, args), throwable);
    }

}
