package engine;

import authoring.IAttributeDefinition;
import authoring.IPropertyDefinition;
import authoring.exception.PropertyDoesNotExistException;
import engine.event.GameEventMaster;
import authoring.ILevelDefinition;
import engine.event.events.AddAgentEvent;
import engine.event.events.RemoveAgentEvent;
import state.AgentReference;
import state.IPlayerLevelState;
import state.IRequiresGameEventMaster;
import state.LevelState;
import state.Property;
import state.agent.Agent;
import state.agent.AgentUtils;
import state.attribute.Attribute;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Level implements ILevelDefinition, IRequiresGameEventMaster, Serializable, Cloneable {
    public static int HEIGHT = 480;
    public static int WIDTH = 800;
    public static int TOLERANCE = 200;

    private LevelState levelState;
    private GameEventMaster eventMaster;

    private List<Agent> agentsToAdd;
    private List<Agent> agentsToRemove;

    private List<AgentReference> authoringAgentsPlaced;
    private List<String> authoringPlaceableAgents;

    private List<Agent> masterDefinedAgents;

    private Map<String, List<Point2D>> paths;

    public Level(List<Agent> masterDefinedAgents) {
        this.levelState = new LevelState();
        this.agentsToAdd = new ArrayList<>();
        this.agentsToRemove = new ArrayList<>();
        this.authoringAgentsPlaced = new ArrayList<>();
        this.authoringPlaceableAgents = new ArrayList<>();
        this.masterDefinedAgents = masterDefinedAgents;
        this.paths = new HashMap<>();
    }

    public void injectGameEventMaster(GameEventMaster eventMaster) {
        this.eventMaster = eventMaster;
        this.eventMaster.addRemoveAgentListener((Consumer<RemoveAgentEvent> & Serializable) (removeAgentEvent) ->
                    setAgentToRemove(removeAgentEvent.getAgent()));
        this.eventMaster.addAddAgentListener((Consumer<AddAgentEvent> & Serializable) addAgentEvent ->
                    setAgentToAdd(createAgentFromReference(addAgentEvent.getAgentReference())));
    }

    public List<Attribute> getCurrentAttributes() {
        return levelState.getCurrentAttributes();
    }

    @Override
    public List<AgentReference> getCurrentAgents() {
        return authoringAgentsPlaced;
    }

    private List<Agent> createAgentsFromReferences() {
        List<Agent> agents = new ArrayList<>();
        for (AgentReference agentReference: authoringAgentsPlaced) {
            Agent a = createAgentFromReference(agentReference);
            if (a != null)
                agents.add(a);
        }
        return agents;
    }

    private Agent createAgentFromReference(AgentReference agentReference) {
        for (Agent a : masterDefinedAgents) {
            if (a.getName().equals(agentReference.getName())) {
                try {
                    Agent clone = a.clone();
                    clone.setLocation(agentReference.getX(), agentReference.getY());
                    clone.setDirection(agentReference.getDirection());
                    clone.addProperties(agentReference.getInstanceProperties());
                    clone.injectPathsWhereNecessary(paths);
                    return clone;
                } catch (CloneNotSupportedException e) {
                    // Do nothing, that agent does not support cloning
                }
            }
        }
        // Agent does not exist, return null. TODO: Use exception handling
        return null;
    }

    private List<Agent> getAgentsFromNames(List<String> agentNames) {
        List<Agent> agents = new ArrayList<>();
        for (String agentName: agentNames) {
            for (Agent agent : masterDefinedAgents) {
                if (agent.getName().equals(agentName)) {
                    try {
                        Agent clone = agent.clone();
                        clone.injectPathsWhereNecessary(paths);
                        agents.add(clone);
                    } catch (CloneNotSupportedException e) {
                        // Do nothing, the agent does not support cloning
                    }
                    break;
                }
            }
        }
        return agents;
    }

    @Override
    public void removeAgent(int index) {
        authoringAgentsPlaced.remove(index);
    }

    @Override
    public void addAgent(String agentName, int x, int y, double direction, List<? extends IPropertyDefinition> instanceProperties) {
        authoringAgentsPlaced.add(new AgentReference(agentName, x, y, direction, (List<Property>) instanceProperties));
    }

    public List<Agent> getLevelAgents() {
        return levelState.getCurrentAgents();
    }

    @Override
    public List<String> getPlaceableAgents() {
        return authoringPlaceableAgents;
    }

    @Override
    public void removePlaceableAgent(int index) {
        authoringPlaceableAgents.remove(index);
    }

    public void removeAgent(Agent agent) {
        levelState.removeAgent(agent);
    }

    @Override
    public void removePlaceableAgent(String agentName) {
        authoringPlaceableAgents.remove(agentName);
    }

    @Override
    public void addPlaceableAgent(String agentName) {
        if(!authoringPlaceableAgents.contains(agentName))
             authoringPlaceableAgents.add(agentName);
    }

    @Override
    public Map<String, List<Point2D>> getPaths() {
        return paths;
    }

    @Override
    public void removePath(int index) {
        paths.remove(index);
    }

    @Override
    public void removePath(String name) {
        paths.remove(name);
    }

    @Override
    public void addPath(String name, List<Point2D> path) {
        paths.put(name, path);
    }

    public String getBackgroundImageURL() {
        return levelState.getBackgroundImageURL();
    }

    public void setBackgroundImageURL(String imageURL) {
        levelState.setBackgroundImageURL(imageURL);
    }

    public void step(double deltaTime) {
        int index = 0;
        for (Agent agent: levelState.getCurrentAgents()) {
            try {
                agent.update(levelState.getMutableAgentsExcludingSelf(agent), deltaTime);
                // if agent is out of bounds
                Rectangle agentBounds = AgentUtils.createBoundingRect(agent);
                Rectangle screenBounds = new Rectangle(-TOLERANCE, -TOLERANCE, WIDTH + 2*TOLERANCE,
                        HEIGHT + 2*TOLERANCE);
                if(!agentBounds.intersects(screenBounds)) {
                    eventMaster.triggerRemoveAgentEvent(new RemoveAgentEvent(agent));
                }
                index++;
            } catch (CloneNotSupportedException e) {
                // TODO: Deal with exception
                e.printStackTrace();
            } catch (PropertyDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
        }
        updateAgentsList();
    }

    private void updateAgentsList() {
        for (Agent agent: agentsToAdd)
            levelState.addCurrentAgent(agent);

        for (Agent agent: agentsToRemove)
            levelState.removeAgent(agent);

        agentsToAdd.clear();
        agentsToRemove.clear();
    }

    private void setAgentToAdd(Agent agent) {
        agentsToAdd.add(agent);
    }

    private void setAgentToRemove(Agent agent) {
        agentsToRemove.add(agent);
    }

    private void setLevelState(LevelState state) {
        this.levelState = state;
    }

    public IPlayerLevelState getLevelState(){return this.levelState;}

    public void initializeAgents() {
        for (Agent agent: createAgentsFromReferences()) {
            levelState.addCurrentAgent(agent);
        }
        var filteredAgents = authoringPlaceableAgents.stream().distinct().collect(Collectors.toList());
        for (Agent agent: getAgentsFromNames(filteredAgents)) {
            levelState.addPlaceableAgent(agent);
        }
    }

    @Override
    public ILevelDefinition clone() throws CloneNotSupportedException {
        try {
            Level clonedLevel = (Level) super.clone();
            clonedLevel.levelState = (LevelState) AgentUtils.deepClone(this.levelState);
            clonedLevel.agentsToAdd = new ArrayList<>();
            clonedLevel.agentsToRemove = new ArrayList<>();
            clonedLevel.authoringAgentsPlaced =
                    (List<AgentReference>) AgentUtils.deepClone(this.authoringAgentsPlaced);
            clonedLevel.authoringPlaceableAgents =
                    (List<String>) AgentUtils.deepClone(this.authoringPlaceableAgents);
            clonedLevel.paths =
                    (Map<String, List<Point2D>>) AgentUtils.deepClone(this.paths);
            clonedLevel.masterDefinedAgents = this.masterDefinedAgents;
            return clonedLevel;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new CloneNotSupportedException();
        }
    }

    public void resetImageURLs(File imageDir) {
        levelState.resetBackgroundImageURL(imageDir);
        for (Agent agent : levelState.getCurrentAgents()) {
            agent.resetImageURL(imageDir);
        }
    }

    public void setGameOver(boolean gameOver) {
        this.levelState.setGameOver(gameOver);
    }

    public void defineAttribute(IAttributeDefinition attribute) {
        levelState.addAttribute((Attribute)attribute);
    }

    public List<? extends IAttributeDefinition> getAttributes() {
        return levelState.getCurrentAttributes();
    }

    public void setWin(boolean won) {
        this.levelState.setWin(won);
    }
}
