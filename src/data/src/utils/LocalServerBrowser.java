package utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class for detecting open server interfaces for connecting to.
 *
 * @author Jake Mullett
 */
public class LocalServerBrowser {

    private static String LOCAL_ADDR_PREFIX = "192.168.1.";

    /**
     * Scan for servers which are listening on a specified port and implement the interface you are looking for.
     * This scans the traditional LAN address range, 192.168.1.1 through 192.168.1.255.
     * @param expectedPort int port to scan for
     * @param serverInterface Class of the server you are looking for
     * @return List of IP addresses of open servers
     */
    public static List<String> scanForLocalServers(int expectedPort, Class serverInterface) {
        return IntStream.range(2,255)
                .parallel()
                .filter((addrEnd) -> validServerAddress(expectedPort, serverInterface, addrEnd))
                .mapToObj(LocalServerBrowser::generateAddress)
                .collect(Collectors.toList());
    }

    private static boolean validServerAddress(int expectedPort, Class serverInterface, int addrEnd) {
        try {
            Connectable connectable = NetworkFactory.buildClient(serverInterface, new Object(), generateAddress(addrEnd), expectedPort);
            connectable.disconnect();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static String generateAddress(int suffix) {
        return LOCAL_ADDR_PREFIX + suffix;
    }

}
