package state.objective.objectivecondition;

import state.State;
import state.agent.Agent;
import state.objective.ObjectiveUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Jamie Palka
 * Class to define the ObjectiveConditions within the game which are triggered by the value of an agent property
 * being equal to a target value.
 */
public class AgentPropertyObjectiveConditionEqualTo<T> extends AgentPropertyObjectiveCondition {

    public AgentPropertyObjectiveConditionEqualTo(Map<String, Object> params) {
        super(params);
    }

    /**
     * Returns true if one of the agents with the given agentIdentifierProperty has their property with targetPropertyName equal to the targetValue.
     */
    public boolean evaluate(State state) {

        //agent = ObjectiveUtils.getAgentFromObjectiveIdentificationPropertyValue(state, objectiveIdentificationPropertyValue);

        List<Agent> agentList = ObjectiveUtils.getRelevantAgents(state, agentIdentifierProperty);

        for(Agent agent : agentList) {
            if ((((Comparable) agent.getPropertyValue(targetPropertyName)).compareTo(targetPropertyValue) == 0)
                    && (state.getCurrentLevelInt() == level || level == -1)) {
                return true;
            }
        }
        return false;
    }
}
