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