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