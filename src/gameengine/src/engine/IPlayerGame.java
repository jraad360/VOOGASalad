package engine;

import state.IPlayerLevelState;

import java.io.File;

/**
 * This interface is to be implemented by Game, giving player the methods necessary to run the game.
 * @author Jorge Raad
 */
public interface IPlayerGame {

    /**
     * Loads the desired State from the given XML file into the Game.
     * This is to be called by Player to load and display the game prior to running.
     */
    void loadState(File gameDirLocation);

    /**
     * Saves the current State held by the Player into an XML file of the given name. This is to be used
     * from the Player's pause menu.
     */
    void saveState(File location);

    /**
     * Returns the current LevelState so that the player may take the current configuration of the level
     * (such as current agents) and display them in the scene.
     * @return
     */
    IPlayerLevelState getLevelState();

    void loadState2(File xml, File img);

//    /**
//     * Initiates the running of the game loop.
//     */
//    void run();

    /**
     * Halts the running of the game loop. This is to be used by the pause menu in the Player. If the game is
     * already stopped when this is called, it will remain stopped.
     */
    void stop();

    /**
     * Resumes the running of the game loop. This is to be used when exiting the pause menu in the Player. If the game is
     * already playing when this is called, it will remain running.
     */
    void play();


    /**
     * Updates the game's state. To be called from within in the Player's own JavaFX step method. Therefore, the Player
     * is what controls the rate at which the gae is run.
     */
    void step();
}
