package state.objective.objectivecondition;

import state.State;
import state.agent.Agent;

import java.util.Map;

/**
 * @author Jamie Palka
 * Abstract class to define the ObjectiveConditions within the game which are triggered by the value of a variable of an agent
 * (x value, y value, or direction)
 */
abstract public class AgentVariableObjectiveCondition extends ObjectiveCondition {

    protected String variableName;
    protected double targetValue;
    //protected Agent agent;
    protected double variableValue;
    //protected String objectiveIdentificationPropertyValue;
    protected String agentIdentifierProperty; // property NAME by which to choose agents

    public AgentVariableObjectiveCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void setParams(Map<String, Object> params) {

        this.variableName = (String) params.get("variableName");
        this.targetValue = (Double) params.get("targetValue");
        //this.objectiveIdentificationPropertyValue = (String) params.get(OBJECTIVE_IDENTIFICATION_PROPERTY_PARAMS);
        this.agentIdentifierProperty = (String) params.get("agentIdentifierProperty");
        super.setParams(params);

    }

    protected void setVariableValue(Agent agent) {

        if(variableName.equals("x")) {
            variableValue = agent.getX();
        }

        if(variableName.equals("y")) {
            variableValue = agent.getY();
        }

        if(variableName.equals("direction")) {
            variableValue = agent.getDirection();
        }
    }

    /**
     * Returns true if the agent has the correct condition for the targetPropertyValue
     */
    abstract public boolean evaluate(State state);
    //TODO use reflection & a lambda expression rather than if statements - makes more extendable
}
