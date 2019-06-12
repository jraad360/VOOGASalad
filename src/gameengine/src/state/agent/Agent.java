package state.agent;

import authoring.IActionDecisionDefinition;
import authoring.IAgentDefinition;
import authoring.IPropertyDefinition;
import authoring.exception.PropertyDoesNotExistException;
import state.IRequiresBaseAgent;
import state.IRequiresPaths;
import state.Property;
import state.action.Action;
import state.actiondecision.ActionDecision;
import state.condition.Condition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author David Miron
 * @author Luke_Truitt
 * @author Jamie Palka
 * Agent used by backend and authoring
 */
public class Agent implements IAgentDefinition, IPlayerAgent, Cloneable, Serializable{

    protected List<ActionDecision> actionDecisions;
    private PlayerAgent playerAgent;
    private PropertyChangeSupport pcs;

    /**
     * Agent constructor.
     * @param id agent ID
     * @param x,y initial location
     */
    public Agent(int id, int x, int y, int width, int height, double direction, String name, String imageURL, List<? extends IActionDecisionDefinition> actionDecisions,
                List<? extends IPropertyDefinition> properties) {
        this.actionDecisions = (List<ActionDecision>)actionDecisions;
        injectBaseAgentWhereNecessary(this.actionDecisions, this);
        playerAgent = new PlayerAgent(id, x, y, width, height, name, direction, imageURL);
        addProperties((List<Property>)properties);
        pcs = new PropertyChangeSupport(this);
    }

    public PlayerAgent getPlayerAgent(){
        return this.playerAgent;
    }

    private void injectBaseAgentWhereNecessary(List<ActionDecision> actionDecisions, Agent baseAgent) {
        for (ActionDecision ad: actionDecisions) {
            if (IRequiresBaseAgent.class.isAssignableFrom(ad.getAction().getClass()))
                ((IRequiresBaseAgent)ad.getAction()).injectBaseAgent(baseAgent);

            for (Condition condition: ad.getConditions()) {
                if (IRequiresBaseAgent.class.isAssignableFrom(condition.getClass()))
                    ((IRequiresBaseAgent)condition).injectBaseAgent(baseAgent);
            }
        }
    }

    /**
     * Update the agent's state, by moving and executing action decisions
     * @param agents All other agents in play
     */
    public void update(List<Agent> agents, double delta_time) throws CloneNotSupportedException, PropertyDoesNotExistException {

        for (ActionDecision decision: actionDecisions)
            decision.execute(agents, delta_time);

    }

    /**
     * Returns the X location of the Agent.
     * @return int - X location of the agent
     */
    public double getX() {
        return playerAgent.getX();
    }

    @Override
    public void setX(double x) {
        playerAgent.setX(x);
    }

    /**
     * Returns the Y location of the Agent.
     * @return int - Y location of the agent
     */
    public double getY() {
        return playerAgent.getY();
    }

    @Override
    public void setY(double y) {
        playerAgent.setY(y);
    }

    public String getName() {
        return playerAgent.getName();
    }

    public void setWidth(int w) {
        this.playerAgent.setWidth(w);
    }

    public void setHeight(int h) {
        this.playerAgent.setHeight(h);
    }

    public String getImageURL() {
        return playerAgent.getImageURL();
    }

    /**
     * Move the current agent a specified distance
     * @param x The vector representing the movement in x
     * @param y The vector representing the movement in y
     */

    public void move(double x, double y) {
        this.playerAgent.setX(x);
        this.playerAgent.setY(y);
    }

    public void setName(String name ) { this.playerAgent.setName(name);}

    public void setImageURL(String url) { this.playerAgent.setImageURL(url);}

    /**
     * Returns the distance between this Agent and the given Agent
     * @param agent Agent to which the distance will be calculated
     * @return distance
     */
    public double calculateDistance(Agent agent){
        return Math.sqrt(Math.pow(this.playerAgent.getX() - agent.getX(), 2) + Math.pow(this.playerAgent.getY() - agent.getY(), 2));
    }

    /**
     * Clones the agent
     * @return a copy of the this agent
     */
    @Override
    public Agent clone() throws CloneNotSupportedException {
        Agent clonedAgent = (Agent)super.clone();
        // ensure internal structure of agent is cloned as well
        // clone the playerAgent, so they aren't always in the same position, etc.
        clonedAgent.playerAgent = playerAgent.clone();
        // clone conditions and actions within ActionDecisions (without cloning the event handler)
        List<ActionDecision> newActionDecisions = new ArrayList<>();
        clonedAgent.pcs = new PropertyChangeSupport(clonedAgent);
        for(ActionDecision ad : actionDecisions){
            newActionDecisions.add(ad.clone());
        }
        injectBaseAgentWhereNecessary(newActionDecisions, clonedAgent);
        clonedAgent.actionDecisions = newActionDecisions;
        return clonedAgent;

    }


    /**
     * Updates location of the agent
     * @param x - the new x location to give to the agent
     * @param y - the new y location to give to the agent
     */
    public void setLocation(double x, double y) {
        playerAgent.setX(x);
        playerAgent.setY(y);
    }


    /**
     * Determines if two agents are intersecting.
     * @param agent check if this agent is intersecting with this agent.
     */
    public boolean isColliding(Agent agent) {
        return AgentUtils.createBoundingRect(this).intersects(AgentUtils.createBoundingRect(agent));
    }

    /**
     * Returns the height in that order
     * @return an array with first element of height
     */
    public int getHeight() {
        return this.playerAgent.getHeight();
    }

    /**
     * Returns the width in that order
     * @return an array with first element of width
     */
    public int getWidth() {
        return this.playerAgent.getWidth();
    }

    /**
     * Returns the direction angle of the agent.
     * @return the angle the agent is pointing to.
     */
    public double getDirection() {
        return this.playerAgent.getDirection();
    }

    public void setDirection(double direction){
        playerAgent.setDirection(direction);
    }

    public List<? extends IActionDecisionDefinition> getActionDecisions() {
        return this.actionDecisions;
    }

    public void removeActionDecision(int index) {
        actionDecisions.remove(index);
    }

    public void addActionDecision(IActionDecisionDefinition def) {
        actionDecisions.add((ActionDecision) def);
    }

    // TODO:  fill out all property-related methods

    @Override
    public List<Property> getProperties() {
        return this.playerAgent.getProperties();
    }

    @Override
    public void removeProperty(String name) {
        playerAgent.removeProperty(name);
    }

    @Override
    public void addProperty(IPropertyDefinition property) {
        playerAgent.addProperty((Property) property);
    }

    @Override
    public <T> void setProperty(String name, T value) throws PropertyDoesNotExistException {
        this.playerAgent.updateProperty(name, value);
    }

    public void addActionDecisionRaw(ActionDecision decision) {
        actionDecisions.add(decision);
    }


    /**
     * Returns the value of a Property specified by the Property name given.
     * @param name - name of desired Property
     * @return value of the Property
     */
    public Object getPropertyValue(String name) {
       return this.playerAgent.getProperty(name);
    }

    public Object getProperty(String name) {
        return this.playerAgent.getProperty(name);
    }

    public void injectPathsWhereNecessary(Map<String, List<Point2D>> paths) {
        for (ActionDecision ad: actionDecisions) {
            if (IRequiresPaths.class.isAssignableFrom(ad.getAction().getClass())) {
                ((IRequiresPaths)ad.getAction()).injectPaths(paths);
            }
        }
    }

    public void updateProperties(String[] properties, Object[] values) throws PropertyDoesNotExistException {
        for(int i = 0; i < properties.length; i++) {
            this.playerAgent.updateProperty(properties[i], values[i]);
        }
    }

    /**
     * Takes a given list of properties and adds them all to the list of properties held by the playerAgent.
     * @param properties - properties to be transferred to the playerAgent
     */
    public void setProperties(List<Property> properties){
        for(Property p : properties) {
            this.playerAgent.setProperty(p);
        }
    }

    /**
     * Sets property whether or not it was previously existing.
     * @param property - property to be set
     */
    public void overwriteProperty(Property property){
        if(!playerAgent.setProperty(property)){
            addProperty(new Property(property.getName(), property.getValue()));
        }
    }

    /**
     * Adds the given list of Properties to the existing list of Properties
     * @param properties
     */
    public void addProperties(List<Property> properties) {
        for(Property p : properties) {
            this.playerAgent.addProperty(p);
        }
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.playerAgent.addPropertyChangeListener(listener);
    }

    public void resetImageURL(File imageDir) {
        String fileName = new File(getImageURL()).getName();
        setImageURL(Paths.get(imageDir.getPath(), fileName).toString());
    }
}
