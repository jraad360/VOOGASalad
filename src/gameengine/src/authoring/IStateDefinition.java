package authoring;

import java.util.List;

/**
 * Interface implemented by State such as the Authoring Environment should see it.
 * @author Jorge Raad
 * @author Jamie Palka
 */
public interface IStateDefinition {

    /**
     * Gives the list of levels stored within the State.
     * @return list of ILevelDefinition
     */
    List<? extends ILevelDefinition> getLevels();

    /**
     * Removes the level with the given index from the list of levels stored within the State.
     * @param index
     */
    void removeLevel(int index);

    /**
     * Adds the given level to the list of levels stored within the State.
     * @param level
     */
    void addLevel(ILevelDefinition level);

    /**
     * Get the agents that are defined to exist
     * @return The defined agents
     */
    List<? extends IAgentDefinition> getDefinedAgents();
    void removeDefinedAgent(int index);
    void addDefinedAgent(IAgentDefinition agent);

    void defineObjective(IObjectiveDefinition objective);
    List<? extends IObjectiveDefinition> getObjectives();

    void defineAttribute(IAttributeDefinition attribute);
    List<? extends IAttributeDefinition> getAttributes();
}
