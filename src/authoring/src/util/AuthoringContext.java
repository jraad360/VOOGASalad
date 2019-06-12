package util;

import authoring.GameFactory;
import authoring.IGameDefinition;
import authoring.IStateDefinition;
import panes.ConsolePane;

import java.util.ResourceBundle;
import java.util.function.BiConsumer;

/**
 * A context "kitchen-sink" object that should be common to all objects instantiated within the Authoring Environment.
 *
 * @author Samuel Rabinowitz
 */
public class AuthoringContext {

    private ResourceBundle resourceBundle;
    private BiConsumer<String, ConsolePane.Level> displayConsoleMessage;
    private GameFactory gameFactory;
    private IStateDefinition state;
    private IGameDefinition game;

    /**
     * Create an authoring context "kitchen-sink" object to provide common objects to the Authoring Environment.
     * @param resourceBundle the resource bundle for getting displayable strings in the current language
     * @param displayConsoleMessage a lambda that allows displaying of console messages in the ConsolePane
     * @param gameFactory the GameEngine's GameFactory for creating instances of GameEngine objects
     * @param state the GameEngine's state
     * @param game the GameEngine's game for saving games to data files
     */
    public AuthoringContext(ResourceBundle resourceBundle,
                            BiConsumer<String, ConsolePane.Level> displayConsoleMessage,
                            GameFactory gameFactory,
                            IStateDefinition state,
                            IGameDefinition game) {
        this.resourceBundle = resourceBundle;
        this.displayConsoleMessage = displayConsoleMessage;
        this.gameFactory = gameFactory;
        this.state = state;
        this.game = game;
    }

    /**
     * Get a string from the resource bundle in the current language
     * @param key the key for the string
     * @return the string
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Get a double from the resource bundle in the current language
     * @param key the key for the double
     * @return the double
     */
    public double getDouble(String key) {
        return Double.parseDouble(resourceBundle.getString(key));
    }

    /**
     * Get an int from the resource bundle in the current language
     * @param key the key for the int
     * @return the int
     */
    public int getInt(String key) {
        return Integer.parseInt(resourceBundle.getString(key));
    }

    /**
     * Set the lambda that allows for displaying messages in the console.
     * This method only exists because an AuthoringContext must be passed into the constructor for all
     * AuthoringPanes, including ConsolePane, but ConsolePane must be instantiated to set this lambda.
     * @param displayConsoleMessage the lambda that allows for displaying messages in the console
     */
    public void setDisplayConsoleMessage(BiConsumer<String, ConsolePane.Level> displayConsoleMessage) {
        this.displayConsoleMessage = displayConsoleMessage;
    }

    /**
     * Display a message in the console pane.
     * @param message the message to display
     * @param level the level of importance of the message
     */
    public void displayConsoleMessage(String message, ConsolePane.Level level) {
        displayConsoleMessage.accept(message, level);
    }

    /**
     * Get the current instance of the GameEngine's GameFactory.
     * @return the game factory
     */
    public GameFactory getGameFactory() {
        return gameFactory;
    }

    /**
     * Get the current instance of the GameEngine's State.
     * @return the state
     */
    public IStateDefinition getState() {
        return state;
    }

    /**
     * Set the state of the Game Factory.
     * @param state the state to set
     */
    public void setState(IStateDefinition state) {
        this.state = state;
    }

    /**
     * Get the current instance of the GameEngine's Game.
     * @return the game
     */
    public IGameDefinition getGame() {
        return game;
    }

    /* TODO: Add methods and instance variables as needed, but be restrictive (for example, I didn't make a "getResourceBundle" method because you only need to get a String from the bundle)
     * I also did not include setter methods because everything should be created once in the constructor. Consider carefully if setters are actually needed before adding them.
     */
}
