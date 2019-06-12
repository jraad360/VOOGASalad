## Game Engine

- Create a separate module for displaying agents that the Authoring Environment and Player can share
- Authoring Environment will create objects and send them to the Game Engine.
- Package within Game Engine that is exported to Authoring Environment
    - the hierarchy of placeable agent types
    - possible game rules
    - possible objectives
    - possible player attributes

### Game Engine's API for Authoring Environment

```java

interface IState {
    List<Agent> availableTowers;
    List<Agent> currentAgents;
    List<Agent> currentObjectives;
    List<Agent> currentAttributes;
    Image Background
    
    /**
     * Adds a copy of a specific type of agent to the state.
     */
    void placeAgent(Agent agent);
}

```

```java

/**
 * Interface to represent Game Engine functionality to be used by the Authoring Environment
  * @author Jorge Raad
  * @author Jamie Palka
  * @author David Miron
  * @author Luke Truitt
 */
interface GameEngineAuthoring {
    
    /**
     * Set the state of the game
     */
    void setState(IState state);
}

```

```java

/**
 * Interface to represent Game Engine functionality to be used by the Player
 * @author Jorge Raad
 * @author Jamie Palka
 * @author David Miron
 * @author Luke Truitt
 */
interface GameEnginePlayer {

    /**
     * Register a function to run when objects are updated
     */
    void registerUpdater(Consumer<List<Updatable>> objects);

}

```

### Game Engine's API for Player

- pause, play

### Game Engine's API for both Authoring Environment and Player
- placeAgent()

## Player
No public interfaces, updates to front-end done through Observables.

## Authoring Environment

### External API 
```java	
/**
 * External API contained within the authoring environment.
 * Intended for use by the controller between the authoring environment and the game engine.
 * @author Mary Stuart Elder
 * @author Samuel Rabinowitz
 * @author Erin Lin
 */
 
public interface AuthorController {
    /**
     * Typcially called by the controller in case of exceptions thrown in the backend (ex. incorrect input).
     * Exception passed in, authoring environment works to determine the type of error message to display to the user.
     * Could also be called internally to display frontend exceptions (ex. NullPointerExceptions, IOExceptions, issues from display updates)
     */
    public void displayError(Exception displayException);
    
    /**
     * Called after a new agent is defined within the agent creation dialogue. This is used to serve as an intermediary method between the 
     */
    public void defineAgent(Agent agent);
    
    /**
     * 
     * /
    
}
``````

### Internal API

```java 
/**
 * Internal interface that allows for communication between the agent inventory and the x attribute editor. 
 * 
 */ 
public interface IDynamic {

    /*
     * If we create an agent and the agent is clicked, the attribute panel needs to diplay the agent's attributes. 
     */ 
    public List<Object> getAttributes();
    
    /**
     * We'll also need to set the attributes as well
     */ 
    public void setAttributes(List<Object>);


}

public interface IMapPane {

    /** 
     * can be used by other parts of the authoring enviroment to set the background
     */
    public void setBackground(String imageURL);
    
    /*
     * Used to add agent to a list of agents on the map for record
     */
    public void addAgent(Agent agent);
    
    /*
     * Can be used to obtain agents set on the map for the authoring environment
     */ 
    public List<Agent> getAgents();
    
}

public Interface IPathRegion() {
    
    /**
     * Allows other modules in the authoring environment to define the path of travel
     */ 
     public void setPath(Path path);
     
     
     /**
      * Allows other modules within the environment to obtain the path
      */ 
     public void getPath(Path path);
    

}
// These are the different methods we had in game engine that now are internal to you. If you already have methods that do these things then delete them.

/**
     * Takes in an agent object and adds it to the current state of the game (adds it to the list of agents available to the Player)
     */
    void defineAgent(Agent agent);
    
    /**
     * Takes in a rule object and adds it to the current state of the game
     */
    void setRule(Rule rule);
    
    /**
     * Takes in an object object and adds it to the current state of the game
     */
    void defineObjective(Objective objective);
```

## Data
```java	
/**
 * Set of interfaces for saving any Serializable object into a file or into a string, or reversing any serialization.
 */ 
public interface DataSerializer {
    
    /**
     * Takes in a Serializable object and a File to save it to and writes it.
     */
    public void save(Serializable state, File fileLocation);
    
    /**
     * Takes in a Serializable object and returns the serialized string.
     */
    public String serialize(Serializable object);
    
    /**
     * Takes in a String and returns a Serializable object back.
     */
    public Serializable deserialize(String object);
    
    /**
     * Takes in a File and loads the serial object from this file.
     */
    public Serializable load(File fileLocation);
    
}
```

```java

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
```
