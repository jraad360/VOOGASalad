package engine;

import authoring.IActionDefinition;
import authoring.IGameDefinition;
import authoring.IStateDefinition;
import state.IPlayerLevelState;
import state.LevelState;
import state.Property;
import state.State;
import state.actiondecision.ActionDecision;
import state.agent.Agent;
import utils.SerializationException;
import utils.Serializer;
import utils.SerializerSingleton;

import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Jorge Raad
 * @author Luke Truitt
 * @author David Miron
 * @author Jamie Palka
 */
public class Game implements IGameDefinition, IPlayerGame {

    public static final String IMAGES_DIR = "resources";

    public static double nanoTrans = 1000000000.0;
    private boolean runFlag = true;
    public static final double DELTA_TIME = 0.0167;
    private Serializer serializer;

    private File imageDir;

    private State state;

    public Game() {
        serializer = SerializerSingleton.getInstance();
    }

    /**
     * Once called, begins continually running the game until stop() is called on it.
     */
    @Deprecated
    public void run() {
        runFlag = true;

        if(state.getGameOverStatus()) { runFlag = false; }

        // TODO: throw exception if state not initialized?
        double nextTime = System.nanoTime() / nanoTrans;

        while(runFlag) {
            double currentTime = System.nanoTime() / nanoTrans;

            // if deltaTime has passed, then update
            if(currentTime >= nextTime){

                // assign time of next update
                nextTime += DELTA_TIME;
                state.step(DELTA_TIME);
            }
        }
    }


    @Override
    public IPlayerLevelState getLevelState(){
        return this.state.getLevelState();
    }

    /**
     * @param gameDirLocation Directory containing game file and images
     */

    @Override
    public void loadState(File gameDirLocation){
        try {
            File gameFile = gameDirLocation.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.indexOf(".xml") >= 0;
                }
            })[0];
            state = (State) serializer.load(gameFile);
            imageDir = gameDirLocation.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.equals(IMAGES_DIR);
                }
            })[0];
            state.initializeLevelAgents();
            state.resetImageURLs(imageDir);
        } catch (SerializationException | IOException e) {
            // TODO: Deal with Exceptions by letting player know invalid file was chosen.
            e.printStackTrace();
        }
    }

    public void loadState2(File xml, File img){
        try {
            state = (State) serializer.load(xml);
            imageDir = img;
            state.initializeLevelAgents();
            state.resetImageURLs(imageDir);
        } catch (SerializationException | IOException e) {
            // TODO: Deal with Exceptions by letting player know invalid file was chosen.
            e.printStackTrace();
        }
    }

    @Override
    public void setState(IStateDefinition state) {
        this.state = (State)state;
    }

    @Override
    public IStateDefinition getState() {
        return state;
    }

    @Override
    public void step() {
        if(runFlag){
            this.state.step(DELTA_TIME);
        }
    }

    @Override
    public void saveState(File saveLocation){
        try {

            serializer.save(state, saveLocation, state.getAllImages());
        } catch (SerializationException e) {
            // TODO: Deal with Exceptions by letting player know about problem.
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        runFlag = false;
    }

    @Override
    public void play(){
        runFlag = true;
    }
}
