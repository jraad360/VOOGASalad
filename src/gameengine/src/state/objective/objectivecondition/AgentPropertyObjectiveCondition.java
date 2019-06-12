package state.objective.objectivecondition;


import state.State;
import state.agent.Agent;

import java.util.Map;

/**
 * @author Jamie Palka
 * Abstract class to define the ObjectiveConditions within the game which are triggered by the value of an agent property.
 */
abstract public class AgentPropertyObjectiveCondition<T> extends ObjectiveCondition {

    protected String targetPropertyName; //property listening to
    protected T targetPropertyValue; //target value of property listening to
    //protected String objectiveIdentificationPropertyValue;
    //protected Agent agent;
    protected String agentIdentifierProperty; // property NAME by which to choose agents


    public AgentPropertyObjectiveCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void setParams(Map<String, Object> params) {

        this.targetPropertyName = (String) params.get("targetPropertyName");
        this.targetPropertyValue = (T) params.get("targetPropertyValue");
        //this.objectiveIdentificationPropertyValue = (String) params.get(OBJECTIVE_IDENTIFICATION_PROPERTY_PARAMS);
        this.agentIdentifierProperty = (String) params.get("agentIdentifierProperty");
        super.setParams(params);
    }

    /**
     * Returns true if the given agent has the correct condition for the targetPropertyValue.
     */
    abstract public boolean evaluate(State state);

}
