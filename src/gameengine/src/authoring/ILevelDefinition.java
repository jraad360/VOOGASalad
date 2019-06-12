package authoring;

import state.AgentReference;
import state.Property;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface to define all the parts of a level of the game
 * @author David Miron
 */
public interface ILevelDefinition {

    /**
     * Get the agents that actually exist in the game
     * @return The currently existing agents
     */
    List<AgentReference> getCurrentAgents();
    void removeAgent(int index);
    void addAgent(String agentName, int x, int y, double direction, List<? extends IPropertyDefinition> instanceProperties);

    List<String> getPlaceableAgents();
    void removePlaceableAgent(int index);
    void removePlaceableAgent(String agentName);
    void addPlaceableAgent(String agentName);

    Map<String, List<Point2D>> getPaths();
    void removePath(int index);
    void removePath(String name);
    void addPath(String name, List<Point2D> path);

    String getBackgroundImageURL();
    void setBackgroundImageURL(String imageURL);

    ILevelDefinition clone() throws CloneNotSupportedException;

    void defineAttribute(IAttributeDefinition attribute);
    List<? extends IAttributeDefinition> getAttributes();
}
