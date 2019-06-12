package state.objective.objectivecondition;

import state.Property;
import state.State;
import state.agent.Agent;
import state.objective.ObjectiveUtils;

import java.util.Map;

/**
 * @author Jamie Palka
 * A class to define the ObjectiveConditions within the game which are triggered by the existence of agents with a given property value
 */
public class AgentExistenceObjectiveCondition<T> extends ObjectiveCondition {

    protected int targetNumber; // target number of agents with given property
    protected String propertyName; // the property the user wishes to check agents by
    //protected T targetValue; // the value for the property needed

    public AgentExistenceObjectiveCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void setParams(Map<String, Object> params) {

        this.targetNumber = (Integer) params.get("targetNumber");
        this.propertyName = (String) params.get("targetPropertyName");
        //this.targetValue = (T) params.get("targetPropertyValue");
        super.setParams(params);
    }

    /**
     * Returns true if the correct number of agents with the given property value exist.
     */
    public boolean evaluate(State state) {

        int count = ObjectiveUtils.getRelevantAgents(state, propertyName).size();

        return ((state.getCurrentLevelInt() == level || level == -1) && count == targetNumber);
    }
}
