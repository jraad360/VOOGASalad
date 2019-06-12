package state.objective.objectivecondition;

import state.State;
import state.agent.Agent;
import state.objective.ObjectiveUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Jamie Palka
 * Class to define the ObjectiveConditions within the game which are triggered by the value of a variable of an agent
 * (x value, y value, or direction) being equal to a targetPropertyValue.
 */
public class AgentVariableObjectiveConditionEqualTo extends AgentVariableObjectiveCondition {

    public AgentVariableObjectiveConditionEqualTo(Map<String, Object> params) {
        super(params);
    }

    /**
     * Returns true if the given agent's property is equal to the target value.
     */
    public boolean evaluate(State state) {

        //agent = ObjectiveUtils.getAgentFromObjectiveIdentificationPropertyValue(state, objectiveIdentificationPropertyValue);
        //setVariableValue(agent);

        List<Agent> agentList = ObjectiveUtils.getRelevantAgents(state, agentIdentifierProperty);

        for(Agent agent : agentList) {
            setVariableValue(agent);
            if ((variableValue == targetValue)
                    && (state.getCurrentLevelInt() == level || level == -1)) {
                return true;
            }
        }
        return false;
    }
}
