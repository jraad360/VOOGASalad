import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utils.ConnectableClient;
import utils.ConnectableServer;
import utils.NetworkException;
import utils.NetworkFactory;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing/examples for the NetworkFactory utils.
 * For documentation on how to utilize these objects, check out the NetworkFactory javadoc comments.
 *
 * If these tests fail, make sure you have followed the steps to get XStream working as well as the
 * possibility that your firewall is blocking these ports (despite being localhost)
 * @author Jake Mullett
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NetworkInterfaceTests {

    private static final String LOCALHOST = "127.0.0.1";
    private static final int SERVER_PORT = 1234;
    private static final int SLEEP_TIME = 50;
    private static final int TEST_RUN_ITERATIONS = 100;

    private ConnectableServer serverInterface;
    private ConnectableClient clientInterface;

    private BasicTestInterface interfaceViaClient;
    private BasicTestInterface iface;

    /**
     * Creates a client and server on localhost for interacting with the interface through our network
     * abstraction layer.
     * @throws IOException Issues with establishing the connection.
     */
    @BeforeAll
    public void setUp() throws NetworkException {
        iface = new TestObject();
        serverInterface = NetworkFactory.buildServer(BasicTestInterface.class, iface, SERVER_PORT);
        // the parentInstance here is this as for most usecases this will be the case, but it has no functionality during these tests
        // as nothing is called from the server to the client for interacting with the client's 'parent'.
        clientInterface = NetworkFactory.buildClient(BasicTestInterface.class, this, LOCALHOST, SERVER_PORT);
        interfaceViaClient = (BasicTestInterface) clientInterface;
    }

    @Test
    public void testChangeString() throws InterruptedException{
        String newString = "newString";
        runAndWaitForNetwork(() -> interfaceViaClient.changeString(newString));
        assertEquals(iface.getString(), newString);
    }

    @Test
    public void testChangeArgs() throws InterruptedException{
        String[] args = {"arg1", "arg2"};
        iface.storeArgs(null);
        assertNull(iface.getArgs()); // sanity check
        runAndWaitForNetwork(() -> interfaceViaClient.storeArgs(args));
        assertArrayEquals(args, iface.getArgs());
    }

    @Test
    public void testArgTypes() throws InterruptedException {
        Object[] args = new Object[] {0, "String", 0.0, new Date()};
        runAndWaitForNetwork(() -> interfaceViaClient.storeObjects(args));
        assertArrayEquals(args, iface.getObjects());
    }

    @Test
    public void testMethodWithReturnType() {
        assertEquals(iface.getString(), interfaceViaClient.getString());
        runAndTime("get one string via network", () -> interfaceViaClient.getString());
    }

    @Test
    public void testMethodWithMultipleReturnObjects() {
        assertArrayEquals(iface.getObjects(), interfaceViaClient.getObjects());
        runAndTime("get array of arguments via network", () -> interfaceViaClient.getObjects());
    }

    @Test
    public void testMultipleClientsOneObject() throws NetworkException, InterruptedException {
        int newport = 1235;
        var server2 =  NetworkFactory.buildServer(BasicTestInterface.class, iface, newport);
        BasicTestInterface client2 = (BasicTestInterface)NetworkFactory.buildClient(BasicTestInterface.class, this, LOCALHOST, newport);
        runAndWaitForNetwork(()-> client2.storeArgs("john", "cena"));
        assertArrayEquals(iface.getArgs(), interfaceViaClient.getArgs());
    }

    @Test
    public void testGenericInterfaceType() throws NetworkException {
        int newport = 1238;
        String key = "t";
        Map<String, String> map = new HashMap<>();
        var server2 =  (ConnectableServer) NetworkFactory.buildServer(Map.class, map, newport);
        var client2 = (Map<String, String> & ConnectableClient)NetworkFactory.buildClient(Map.class, this, LOCALHOST, newport);
        client2.put(key, "e");
        assertEquals(map.get(key), client2.get(key));
        runAndTime("put an object into a hashmap", () -> client2.put("q", "e"));
        client2.disconnect();
        server2.disconnect();
    }

    @Test
    public void testThrowsError() throws NetworkException {
        int newport = 1240;
        List<String> arr = new ArrayList<>();
        var server2 =  (ConnectableServer) NetworkFactory.buildServer(List.class, arr, newport);
        var client2 = (List<String> & ConnectableClient)NetworkFactory.buildClient(List.class, this, LOCALHOST, newport);
        assertThrows(IndexOutOfBoundsException.class, () -> client2.get(0));
    }

    @Test
    public void testIncorrectServerInterfaces() throws NetworkException {
        int newport = 1241;
        List<String> arr = new ArrayList<>();
        var server2 =  (ConnectableServer) NetworkFactory.buildServer(List.class, arr, newport);
        var client2 = (ConnectableClient)NetworkFactory.buildClient(List.class, this);
        assertThrows(NetworkException.class, () -> client2.connect(LOCALHOST, newport, Map.class));
    }

    private void runAndTime(String description, Runnable runnable) {
        long curtime = System.nanoTime();
        for (int i = 0; i < TEST_RUN_ITERATIONS; i++) {
            runnable.run();
        }
        long timeafter = System.nanoTime();
    }

    private void runAndWaitForNetwork(Runnable runnable) throws InterruptedException {
        runnable.run();
        Thread.sleep(SLEEP_TIME);
    }

    public static void main(String[] args) {
        // do nothing. This exists so that we can build a JAR for XStream.
    }

}
