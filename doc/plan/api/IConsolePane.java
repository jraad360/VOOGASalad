public interface IMapPane {
    /**
     * Can be used by other parts of the authoring environment to display messages in the console
     */

    /**
     * This method can be called externally to notify the user of environment changes.
     * Changes (good, neutral, and bad) to the environment that don't require immediate user action are sent to the console.
     * @param message
     */
    public void displayConsoleMessage(String message);
}