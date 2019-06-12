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