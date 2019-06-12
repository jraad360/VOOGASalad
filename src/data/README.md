# Network Utils

## Overview
This set of packages under the network module is designed to abstract away the network layer, allowing java classes to interact with each other
over the network via normal method calls. Here is a diagram of what this util is achieving: 

![diagram](https://i.imgur.com/1zctlvT.jpg)

This util is designed for networked play, particularly multiplayer but supports single player as well. Because the abstraction takes any 
interface as a parameter, if you are using it for the Voogasalad project it can be used as a networked interface for online authoring or playing.

## Using This Package
First off, this package uses `XStream` to achieve the serialization of method calls to send them over the network layer. 
Because of this, we have to use the trick shown in class and documented [here](https://docs.google.com/document/d/1OZj2a4qV2VawOdaw_h0CPdbjkDP5hFIA0Vq64aIsY6o/edit) to allow for `XStream` to properly access the internal attributes of our classes etc.

Once this is set up, let's run through an example. For simplicity, let's say that you are wanting to interact with a `Map<String,String>` across the network. 
We will have two classes, `AliceServer` and `BobClient` who are wanting to share the utility of this map. 

##### AliceServer

```java
public class AliceServer {
    
    private static final int PORT_OPENING = 1234;
    
    public AliceServer() {
        // Set up our server. We set the expected interface to null because we are just letting them use our map, and 
        // don't need to interact with anything on their end. 
        // We provide map as the 'parent object' so that the server can see it and pass method calls along to it.
        // Finally, open a port. 
        Map<String, String> map = new HashMap<>();
        var server2 = NetworkFactory.buildServer(null, map, PORT_OPENING);
        ...
    }
    
}
```

##### BobClient
```java
public class BobClient {
    
    private static final int SERVER_PORT = 1234; // this must be decided between your two classes
    private static final String SERVER_IP = "127.0.0.1"; // this is a localhost, but you will need to have this as input in some way
    
    private Map<String, String> networkMap;
    
    public BobClient() {
        // First, let's just create our client and connect.
        // We know that they are hosting a map over there for us, so provide it as the expected interface.
        // We set the 'parent object' to be this object, but it shouldn't matter as the server isn't expecting anything from us.
        ConnectableClient client = NetworkFactory.buildClient(Map.class, this, SERVER_IP, SERVER_PORT);
        networkMap = (Map<String, String>) client;
        // We can now use this like a normal map!
        networkMap.put("key", "value");
        String value = networkMap.get("key");
        ...
        // Alternatively, to have one reference we can do such:
//      var client = (Map<String, String> & NetworkedClientInterface)NetworkFactory.buildClient(Map.class, this, SERVER_IP, SERVER_PORT);
    }

}

```

## Additional Notes
This utility works two-way, so for example the server could be a `GameEngine` instance interacting with a `Player` instance, where both objects are calling methods on the other instance. 

For multiplayer/multiple objects over the network, the design choice was made to have one `ConnectableServer` instance per connection as to better abstract out the networking. 
For multiple connections simply create multiple instances with different port openings and treat each connection as one instance of whatever you are communicating with. 

## Troubleshooting
* Connection Hangs/Trouble Connecting

Make sure that your firewall allows traffic on the port you have opened, and that the IP of the server hosting is correct.

Additionally, the server must be instantiated and listening first before you can try to connect to it. 

* XStream errors/missing library utils

Check that you have built the JAR file for that specific `main()` function you are running off of, and that the rest of the module dependencies have been added in your package structure. 

## Issues? Suggestions?
Contact me at jtm51@duke.edu if you are having issues or want any additional features.
