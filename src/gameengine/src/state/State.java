package state;

import authoring.*;
import engine.Level;
import state.agent.Agent;
import state.objective.Objective;
import state.attribute.Attribute;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface to represent the game as the authoring environment should see it
 * @author Jorge Raad
 * @author David Miron
 * @author Jamie Palka
 * @author Luke Truitt
 */
public class State implements IStateDefinition, Serializable {

    private static final int START_LEVEL = 0;
    private List<Level> levels;
    private int currentLevel;
    private List<Objective> currentObjectives;
    private boolean gameOver;
    private boolean won;
    private List<Agent> agentsCurrent;
    private List<Agent> agentsOptions;
    private List<Attribute> attributes;

    private List<Agent> definedAgents;

    public State(List<Agent> masterDefinedAgents){
        this.currentLevel = START_LEVEL;
        this.levels = new ArrayList<>();
        this.currentObjectives = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.gameOver = false;
        this.won = false;
        this.definedAgents = masterDefinedAgents;
    }

    /**
     * The list of current levels levels
     * @return The levels
     */
    @Override
    public List<? extends ILevelDefinition> getLevels() {
        return this.levels;
    }

    /**
     * Remove a level
     * @param index The index of the level
     */
    @Override
    public void removeLevel(int index) {
        if (levels.size() > index) {
            levels.remove(index);
        }
    }

    /**
     * Add a level to the current list of levels. This level would be the last to be played.
     * @param level
     */
    @Override
    public void addLevel(ILevelDefinition level) {
        levels.add((Level)level);
    }

    @Override
    public List<? extends IAgentDefinition> getDefinedAgents() {
        return definedAgents;
    }

    @Override
    public void removeDefinedAgent(int index) {
        definedAgents.remove(index);
    }

    @Override
    public void addDefinedAgent(IAgentDefinition agent) {
        definedAgents.add((Agent)agent);
    }

    /**
     * Add an objective to the current list of objectives.
     * @param objective the objective to be added
     */
    public void defineObjective(IObjectiveDefinition objective) {
        Objective addObjective = (Objective)objective;
        currentObjectives.add(addObjective);
    }

    public List<Objective> getObjectives() {
        return currentObjectives;
    }

    /**
     * For Player - Iterate through and update your copy based on the corresponding ID.
     */
    public Iterable<Objective> getImmutableObjectives() {
        return List.copyOf(this.currentObjectives);
    }

    /**
     * Switches the current level being played to the next level. Currently the order in which the levels of a game are
     * played are simply determined by their order within the levels list.
     */
    public void nextLevel(){
        if (levels.get(currentLevel) != levels.get(levels.size() - 1)){
            currentLevel++;
        }
    }

    public List<Attribute> getCurrentAttributes() {
        return levels.get(currentLevel).getCurrentAttributes();
    }

    /**
     * Executes any outcomes necessary for existing objectives and then calls step() on the current level.
     * @param deltaTime the change in time
     */
    public void step(double deltaTime){

        for(Objective objective : currentObjectives) {
            objective.execute(this);
        }

        levels.get(currentLevel).step(deltaTime);
    }

    /**
     * @return level the current level
     */
    public Level getCurrentLevel() {
        return levels.get(currentLevel);
    }

    /**
     * @return int the current level
     */
    public int getCurrentLevelInt() {
        return currentLevel + 1;
    }

    /**
     * Gives the Game the status of the state regarding gameOver.
     * @return a boolean value representing if the game is over or not
     */
    public boolean getGameOverStatus() {

        return gameOver;
    }

    /**
     * Changes the value of gameOver to true.
     */
    public void makeGameOver() {
        gameOver = true;
        this.getCurrentLevel().setGameOver(true);
    }

    /**
     * Changes the value of gameOver to true.
     */
    public void win() {
        won = true;
        this.getCurrentLevel().setWin(true);
    }

    /**
     * Returns the list of agents that exist in the current level.
     */
    public List<Agent> getCurrentAgents() {
        return levels.get(currentLevel).getLevelAgents();
    }

    public IPlayerLevelState getLevelState(){
        return this.levels.get(currentLevel).getLevelState();
    }

    public void initializeLevelAgents() {
        for (Level level: levels) {
            level.initializeAgents();
        }
    }

    public void resetImageURLs(File imageDir) {
        for (Level level: levels) {
            level.resetImageURLs(imageDir);
        }
    }

    public List<String> getAllImages() {
        List<String> images = new ArrayList<>();
        for (Level level: levels) {
            images.add(level.getBackgroundImageURL());
        }
        for(IAgentDefinition a: getDefinedAgents()){
            System.out.println(a.getImageURL());
            images.add(a.getImageURL());
        }
        images.removeIf(p -> p == null);
        return images;
    }

    public void defineAttribute(IAttributeDefinition attribute) {
        attributes.add((Attribute)attribute);
    }

    public List<? extends IAttributeDefinition> getAttributes() {
        return attributes;
    }
}